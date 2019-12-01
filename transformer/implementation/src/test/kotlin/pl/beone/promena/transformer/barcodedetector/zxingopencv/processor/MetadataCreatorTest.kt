package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.barcodedetector.metadata.BarcodeDetectorMetadata
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.ITF
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDecoder.DecodedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector.DetectedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.Processor.PageWithItem

class MetadataCreatorTest {

    @Test
    fun createMetadata() {
        val text = "30712345000010"
        val format = ITF
        val page = 1
        val vertexX = 1
        val vertexY = 2
        val vertex2X = 10
        val vertex2Y = 20
        val vertex3X = 100
        val vertex3Y = 200
        val vertex4X = 1000
        val vertex4Y = 2000

        val metadata = MetadataCreator.createMetadata(
            listOf(
                PageWithItem(
                    page,
                    DetectedBarcode(
                        DecodedBarcode(text, format),
                        FoundContour(
                            Vertex(vertexX, vertexY),
                            Vertex(vertex2X, vertex2Y),
                            Vertex(vertex3X, vertex3Y),
                            Vertex(vertex4X, vertex4Y)
                        )
                    )
                )
            )
        )

        with(BarcodeDetectorMetadata(metadata).getBarcodes()) {
            this shouldHaveSize 1
            with(get(0)) {
                getText() shouldBe text
                getFormat() shouldBe format.value
                getPage() shouldBe 1

                with(getContourVerticesOnPage()) {
                    with(get(0)) {
                        getX() shouldBe vertexX
                        getY() shouldBe vertexY
                    }
                    with(get(1)) {
                        getX() shouldBe vertex2X
                        getY() shouldBe vertex2Y
                    }
                    with(get(2)) {
                        getX() shouldBe vertex3X
                        getY() shouldBe vertex3Y
                    }
                    with(get(3)) {
                        getX() shouldBe vertex4X
                        getY() shouldBe vertex4Y
                    }
                }
            }
        }
    }
}