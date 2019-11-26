package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import kotlin.math.PI

class AngleRadiansCalculatorTest {

    @Test
    fun calculate() {
        with(AngleRadiansCalculator.calculate(FoundContour(Vertex(10, 20), Vertex(20, 20), Vertex(20, 30), Vertex(10, 30)))) {
            this shouldHaveSize 4
            get(0) shouldBe 0.0
            get(1) shouldBe PI * 0.5
            get(2) shouldBe PI
            get(3) shouldBe PI * 1.5
        }
    }

    @Test
    fun calculate2() {
        with(AngleRadiansCalculator.calculate(FoundContour(Vertex(1, 1), Vertex(2, 2), Vertex(1, 3), Vertex(0, 2)))) {
            this shouldHaveSize 4
            get(0) shouldBe PI * 0.25
            get(1) shouldBe PI * 0.75
            get(2) shouldBe PI * 1.25
            get(3) shouldBe PI * 1.75
        }
    }
}