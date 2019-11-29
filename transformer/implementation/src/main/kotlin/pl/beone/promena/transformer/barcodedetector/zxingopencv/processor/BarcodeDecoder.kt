package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.NotFoundException
import com.google.zxing.Reader
import com.google.zxing.aztec.AztecReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.datamatrix.DataMatrixReader
import com.google.zxing.maxicode.MaxiCodeReader
import com.google.zxing.multi.GenericMultipleBarcodeReader
import com.google.zxing.multi.MultipleBarcodeReader
import com.google.zxing.multi.qrcode.QRCodeMultiReader
import com.google.zxing.oned.*
import com.google.zxing.oned.rss.RSS14Reader
import com.google.zxing.oned.rss.expanded.RSSExpandedReader
import com.google.zxing.pdf417.PDF417Reader
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.applyWhiteBackground
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.drawCentred
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.rotate
import java.awt.image.BufferedImage
import kotlin.math.sqrt

class BarcodeDecoder(
    barcodeFormats: List<ZxingOpenCvBarcodeDetectorBarcodeFormat>,
    private val rotationThresholdDegrees: Int
) {

    data class DecodedBarcode(
        val text: String,
        val barcodeFormat: ZxingOpenCvBarcodeDetectorBarcodeFormat
    )

    private data class BarcodeDescriptor(
        val barcodeFormat: ZxingOpenCvBarcodeDetectorBarcodeFormat,
        val multipleBarcodeReader: MultipleBarcodeReader,
        val hints: Map<DecodeHintType, Any>
    )

    private data class BarcodeDescriptorWithDecodedBarcode(
        val decodedBarcode: DecodedBarcode,
        val barcodeDescriptor: BarcodeDescriptor
    )

    companion object {
        private const val MARGIN_SIZE = 10

        private val hints = mapOf(
            DecodeHintType.TRY_HARDER to true,
            DecodeHintType.PURE_BARCODE to false
        )

        private val allBarcodeDescriptors = listOf(
            BarcodeDescriptor(CODABAR, CodaBarReader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(UPC_A, UPCAReader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(UPC_E, UPCEReader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(EAN_8, EAN8Reader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(EAN_13, EAN13Reader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(CODE_39, Code39Reader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(CODE_128, Code128Reader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(ITF, ITFReader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(RSS_14, RSS14Reader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(RSS_EXPANDED, RSSExpandedReader().toGenericMultipleBarcodeReader(), hints),

            BarcodeDescriptor(QR_CODE, QRCodeMultiReader(), hints),
            BarcodeDescriptor(PDF417, PDF417Reader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(AZTEC_CODE, AztecReader().toGenericMultipleBarcodeReader(), hints),
            BarcodeDescriptor(DATA_MATRIX, DataMatrixReader().toGenericMultipleBarcodeReader(), emptyMap()),
            BarcodeDescriptor(MAXI_CODE, MaxiCodeReader().toGenericMultipleBarcodeReader(), hints)
        )

        private fun Reader.toGenericMultipleBarcodeReader(): GenericMultipleBarcodeReader =
            GenericMultipleBarcodeReader(this)
    }

    private val barcodeDescriptors = allBarcodeDescriptors.filter { barcodeFormats.contains(it.barcodeFormat) }

    fun decode(bufferedImage: BufferedImage, possibleAngleRadians: List<Double>): DecodedBarcode =
        generateRotations(createSquareWithMarginAndWhiteBackgroundAndCenter(bufferedImage), possibleAngleRadians)
            .flatMap(::generateRotations)
            .map(::convertToBinaryBitmap)
            .map(::decode).flatten()
            .let(::selectTheBestMatch) ?: throw NoSuchElementException()

    private fun createSquareWithMarginAndWhiteBackgroundAndCenter(bufferedImage: BufferedImage): BufferedImage {
        val diagonalWithMargin =
            sqrt((bufferedImage.width * bufferedImage.width).toDouble() + (bufferedImage.height * bufferedImage.height).toDouble()) + MARGIN_SIZE

        return BufferedImage(diagonalWithMargin.toInt(), diagonalWithMargin.toInt(), BufferedImage.TYPE_INT_ARGB)
            .also { it.applyWhiteBackground() }
            .also { bufferedImage.drawCentred(it) }
    }

    private fun generateRotations(bufferedImage: BufferedImage, angleRadians: List<Double>): List<BufferedImage> =
        angleRadians.map(bufferedImage::rotate)

    private fun generateRotations(bufferedImage: BufferedImage): List<BufferedImage> =
        (-rotationThresholdDegrees..rotationThresholdDegrees)
            .map { it * Math.toRadians(1.0) }
            .map(bufferedImage::rotate)

    private fun convertToBinaryBitmap(bufferedImage: BufferedImage): BinaryBitmap =
        BinaryBitmap(HybridBinarizer(BufferedImageLuminanceSource(bufferedImage)))

    private fun decode(binaryBitmap: BinaryBitmap): List<BarcodeDescriptorWithDecodedBarcode> =
        barcodeDescriptors.mapNotNull { barcodeDescriptor ->
            try {
                barcodeDescriptor.multipleBarcodeReader.decodeMultiple(binaryBitmap, barcodeDescriptor.hints)
                    .map { DecodedBarcode(it.text, barcodeDescriptor.barcodeFormat) }
                    .map { BarcodeDescriptorWithDecodedBarcode(it, barcodeDescriptor) }
            } catch (e: NotFoundException) {
                null
            }
        }.flatten()

    private fun selectTheBestMatch(barcodeDescriptorWithDecodedBarcodeList: List<BarcodeDescriptorWithDecodedBarcode>): DecodedBarcode? {
        if (barcodeDescriptorWithDecodedBarcodeList.isEmpty()) {
            return null
        }

        return barcodeDescriptorWithDecodedBarcodeList.asSequence()
            .groupBy { (decodedBarcode, barcodeDescriptor) -> decodedBarcode.text + barcodeDescriptor.barcodeFormat }
            .maxBy { (_, barcodeDescriptorWithDecodedBarcodeList) -> barcodeDescriptorWithDecodedBarcodeList.size }!!
            .value.first().decodedBarcode
    }
}