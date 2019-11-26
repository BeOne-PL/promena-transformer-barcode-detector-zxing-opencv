package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import pl.beone.promena.transformer.contract.transformer.transformerId

object ZxingOpenCvBarcodeDetectorConstants {

    const val TRANSFORMER_NAME = "barcode detector"

    const val TRANSFORMER_SUB_NAME = "ZXing & OpenCV"

    @JvmField
    val TRANSFORMER_ID = transformerId(TRANSFORMER_NAME, TRANSFORMER_SUB_NAME)
}