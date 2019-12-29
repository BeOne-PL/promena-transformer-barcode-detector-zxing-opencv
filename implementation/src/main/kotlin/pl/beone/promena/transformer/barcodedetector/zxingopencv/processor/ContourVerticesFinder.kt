package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.bytedeco.javacpp.indexer.FloatRawIndexer
import org.bytedeco.opencv.global.opencv_core.*
import org.bytedeco.opencv.global.opencv_imgproc.*
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.MatVector
import org.bytedeco.opencv.opencv_core.Point
import org.bytedeco.opencv.opencv_core.Size
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.createMatrix

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
        val contours: MatVector
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
    private lateinit var contours: MatVector

    fun find(matrix: Mat): List<FoundContour> =
        computeScharrGradientMagnitudeRepresentation(matrix)
            .let { (gradientX, gradientY) -> subtractYGradientFromXGradient(gradientX, gradientY) }
            .let(::applyThreshold)
            .also { ifStoreImmediateMatricesIsTrue { threshold = it } }
            .let(::constructClosingKernel)
            .also { ifStoreImmediateMatricesIsTrue { closingKernel = it } }
            .let(::applyErosionsAndDilations)
            .also { ifStoreImmediateMatricesIsTrue { erosionsAndDilations = it } }
            .let(::findContours)
            .also { ifStoreImmediateMatricesIsTrue { contours = it } }
            .let(::computeBoundingBox)
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

    private fun computeScharrGradientMagnitudeRepresentation(matrix: Mat): Pair<Mat, Mat> =
        (createMatrix { Sobel(matrix, it, CV_32F, 1, 0) } to
                createMatrix { Sobel(matrix, it, CV_32F, 0, 1) })

    private fun subtractYGradientFromXGradient(gradientX: Mat, gradientY: Mat): Mat =
        createMatrix {
            subtract(gradientX, gradientY, it)
            convertScaleAbs(it, it)
        }

    private fun applyThreshold(matrix: Mat): Mat =
        createMatrix(matrix) {
            threshold(matrix, it, thresholdValue, thresholdMaxVal, THRESH_BINARY)
        }

    private fun constructClosingKernel(matrix: Mat): Mat =
        createMatrix(matrix) {
            getStructuringElement(MORPH_RECT, Size(kernelSizeWidth.toInt(), kernelSizeHeight.toInt()))
                .also { kernel -> morphologyEx(matrix, it, MORPH_CLOSE, kernel) }
        }

    private fun applyErosionsAndDilations(matrix: Mat): Mat =
        createMatrix(matrix) { }
            .also { erode(matrix, it, Mat(), Point(-1, -1), erosionsIterations, BORDER_CONSTANT, null) }
            .also { dilate(it, it, Mat(), Point(-1, -1), dilationsIterations, BORDER_CONSTANT, null) }

    private fun findContours(matrix: Mat): MatVector =
        MatVector()
            .also { findContours(matrix, it, RETR_EXTERNAL, CHAIN_APPROX_SIMPLE) }

    // iterating using .map (High-order function) causes SEGMENTATION FAULT
    private fun computeBoundingBox(matrixVector: MatVector): List<Mat> =
        (0 until matrixVector.size()).map { index ->
            createMatrix {
                val rotatedRect = minAreaRect(matrixVector.get(index))
                boxPoints(rotatedRect, it)
            }
        }

    private fun convexHull(matrix: Mat): Mat =
        createMatrix(matrix) {
            convexHull(matrix, it)
        }

    private fun convertToContourVertices(matrix: Mat): FoundContour {
        val indexer = matrix.createIndexer<FloatRawIndexer>()

        return FoundContour(
            FoundContour.Vertex(indexer.get(0, 0).toInt(), indexer.get(0, 1).toInt()),
            FoundContour.Vertex(indexer.get(1, 0).toInt(), indexer.get(1, 1).toInt()),
            FoundContour.Vertex(indexer.get(2, 0).toInt(), indexer.get(2, 1).toInt()),
            FoundContour.Vertex(indexer.get(3, 0).toInt(), indexer.get(3, 1).toInt())
        ).also { indexer.release() }
    }

    private fun ifStoreImmediateMatricesIsTrue(toRun: () -> Unit) {
        if (storeImmediateMatrices) {
            toRun()
        }
    }
}