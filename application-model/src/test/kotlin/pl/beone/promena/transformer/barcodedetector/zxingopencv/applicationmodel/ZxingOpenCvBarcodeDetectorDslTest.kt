package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorConstants.TRANSFORMER_ID

class ZxingOpenCvBarcodeDetectorDslTest {

    @Test
    fun zxingOpenCvBarcodeDetectorTransformation() {
        with(zxingOpenCvBarcodeDetectorTransformation(APPLICATION_PDF, zxingOpenCvBarcodeDetectorParameters())) {
            transformerId shouldBe TRANSFORMER_ID
            targetMediaType shouldBe APPLICATION_PDF
            parameters.getAll().size shouldBe 0
        }
    }
}