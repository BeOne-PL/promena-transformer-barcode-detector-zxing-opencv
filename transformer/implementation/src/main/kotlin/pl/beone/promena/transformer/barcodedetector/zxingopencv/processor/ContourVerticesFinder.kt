package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.opencv.core.*
import org.opencv.imgproc.Imgproc

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
            .also { ifStoreImmediateMatricesIsTrue { threshold = it } }
            .let(::constructClosingKernel)
            .also { ifStoreImmediateMatricesIsTrue { closingKernel = it } }
            .let(::applyErosionsAndDilations)
            .also { ifStoreImmediateMatricesIsTrue { erosionsAndDilations = it } }
            .let(::findContours)
            .toMatOfPoint2f()
            .map(::computeBoundingBox)
            .also { ifStoreImmediateMatricesIsTrue { contours = it } }
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
            createMatrix {
                Imgproc.cvtColor(matrix, it, Imgproc.COLOR_BGR2GRAY)
            }
        } else {
            matrix
        }

    private fun computeScharrGradientMagnitudeRepresentation(matrix: Mat): Pair<Mat, Mat> =
        createMatrix {
            Imgproc.Sobel(matrix, it, CvType.CV_32F, 1, 0, -1)
        } to createMatrix { Imgproc.Sobel(matrix, it, CvType.CV_32F, 0, 1, -1) }

    private fun subtractYGradientFromXGradient(gradientX: Mat, gradientY: Mat): Mat =
        createMatrix {
            Core.subtract(gradientX, gradientY, it)
            Core.convertScaleAbs(it, it)
        }

    private fun applyThreshold(matrix: Mat): Mat =
        createMatrix {
            Imgproc.threshold(matrix, it, thresholdValue, thresholdMaxVal, Imgproc.THRESH_BINARY)
        }

    private fun constructClosingKernel(matrix: Mat): Mat =
        createMatrix {
            val kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(kernelSizeWidth, kernelSizeHeight))
            Imgproc.morphologyEx(matrix, it, Imgproc.MORPH_CLOSE, kernel)
        }

    private fun applyErosionsAndDilations(matrix: Mat): Mat =
        createMatrix { matrix.copyTo(it) }
            .also { Imgproc.erode(it, it, Mat(), Point(-1.0, -1.0), erosionsIterations) }
            .also { Imgproc.dilate(it, it, Mat(), Point(-1.0, -1.0), dilationsIterations) }

    private fun findContours(matrix: Mat): List<MatOfPoint> =
        mutableListOf<MatOfPoint>()
            .also { Imgproc.findContours(matrix, it, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE) }

    private fun List<MatOfPoint>.toMatOfPoint2f(): List<MatOfPoint2f> =
        map { MatOfPoint2f(*it.toArray()) }

    private fun computeBoundingBox(matOfPoint2f: MatOfPoint2f): Mat =
        createMatrix {
            val rotatedRect = Imgproc.minAreaRect(matOfPoint2f)
            Imgproc.boxPoints(rotatedRect, it)
        }

    private fun convexHull(matrix: Mat): MatOfPoint =
        matrix.toMatOfPoint()
            .also { Imgproc.convexHull(it, MatOfInt()) }

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
}