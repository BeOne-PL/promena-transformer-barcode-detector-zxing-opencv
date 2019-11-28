package pl.beone.promena.transformer.barcodedetector.zxingopencv.example

import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.zxingOpenCvBarcodeDetectorTransformation
import pl.beone.promena.transformer.contract.transformation.Transformation
import pl.beone.promena.transformer.internal.model.parameters.emptyParameters

fun promena(): Transformation {
    // Data: example.pdf

    return zxingOpenCvBarcodeDetectorTransformation(emptyParameters())
}