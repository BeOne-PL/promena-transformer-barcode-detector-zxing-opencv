@file:JvmName("ZxingOpenCvBarcodeDetectorParametersDsl")

package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.Formats
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearAdditionalVerticalTransformation
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearDilationsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearErosionsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearKernelSizeHeight
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearKernelSizeWidth
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearRotationThresholdDegrees
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearThresholdMaxVal
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearThresholdValue
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixAdditionalVerticalTransformation
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixDilationsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixErosionsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixKernelSizeHeight
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixKernelSizeWidth
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixRotationThresholdDegrees
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixThresholdMaxVal
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixThresholdValue
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.RegexFilter
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.parameters.MapParameters
import pl.beone.promena.transformer.internal.model.parameters.addIfNotNull
import pl.beone.promena.transformer.internal.model.parameters.emptyParameters

fun zxingOpenCvBarcodeDetectorParameters(
    formats: List<String>? = null,
    regexFilter: String? = null,
    linearRotationThresholdDegrees: Int? = null,
    linearAdditionalVerticalTransformation: Boolean? = null,
    linearThresholdValue: Double? = null,
    linearThresholdMaxVal: Double? = null,
    linearKernelSizeWidth: Double? = null,
    linearKernelSizeHeight: Double? = null,
    linearErosionsIterations: Int? = null,
    linearDilationsIterations: Int? = null,
    matrixRotationThresholdDegrees: Int? = null,
    matrixAdditionalVerticalTransformation: Boolean? = null,
    matrixThresholdValue: Double? = null,
    matrixThresholdMaxVal: Double? = null,
    matrixKernelSizeWidth: Double? = null,
    matrixKernelSizeHeight: Double? = null,
    matrixErosionsIterations: Int? = null,
    matrixDilationsIterations: Int? = null
): MapParameters =
    emptyParameters() addIfNotNull
            (Formats.NAME to formats) addIfNotNull
            (RegexFilter.NAME to regexFilter) addIfNotNull
            (LinearRotationThresholdDegrees.NAME to linearRotationThresholdDegrees) addIfNotNull
            (LinearAdditionalVerticalTransformation.NAME to linearAdditionalVerticalTransformation) addIfNotNull
            (LinearThresholdValue.NAME to linearThresholdValue) addIfNotNull
            (LinearThresholdMaxVal.NAME to linearThresholdMaxVal) addIfNotNull
            (LinearKernelSizeWidth.NAME to linearKernelSizeWidth) addIfNotNull
            (LinearKernelSizeHeight.NAME to linearKernelSizeHeight) addIfNotNull
            (LinearErosionsIterations.NAME to linearErosionsIterations) addIfNotNull
            (LinearDilationsIterations.NAME to linearDilationsIterations) addIfNotNull
            (MatrixRotationThresholdDegrees.NAME to matrixRotationThresholdDegrees) addIfNotNull
            (MatrixAdditionalVerticalTransformation.NAME to matrixAdditionalVerticalTransformation) addIfNotNull
            (MatrixThresholdValue.NAME to matrixThresholdValue) addIfNotNull
            (MatrixThresholdMaxVal.NAME to matrixThresholdMaxVal) addIfNotNull
            (MatrixKernelSizeWidth.NAME to matrixKernelSizeWidth) addIfNotNull
            (MatrixKernelSizeHeight.NAME to matrixKernelSizeHeight) addIfNotNull
            (MatrixErosionsIterations.NAME to matrixErosionsIterations) addIfNotNull
            (MatrixDilationsIterations.NAME to matrixDilationsIterations)

fun Parameters.getFormats(): List<String> =
    get(Formats.NAME, Formats.CLASS)

fun Parameters.getFormatsOrNull(): List<String>? =
    getOrNull(Formats.NAME, Formats.CLASS)

fun Parameters.getFormatsOrDefault(default: List<String>): List<String> =
    getOrDefault(Formats.NAME, Formats.CLASS, default)

fun Parameters.getRegexFilter(): String =
    get(RegexFilter.NAME, RegexFilter.CLASS)

fun Parameters.getRegexFilterOrNull(): String? =
    getOrNull(RegexFilter.NAME, RegexFilter.CLASS)

fun Parameters.getRegexFilterOrDefault(default: String): String =
    getOrDefault(RegexFilter.NAME, RegexFilter.CLASS, default)

// ***

fun Parameters.getLinearRotationThresholdDegrees(): Int =
    get(LinearRotationThresholdDegrees.NAME, LinearRotationThresholdDegrees.CLASS)

fun Parameters.getLinearRotationThresholdDegreesOrNull(): Int? =
    getOrNull(LinearRotationThresholdDegrees.NAME, LinearRotationThresholdDegrees.CLASS)

fun Parameters.getLinearRotationThresholdDegreesOrDefault(default: Int): Int =
    getOrDefault(LinearRotationThresholdDegrees.NAME, LinearRotationThresholdDegrees.CLASS, default)

fun Parameters.getLinearAdditionalVerticalTransformation(): Boolean =
    get(LinearAdditionalVerticalTransformation.NAME, LinearAdditionalVerticalTransformation.CLASS)

fun Parameters.getLinearAdditionalVerticalTransformationOrNull(): Boolean? =
    getOrNull(LinearAdditionalVerticalTransformation.NAME, LinearAdditionalVerticalTransformation.CLASS)

fun Parameters.getLinearAdditionalVerticalTransformationOrDefault(default: Boolean): Boolean =
    getOrDefault(LinearAdditionalVerticalTransformation.NAME, LinearAdditionalVerticalTransformation.CLASS, default)

fun Parameters.getLinearThresholdValue(): Double =
    get(LinearThresholdValue.NAME, LinearThresholdValue.CLASS)

fun Parameters.getLinearThresholdValueOrNull(): Double? =
    getOrNull(LinearThresholdValue.NAME, LinearThresholdValue.CLASS)

fun Parameters.getLinearThresholdValueOrDefault(default: Double): Double =
    getOrDefault(LinearThresholdValue.NAME, LinearThresholdValue.CLASS, default)

fun Parameters.getLinearThresholdMaxVal(): Double =
    get(LinearThresholdMaxVal.NAME, LinearThresholdMaxVal.CLASS)

fun Parameters.getLinearThresholdMaxValOrNull(): Double? =
    getOrNull(LinearThresholdMaxVal.NAME, LinearThresholdMaxVal.CLASS)

fun Parameters.getLinearThresholdMaxValOrDefault(default: Double): Double =
    getOrDefault(LinearThresholdMaxVal.NAME, LinearThresholdMaxVal.CLASS, default)

fun Parameters.getLinearKernelSizeWidth(): Double =
    get(LinearKernelSizeWidth.NAME, LinearKernelSizeWidth.CLASS)

fun Parameters.getLinearKernelSizeWidthOrNull(): Double? =
    getOrNull(LinearKernelSizeWidth.NAME, LinearKernelSizeWidth.CLASS)

fun Parameters.getLinearKernelSizeWidthOrDefault(default: Double): Double =
    getOrDefault(LinearKernelSizeWidth.NAME, LinearKernelSizeWidth.CLASS, default)

fun Parameters.getLinearKernelSizeHeight(): Double =
    get(LinearKernelSizeHeight.NAME, LinearKernelSizeHeight.CLASS)

fun Parameters.getLinearKernelSizeHeightOrNull(): Double? =
    getOrNull(LinearKernelSizeHeight.NAME, LinearKernelSizeHeight.CLASS)

fun Parameters.getLinearKernelSizeHeightOrDefault(default: Double): Double =
    getOrDefault(LinearKernelSizeHeight.NAME, LinearKernelSizeHeight.CLASS, default)

fun Parameters.getLinearErosionsIterations(): Int =
    get(LinearErosionsIterations.NAME, LinearErosionsIterations.CLASS)

fun Parameters.getLinearErosionsIterationsOrNull(): Int? =
    getOrNull(LinearErosionsIterations.NAME, LinearErosionsIterations.CLASS)

fun Parameters.getLinearErosionsIterationsOrDefault(default: Int): Int =
    getOrDefault(LinearErosionsIterations.NAME, LinearErosionsIterations.CLASS, default)

fun Parameters.getLinearDilationsIterations(): Int =
    get(LinearDilationsIterations.NAME, LinearDilationsIterations.CLASS)

fun Parameters.getLinearDilationsIterationsOrNull(): Int? =
    getOrNull(LinearDilationsIterations.NAME, LinearDilationsIterations.CLASS)

fun Parameters.getLinearDilationsIterationsOrDefault(default: Int): Int =
    getOrDefault(LinearDilationsIterations.NAME, LinearDilationsIterations.CLASS, default)

// ***

fun Parameters.getMatrixRotationThresholdDegrees(): Int =
    get(MatrixRotationThresholdDegrees.NAME, MatrixRotationThresholdDegrees.CLASS)

fun Parameters.getMatrixRotationThresholdDegreesOrNull(): Int? =
    getOrNull(MatrixRotationThresholdDegrees.NAME, MatrixRotationThresholdDegrees.CLASS)

fun Parameters.getMatrixRotationThresholdDegreesOrDefault(default: Int): Int =
    getOrDefault(MatrixRotationThresholdDegrees.NAME, MatrixRotationThresholdDegrees.CLASS, default)

fun Parameters.getMatrixAdditionalVerticalTransformation(): Boolean =
    get(MatrixAdditionalVerticalTransformation.NAME, MatrixAdditionalVerticalTransformation.CLASS)

fun Parameters.getMatrixAdditionalVerticalTransformationOrNull(): Boolean? =
    getOrNull(MatrixAdditionalVerticalTransformation.NAME, MatrixAdditionalVerticalTransformation.CLASS)

fun Parameters.getMatrixAdditionalVerticalTransformationOrDefault(default: Boolean): Boolean =
    getOrDefault(MatrixAdditionalVerticalTransformation.NAME, MatrixAdditionalVerticalTransformation.CLASS, default)

fun Parameters.getMatrixThresholdValue(): Double =
    get(MatrixThresholdValue.NAME, MatrixThresholdValue.CLASS)

fun Parameters.getMatrixThresholdValueOrNull(): Double? =
    getOrNull(MatrixThresholdValue.NAME, MatrixThresholdValue.CLASS)

fun Parameters.getMatrixThresholdValueOrDefault(default: Double): Double =
    getOrDefault(MatrixThresholdValue.NAME, MatrixThresholdValue.CLASS, default)

fun Parameters.getMatrixThresholdMaxVal(): Double =
    get(MatrixThresholdMaxVal.NAME, MatrixThresholdMaxVal.CLASS)

fun Parameters.getMatrixThresholdMaxValOrNull(): Double? =
    getOrNull(MatrixThresholdMaxVal.NAME, MatrixThresholdMaxVal.CLASS)

fun Parameters.getMatrixThresholdMaxValOrDefault(default: Double): Double =
    getOrDefault(MatrixThresholdMaxVal.NAME, MatrixThresholdMaxVal.CLASS, default)

fun Parameters.getMatrixKernelSizeWidth(): Double =
    get(MatrixKernelSizeWidth.NAME, MatrixKernelSizeWidth.CLASS)

fun Parameters.getMatrixKernelSizeWidthOrNull(): Double? =
    getOrNull(MatrixKernelSizeWidth.NAME, MatrixKernelSizeWidth.CLASS)

fun Parameters.getMatrixKernelSizeWidthOrDefault(default: Double): Double =
    getOrDefault(MatrixKernelSizeWidth.NAME, MatrixKernelSizeWidth.CLASS, default)

fun Parameters.getMatrixKernelSizeHeight(): Double =
    get(MatrixKernelSizeHeight.NAME, MatrixKernelSizeHeight.CLASS)

fun Parameters.getMatrixKernelSizeHeightOrNull(): Double? =
    getOrNull(MatrixKernelSizeHeight.NAME, MatrixKernelSizeHeight.CLASS)

fun Parameters.getMatrixKernelSizeHeightOrDefault(default: Double): Double =
    getOrDefault(MatrixKernelSizeHeight.NAME, MatrixKernelSizeHeight.CLASS, default)

fun Parameters.getMatrixErosionsIterations(): Int =
    get(MatrixErosionsIterations.NAME, MatrixErosionsIterations.CLASS)

fun Parameters.getMatrixErosionsIterationsOrNull(): Int? =
    getOrNull(MatrixErosionsIterations.NAME, MatrixErosionsIterations.CLASS)

fun Parameters.getMatrixErosionsIterationsOrDefault(default: Int): Int =
    getOrDefault(MatrixErosionsIterations.NAME, MatrixErosionsIterations.CLASS, default)

fun Parameters.getMatrixDilationsIterations(): Int =
    get(MatrixDilationsIterations.NAME, MatrixDilationsIterations.CLASS)

fun Parameters.getMatrixDilationsIterationsOrNull(): Int? =
    getOrNull(MatrixDilationsIterations.NAME, MatrixDilationsIterations.CLASS)

fun Parameters.getMatrixDilationsIterationsOrDefault(default: Int): Int =
    getOrDefault(MatrixDilationsIterations.NAME, MatrixDilationsIterations.CLASS, default)