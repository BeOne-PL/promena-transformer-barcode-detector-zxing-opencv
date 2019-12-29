package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util

import org.bytedeco.javacv.Java2DFrameUtils
import org.bytedeco.opencv.global.opencv_imgproc
import org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.image.BufferedImage

fun <T> createMatrix(templateMatrix: Mat? = null, copyContent: Boolean = false, toProcess: (Mat) -> T): Mat =
    createMatBasedOnTemplate(templateMatrix, copyContent).also { toProcess(it) }

private fun createMatBasedOnTemplate(templateMatrix: Mat?, copyContent: Boolean): Mat =
    if (templateMatrix != null) {
        Mat(templateMatrix.rows(), templateMatrix.cols(), templateMatrix.type())
            .also { if (copyContent) templateMatrix.copyTo(it) }
    } else {
        Mat()
    }

fun Mat.toGrayscaleIfItIsColoured(): Mat =
    if (channels() != 1) {
        createMatrix(this) {
            opencv_imgproc.cvtColor(this, it, COLOR_BGR2GRAY)
        }
    } else {
        this
    }

// if no copy, it causes SEGMENTATION FAULT
fun Mat.toBufferedImage(): BufferedImage =
    Java2DFrameUtils.toBufferedImage(createMatrix(this, true) { })