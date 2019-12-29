package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import pl.beone.lib.typeconverter.internal.getClazz

object ZxingOpenCvBarcodeDetectorParametersConstants {

    object Formats {
        const val NAME = "formats"
        @JvmField
        val CLASS = getClazz<List<String>>()
    }

    object RegexFilter {
        const val NAME = "regexFilter"
        @JvmField
        val CLASS = String::class.java
    }

    // ***

    object LinearRotationThresholdDegrees {
        const val NAME = "linearRotationThresholdDegrees"
        @JvmField
        val CLASS = Int::class.java
    }

    object LinearAdditionalVerticalTransformation {
        const val NAME = "linearAdditionalVerticalTransformation"
        @JvmField
        val CLASS = Boolean::class.java
    }

    object LinearThresholdValue {
        const val NAME = "linearThresholdValue"
        @JvmField
        val CLASS = Double::class.java
    }

    object LinearThresholdMaxVal {
        const val NAME = "linearThresholdMaxVal"
        @JvmField
        val CLASS = Double::class.java
    }

    object LinearKernelSizeWidth {
        const val NAME = "linearKernelSizeWidth"
        @JvmField
        val CLASS = Double::class.java
    }

    object LinearKernelSizeHeight {
        const val NAME = "linearKernelSizeHeight"
        @JvmField
        val CLASS = Double::class.java
    }

    object LinearErosionsIterations {
        const val NAME = "linearErosionsIterations"
        @JvmField
        val CLASS = Int::class.java
    }

    object LinearDilationsIterations {
        const val NAME = "linearDilationsIterations"
        @JvmField
        val CLASS = Int::class.java
    }

    // ***

    object MatrixRotationThresholdDegrees {
        const val NAME = "matrixRotationThresholdDegrees"
        @JvmField
        val CLASS = Int::class.java
    }

    object MatrixAdditionalVerticalTransformation {
        const val NAME = "matrixAdditionalVerticalTransformation"
        @JvmField
        val CLASS = Boolean::class.java
    }

    object MatrixThresholdValue {
        const val NAME = "matrixThresholdValue"
        @JvmField
        val CLASS = Double::class.java
    }

    object MatrixThresholdMaxVal {
        const val NAME = "matrixThresholdMaxVal"
        @JvmField
        val CLASS = Double::class.java
    }

    object MatrixKernelSizeWidth {
        const val NAME = "matrixKernelSizeWidth"
        @JvmField
        val CLASS = Double::class.java
    }

    object MatrixKernelSizeHeight {
        const val NAME = "matrixKernelSizeHeight"
        @JvmField
        val CLASS = Double::class.java
    }

    object MatrixErosionsIterations {
        const val NAME = "matrixErosionsIterations"
        @JvmField
        val CLASS = Int::class.java
    }

    object MatrixDilationsIterations {
        const val NAME = "matrixDilationsIterations"
        @JvmField
        val CLASS = Int::class.java
    }
}