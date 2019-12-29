package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.Type.LINEAR
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.Type.MATRIX

enum class ZxingOpenCvBarcodeDetectorFormat(
    val value: String,
    val type: Type
) {
    CODABAR("Codabar", LINEAR),
    UPC_A("UPC-A", LINEAR),
    UPC_E("UPC-E", LINEAR),
    EAN_8("EAN-8", LINEAR),
    EAN_13("EAN-13", LINEAR),
    CODE_39("Code 39", LINEAR),
    CODE_128("Code 128", LINEAR),
    ITF("ITF", LINEAR),
    RSS_14("RSS-14", LINEAR),
    RSS_EXPANDED("RSS Expanded", LINEAR),
    QR_CODE("QR Code", MATRIX),
    PDF417("PDF417", MATRIX),
    AZTEC_CODE("Aztec Code", MATRIX),
    DATA_MATRIX("Data Matrix", MATRIX),
    MAXI_CODE("MaxiCode", MATRIX);

    enum class Type {
        LINEAR,
        MATRIX
    }

    companion object {
        fun of(format: String): ZxingOpenCvBarcodeDetectorFormat =
            values().firstOrNull { it.value == format }
                ?: throw IllegalArgumentException("Format <$format> isn't supported. Available formats: ${getStringFormats()}")

        fun getStringFormats(): List<String> =
            values().map(ZxingOpenCvBarcodeDetectorFormat::value)
    }
}