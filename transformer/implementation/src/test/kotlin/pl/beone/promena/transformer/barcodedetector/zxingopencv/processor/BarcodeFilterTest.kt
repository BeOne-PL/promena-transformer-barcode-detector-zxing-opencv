package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDecoder.DecodedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector.DetectedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.Processor.PageWithItem

class BarcodeFilterTest {

    companion object {
        private val foundContour = FoundContour(Vertex(1, 1), Vertex(2, 1), Vertex(2, 2), Vertex(1, 2))

        private val listOfPageWithDetectedBarcode = listOf(
            PageWithItem(1, DetectedBarcode(DecodedBarcode("0123456789", CODABAR), foundContour)),
            PageWithItem(2, DetectedBarcode(DecodedBarcode("TEST123456", QR_CODE), foundContour)),
            PageWithItem(3, DetectedBarcode(DecodedBarcode("ABCDEF", PDF417), foundContour)),
            PageWithItem(4, DetectedBarcode(DecodedBarcode("", CODE_128), foundContour))
        )
    }

    @Test
    fun filter() {
        with(BarcodeFilter("[a-zA-Z]+").filter(listOfPageWithDetectedBarcode)) {
            this shouldHaveSize 1
            get(0) shouldBe listOfPageWithDetectedBarcode[2]
        }
    }

    @Test
    fun `filter _ null regexString _ should return input list without barcode with empty text`() {
        with(BarcodeFilter(null).filter(listOfPageWithDetectedBarcode)) {
            this shouldHaveSize 3
            get(0) shouldBe listOfPageWithDetectedBarcode[0]
            get(1) shouldBe listOfPageWithDetectedBarcode[1]
            get(2) shouldBe listOfPageWithDetectedBarcode[2]
        }
    }
}