package pl.beone.promena.transformer.barcodedetector.zxingopencv.evaluator

import org.opencv.core.Core

fun main() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    Window()
        .also { it.isVisible = true }
}