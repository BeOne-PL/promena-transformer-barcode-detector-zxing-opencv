package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import mu.KotlinLogging
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
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
        Mat(imageMatrix.rows(), imageMatrix.cols(), CvType.CV_8U, Scalar(0.0))
            .also { Imgproc.fillConvexPoly(it, matOfPoint, Scalar(255.0)) }

    private fun createMatrixWithBoundingBox(imageMatrix: Mat, mask: Mat): Mat =
        createMatrix { imageMatrix.copyTo(it, mask) }

    private fun reverseMask(mask: Mat): Mat =
        createMatrix { Core.bitwise_not(mask, it) }

    private fun applyWhiteBackground(matrix: Mat, mask: Mat): Mat =
        createMatrix {
            matrix.copyTo(it)
            Core.bitwise_not(matrix, it, mask)
        }

    private fun FoundContour.toMatOfPoint(): MatOfPoint =
        with(this) {
            MatOfPoint(vertex.toPoint(), vertex2.toPoint(), vertex3.toPoint(), vertex4.toPoint())
        }

    private fun FoundContour.Vertex.toPoint(): Point =
        Point(x.toDouble(), y.toDouble())
}