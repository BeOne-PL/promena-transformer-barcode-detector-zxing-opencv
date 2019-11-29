package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util

import org.bytedeco.opencv.opencv_core.Point
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder

fun ContourVerticesFinder.FoundContour.toPoint(): Point =
    Point(4).apply {
        position(0).x(vertex.x).y(vertex.y)
        position(1).x(vertex2.x).y(vertex2.y)
        position(2).x(vertex3.x).y(vertex3.y)
        position(3).x(vertex4.x).y(vertex4.y)
    }