package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import kotlin.math.atan2

object AngleRadiansCalculator {

    private val referenceLineVertex = Vertex(0, 0)
    private val referenceLineVertex2 = Vertex(1, 0)

    fun calculate(foundContour: FoundContour): List<Double> =
        listOf(
            calculateAngle(foundContour.vertex, foundContour.vertex2),
            calculateAngle(foundContour.vertex2, foundContour.vertex3),
            calculateAngle(foundContour.vertex3, foundContour.vertex4),
            calculateAngle(foundContour.vertex4, foundContour.vertex)
        ).sorted()

    private fun calculateAngle(lineVertex: Vertex, lineVertex2: Vertex): Double {
        val angle1 = atan2(referenceLineVertex.y.toDouble() - referenceLineVertex2.y, referenceLineVertex.x.toDouble() - referenceLineVertex2.x)
        val angle2 = atan2(lineVertex.y.toDouble() - lineVertex2.y, lineVertex.x.toDouble() - lineVertex2.x)
        return angle1 - angle2
    }
}