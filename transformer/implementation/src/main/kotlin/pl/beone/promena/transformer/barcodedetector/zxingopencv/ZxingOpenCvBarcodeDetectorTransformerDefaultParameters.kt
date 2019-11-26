package pl.beone.promena.transformer.barcodedetector.zxingopencv

import java.time.Duration

data class ZxingOpenCvBarcodeDetectorTransformerDefaultParameters(
    val formats: List<String>? = null,
    val regexFilter: String? = null,
    val linearRotationThresholdDegrees: Int,
    val linearAdditionalVerticalTransformation: Boolean,
    val linearThresholdValue: Double,
    val linearThresholdMaxVal: Double,
    val linearKernelSizeWidth: Double,
    val linearKernelSizeHeight: Double,
    val linearErosionsIterations: Int,
    val linearDilationsIterations: Int,
    val matrixRotationThresholdDegrees: Int,
    val matrixAdditionalVerticalTransformation: Boolean,
    val matrixThresholdValue: Double,
    val matrixThresholdMaxVal: Double,
    val matrixKernelSizeWidth: Double,
    val matrixKernelSizeHeight: Double,
    val matrixErosionsIterations: Int,
    val matrixDilationsIterations: Int,
    val timeout: Duration? = null
)