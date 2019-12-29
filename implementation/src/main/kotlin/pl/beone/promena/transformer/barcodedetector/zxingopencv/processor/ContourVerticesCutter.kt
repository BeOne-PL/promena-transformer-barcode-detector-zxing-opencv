package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import mu.KotlinLogging
import org.bytedeco.opencv.global.opencv_core.CV_8U
import org.bytedeco.opencv.global.opencv_imgproc.boundingRect
import org.bytedeco.opencv.global.opencv_imgproc.fillConvexPoly
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.Point
import org.bytedeco.opencv.opencv_core.Scalar
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.toPoint

object ContourVerticesCutter {

    private val logger = KotlinLogging.logger {}

    fun cutOut(imageMatrix: Mat, foundContour: FoundContour): Mat {
        val point = foundContour.toPoint()
        val boundingBoxMask = createBoundingBoxMask(imageMatrix, point)

        return try {
            Mat(createNewAndCopyWithMask(imageMatrix, boundingBoxMask), boundingRect(Mat(point)))
        } catch (e: Exception) {
            logger.debug { "Error occurred during cutting out bounding box using contour vertices. It's highly likely that bonding box covers whole image. Returning input image..." }
            imageMatrix
        }
    }

    private fun createBoundingBoxMask(matrix: Mat, point: Point): Mat =
        Mat(matrix.rows(), matrix.cols(), CV_8U, Scalar(0.0))
            .also { fillConvexPoly(it, point.position(0), 4, Scalar(255.0)) }

    private fun createNewAndCopyWithMask(matrix: Mat, boundingBoxMask: Mat): Mat =
        Mat(matrix.rows(), matrix.cols(), CV_8U, Scalar(255.0))
            .also { matrix.copyTo(it, boundingBoxMask) }
}