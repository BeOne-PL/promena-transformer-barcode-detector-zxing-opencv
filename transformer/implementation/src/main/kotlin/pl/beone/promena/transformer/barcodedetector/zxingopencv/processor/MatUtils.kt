package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point

fun <T> createMatrix(toProcess: (Mat) -> T): Mat =
    Mat().also { toProcess(it) }


fun Mat.toMatOfPoint(): MatOfPoint =
    (0 until rows())
        .map { Point(get(it, 0).first(), get(it, 1).first()) }
        .let { MatOfPoint(*it.toTypedArray()) }