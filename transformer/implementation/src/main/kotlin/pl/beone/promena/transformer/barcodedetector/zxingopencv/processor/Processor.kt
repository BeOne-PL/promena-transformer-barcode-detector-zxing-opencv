package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import kotlinx.coroutines.asCoroutineDispatcher
import org.apache.pdfbox.pdmodel.PDDocument
import org.bytedeco.opencv.opencv_core.Mat
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat.Type
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector.DetectedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.toGrayscaleIfItIsColoured
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.singleTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Metadata
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.contract.model.data.Data
import pl.beone.promena.transformer.internal.model.metadata.plus
import pl.beone.promena.transformer.util.execute
import java.util.concurrent.Executors

internal class Processor(
    private val defaultParameters: ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
) {

    data class PageWithItem<T>(
        val page: Int,
        val item: T
    )

    companion object {
        private val linearBarcodeFormats = ZxingOpenCvBarcodeDetectorBarcodeFormat.values().filter { it.type == Type.LINEAR }
        private val matrixBarcodeFormats = ZxingOpenCvBarcodeDetectorBarcodeFormat.values().filter { it.type == Type.MATRIX }
    }

    private val executionDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    fun process(singleDataDescriptor: DataDescriptor.Single, parameters: Parameters): TransformedDataDescriptor.Single {
        val (data, _, metadata) = singleDataDescriptor

        val barcodeRegexFilter = BarcodeFilter(determineFilterRegexString(parameters))

        val transformedMetadata = execute(parameters.getTimeoutOrNull() ?: defaultParameters.timeout, executionDispatcher) {
            detectBarcodes(data, parameters, barcodeRegexFilter)
        }

        return singleTransformedDataDescriptor(data, metadata + transformedMetadata)
    }

    private fun determineFilterRegexString(parameters: Parameters): String? =
        parameters.getRegexFilterOrNull() ?: defaultParameters.regexFilter

    private fun detectBarcodes(data: Data, parameters: Parameters, barcodeFilter: BarcodeFilter): Metadata =
        PDDocument.load(data.getInputStream()).use { document ->
            (0 until document.numberOfPages)
                .map { PageWithItem(it + 1, PdfPageToGrayscaleMatrixConverter.convert(document, it).toGrayscaleIfItIsColoured()) }
                .map { (page, bufferedImage) -> detectBarcodes(bufferedImage, parameters).map { PageWithItem(page, it) } }
                .flatten()
                .let(barcodeFilter::filter)
                .sortedBy(PageWithItem<DetectedBarcode>::page)
                .let(MetadataCreator::createMetadata)
        }

    private fun detectBarcodes(matrix: Mat, parameters: Parameters): List<DetectedBarcode> =
        createLinearBarcodeDetector(parameters).detect(matrix) + createMatrixBarcodeDetector(parameters).detect(matrix)

    private fun createLinearBarcodeDetector(parameters: Parameters): BarcodeDetector =
        BarcodeDetector(
            getAvailableFormats(parameters, linearBarcodeFormats),
            parameters.getLinearRotationThresholdDegreesOrDefault(defaultParameters.linearRotationThresholdDegrees),
            parameters.getLinearAdditionalVerticalTransformationOrDefault(defaultParameters.linearAdditionalVerticalTransformation),
            false,
            parameters.getLinearThresholdValueOrDefault(defaultParameters.linearThresholdValue),
            parameters.getLinearThresholdMaxValOrDefault(defaultParameters.linearThresholdMaxVal),
            parameters.getLinearKernelSizeWidthOrDefault(defaultParameters.linearKernelSizeWidth),
            parameters.getLinearKernelSizeHeightOrDefault(defaultParameters.linearKernelSizeHeight),
            parameters.getLinearErosionsIterationsOrDefault(defaultParameters.linearErosionsIterations),
            parameters.getLinearDilationsIterationsOrDefault(defaultParameters.linearDilationsIterations)
        )

    private fun createMatrixBarcodeDetector(parameters: Parameters): BarcodeDetector =
        BarcodeDetector(
            getAvailableFormats(parameters, matrixBarcodeFormats),
            parameters.getMatrixRotationThresholdDegreesOrDefault(defaultParameters.matrixRotationThresholdDegrees),
            parameters.getMatrixAdditionalVerticalTransformationOrDefault(defaultParameters.matrixAdditionalVerticalTransformation),
            false,
            parameters.getMatrixThresholdValueOrDefault(defaultParameters.matrixThresholdValue),
            parameters.getMatrixThresholdMaxValOrDefault(defaultParameters.matrixThresholdMaxVal),
            parameters.getMatrixKernelSizeWidthOrDefault(defaultParameters.matrixKernelSizeWidth),
            parameters.getMatrixKernelSizeHeightOrDefault(defaultParameters.matrixKernelSizeHeight),
            parameters.getMatrixErosionsIterationsOrDefault(defaultParameters.matrixErosionsIterations),
            parameters.getMatrixDilationsIterationsOrDefault(defaultParameters.matrixDilationsIterations)
        )

    private fun getAvailableFormats(
        parameters: Parameters,
        formats: List<ZxingOpenCvBarcodeDetectorBarcodeFormat>
    ): List<ZxingOpenCvBarcodeDetectorBarcodeFormat> =
        try {
            (parameters.getFormatsOrNull() ?: defaultParameters.formats ?: throw NoSuchElementException())
                .intersect(formats.map(ZxingOpenCvBarcodeDetectorBarcodeFormat::format))
                .map(ZxingOpenCvBarcodeDetectorBarcodeFormat.Companion::of)
        } catch (e: NoSuchElementException) {
            formats
        }
}
