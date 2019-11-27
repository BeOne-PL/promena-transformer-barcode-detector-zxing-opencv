package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.opencv.core.*
import org.opencv.core.Core.convertScaleAbs
import org.opencv.core.Core.subtract
import org.opencv.core.CvType.CV_32F
import org.opencv.imgproc.Imgproc.*

class ContourVerticesFinder(
    private val storeImmediateMatrices: Boolean,
    private val thresholdValue: Double,
    private val thresholdMaxVal: Double,
    private val kernelSizeWidth: Double,
    private val kernelSizeHeight: Double,
    private val erosionsIterations: Int,
    private val dilationsIterations: Int
) {

    data class ImmediateMatrices(
        val threshold: Mat,
        val closingKernel: Mat,
        val erosionsAndDilations: Mat,
        val contours: List<Mat>
    )

    data class FoundContour(
        val vertex: Vertex,
        val vertex2: Vertex,
        val vertex3: Vertex,
        val vertex4: Vertex
    ) {
        data class Vertex(
            val x: Int,
            val y: Int
        )
    }

    private lateinit var threshold: Mat
    private lateinit var closingKernel: Mat
    private lateinit var erosionsAndDilations: Mat
    private lateinit var contours: List<Mat>

    fun find(matrix: Mat): List<FoundContour> =
        convertToGrayscaleIfItIsColoured(matrix)
            .let(::computeScharrGradientMagnitudeRepresentation)
            .let { (gradientX, gradientY) -> subtractYGradientFromXGradient(gradientX, gradientY) }
            .let(::applyThreshold)
            .also { ifStoreImmediateMatricesIsTrue { threshold = createMatrixAndCopy(it) } }
            .let(::constructClosingKernel)
            .also { ifStoreImmediateMatricesIsTrue { closingKernel = createMatrixAndCopy(it) } }
            .let(::applyErosionsAndDilations)
            .also { ifStoreImmediateMatricesIsTrue { erosionsAndDilations = createMatrixAndCopy(it) } }
            .let(::findContours)
            .toMatOfPoint2f()
            .map(::computeBoundingBox)
            .also { ifStoreImmediateMatricesIsTrue { contours = createMatricesAndCopy(it) } }
            .map(::convexHull)
            .map(::convertToContourVertices)

    fun getImmediateMatrices(): ImmediateMatrices =
        if (!storeImmediateMatrices) {
            throw error("Immediate matrices can't be returned because <storeImmediateMatrices> is false")
        } else {
            if (::threshold.isInitialized && ::closingKernel.isInitialized && ::erosionsAndDilations.isInitialized && ::contours.isInitialized) {
                ImmediateMatrices(threshold, closingKernel, erosionsAndDilations, contours)
            } else {
                error("Function <find> must be run first")
            }
        }

    private fun convertToGrayscaleIfItIsColoured(matrix: Mat): Mat =
        if (matrix.channels() == 3) {
            cvtColor(matrix, matrix, COLOR_BGR2GRAY)
                .let { matrix }
        } else {
            matrix
        }

    private fun computeScharrGradientMagnitudeRepresentation(matrix: Mat): Pair<Mat, Mat> =
        (createMatrix { Sobel(matrix, it, CV_32F, 1, 0, -1) } to
                createMatrix { Sobel(matrix, it, CV_32F, 0, 1, -1) })

    private fun subtractYGradientFromXGradient(gradientX: Mat, gradientY: Mat): Mat =
        createMatrix {
            subtract(gradientX, gradientY, it)
            convertScaleAbs(it, it)
        }
            .also { gradientX.release() }
            .also { gradientY.release() }

    private fun applyThreshold(matrix: Mat): Mat =
        threshold(matrix, matrix, thresholdValue, thresholdMaxVal, THRESH_BINARY)
            .let { matrix }

    private fun constructClosingKernel(matrix: Mat): Mat =
        getStructuringElement(MORPH_RECT, Size(kernelSizeWidth, kernelSizeHeight))
            .also { kernel -> morphologyEx(matrix, matrix, MORPH_CLOSE, kernel) }
            .let { matrix }

    private fun applyErosionsAndDilations(matrix: Mat): Mat =
        matrix
            .also { erode(matrix, matrix, Mat(), Point(-1.0, -1.0), erosionsIterations) }
            .also { dilate(matrix, matrix, Mat(), Point(-1.0, -1.0), dilationsIterations) }

    private fun findContours(matrix: Mat): List<MatOfPoint> =
        mutableListOf<MatOfPoint>()
            .also { findContours(matrix, it, Mat(), RETR_EXTERNAL, CHAIN_APPROX_SIMPLE) }
            .also { matrix.release() }

    private fun List<MatOfPoint>.toMatOfPoint2f(): List<MatOfPoint2f> =
        map { MatOfPoint2f(*it.toArray()) }
            .also { forEach { it.release() } }

    private fun computeBoundingBox(matOfPoint2f: MatOfPoint2f): Mat =
        createMatrix {
            val rotatedRect = minAreaRect(matOfPoint2f)
            boxPoints(rotatedRect, it)
        }.also { matOfPoint2f.release() }

    private fun convexHull(matrix: Mat): MatOfPoint =
        matrix.toMatOfPoint()
            .also { matrix.release() }
            .also { convexHull(it, MatOfInt()) }

    private fun convertToContourVertices(matrix: Mat): FoundContour =
        FoundContour(
            FoundContour.Vertex(matrix.get2DimensionalInt(0, 0, 0), matrix.get2DimensionalInt(0, 0, 1)),
            FoundContour.Vertex(matrix.get2DimensionalInt(1, 0, 0), matrix.get2DimensionalInt(1, 0, 1)),
            FoundContour.Vertex(matrix.get2DimensionalInt(2, 0, 0), matrix.get2DimensionalInt(2, 0, 1)),
            FoundContour.Vertex(matrix.get2DimensionalInt(3, 0, 0), matrix.get2DimensionalInt(3, 0, 1))
        )

    private fun Mat.get2DimensionalInt(row: Int, column: Int, index: Int): Int =
        get(row, column)[index].toInt()

    private fun ifStoreImmediateMatricesIsTrue(toRun: () -> Unit) {
        if (storeImmediateMatrices) {
            toRun()
        }
    }

    private fun createMatricesAndCopy(matrices: List<Mat>): List<Mat> =
        matrices.map { matrix ->
            createMatrixAndCopy(matrix)
        }

    private fun createMatrixAndCopy(matrix: Mat): Mat =
        createMatrix {
            matrix.copyTo(it)
        }
}