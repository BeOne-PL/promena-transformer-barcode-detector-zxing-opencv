package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorConstants.TRANSFORMER_ID

class ZxingOpenCvBarcodeDetectorDslKtTest {

    @Test
    fun zxingOpenCvBarcodeDetectorTransformation() {
        zxingOpenCvBarcodeDetectorTransformation(zxingOpenCvBarcodeDetectorParameters()).let {
            it.transformerId shouldBe TRANSFORMER_ID
            it.targetMediaType shouldBe APPLICATION_PDF
            it.parameters.getAll().size shouldBe 0
        }
    }
}