package pl.beone.promena.transformer.barcodedetector.zxingopencv.example

import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.zxingOpenCvBarcodeDetectorParameters
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.zxingOpenCvBarcodeDetectorTransformation
import pl.beone.promena.transformer.contract.transformation.Transformation

fun promena(): Transformation {
    // Data: example.pdf

    return zxingOpenCvBarcodeDetectorTransformation(APPLICATION_PDF, zxingOpenCvBarcodeDetectorParameters())
}