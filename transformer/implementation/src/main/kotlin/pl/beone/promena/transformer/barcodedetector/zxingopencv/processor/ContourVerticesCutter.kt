package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import mu.KotlinLogging
import org.opencv.core.Core.bitwise_not
import org.opencv.core.CvType.CV_8U
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.fillConvexPoly
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour

object ContourVerticesCutter {

    private val logger = KotlinLogging.logger {}

    fun cutOut(imageMatrix: Mat, foundContour: FoundContour): Mat {
        val matOfPoint = foundContour.toMatOfPoint()
        val boundingBoxMask = createBoundingBoxMask(imageMatrix, matOfPoint)

        return try {
            applyWhiteBackground(createMatrixWithBoundingBox(imageMatrix, boundingBoxMask), reverseMask(boundingBoxMask))
                .submat(Imgproc.boundingRect(matOfPoint))
        } catch (e: Exception) {
            logger.debug { "An error occurred during cutting out bounding box using contour vertices. It's highly likely that bonding box covers the whole image. Returning input image..." }
            imageMatrix
        }
    }

    private fun createBoundingBoxMask(imageMatrix: Mat, matOfPoint: MatOfPoint): Mat =
        Mat(imageMatrix.rows(), imageMatrix.cols(), CV_8U, Scalar(0.0))
            .also { fillConvexPoly(it, matOfPoint, Scalar(255.0)) }

    private fun createMatrixWithBoundingBox(imageMatrix: Mat, mask: Mat): Mat =
        createMatrix { imageMatrix.copyTo(it, mask) }

    private fun reverseMask(mask: Mat): Mat =
        createMatrix { bitwise_not(mask, it) }
            .also { mask.release() }

    private fun applyWhiteBackground(matrix: Mat, mask: Mat): Mat =
        bitwise_not(matrix, matrix, mask)
            .let { matrix }

    private fun FoundContour.toMatOfPoint(): MatOfPoint =
        with(this) {
            MatOfPoint(vertex.toPoint(), vertex2.toPoint(), vertex3.toPoint(), vertex4.toPoint())
        }

    private fun FoundContour.Vertex.toPoint(): Point =
        Point(x.toDouble(), y.toDouble())
}