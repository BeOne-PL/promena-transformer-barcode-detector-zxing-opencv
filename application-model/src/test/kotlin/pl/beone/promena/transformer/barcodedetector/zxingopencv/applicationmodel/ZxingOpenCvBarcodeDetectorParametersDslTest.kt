package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.QR_CODE

class ZxingOpenCvBarcodeDetectorParametersDslTest {

    companion object {
        private val formats: List<String> = listOf(QR_CODE.value)
        private const val regexFilter: String = ".*"
        private const val linearRotationThresholdDegrees: Int = 10
        private const val linearAdditionalVerticalTransformation: Boolean = false
        private const val linearThresholdValue: Double = 100.0
        private const val linearThresholdMaxVal: Double = 255.0
        private const val linearKernelSizeWidth: Double = 15.0
        private const val linearKernelSizeHeight: Double = 1.0
        private const val linearErosionsIterations: Int = 10
        private const val linearDilationsIterations: Int = 15
        private const val matrixRotationThresholdDegrees: Int = 20
        private const val matrixAdditionalVerticalTransformation: Boolean = true
        private const val matrixThresholdValue: Double = 50.0
        private const val matrixThresholdMaxVal: Double = 200.0
        private const val matrixKernelSizeWidth: Double = 20.0
        private const val matrixKernelSizeHeight: Double = 30.0
        private const val matrixErosionsIterations: Int = 30
        private const val matrixDilationsIterations: Int = 40
    }

    @Test
    fun `zxingOpenCvBarcodeDetectorParameters _ default parameters`() {
        with(zxingOpenCvBarcodeDetectorParameters()) {
            shouldThrow<NoSuchElementException> { getFormats() }
            getFormatsOrNull() shouldBe null
            getFormatsOrDefault(formats) shouldBe formats

            shouldThrow<NoSuchElementException> { getRegexFilter() }
            getRegexFilterOrNull() shouldBe null
            getRegexFilterOrDefault(regexFilter) shouldBe regexFilter

            // ***

            shouldThrow<NoSuchElementException> { getLinearRotationThresholdDegrees() }
            getLinearRotationThresholdDegreesOrNull() shouldBe null
            getLinearRotationThresholdDegreesOrDefault(linearRotationThresholdDegrees) shouldBe linearRotationThresholdDegrees

            shouldThrow<NoSuchElementException> { getLinearAdditionalVerticalTransformation() }
            getLinearAdditionalVerticalTransformationOrNull() shouldBe null
            getLinearAdditionalVerticalTransformationOrDefault(linearAdditionalVerticalTransformation) shouldBe linearAdditionalVerticalTransformation

            shouldThrow<NoSuchElementException> { getLinearThresholdValue() }
            getLinearThresholdValueOrNull() shouldBe null
            getLinearThresholdValueOrDefault(linearThresholdValue) shouldBe linearThresholdValue

            shouldThrow<NoSuchElementException> { getLinearThresholdMaxVal() }
            getLinearThresholdMaxValOrNull() shouldBe null
            getLinearThresholdMaxValOrDefault(linearThresholdMaxVal) shouldBe linearThresholdMaxVal

            shouldThrow<NoSuchElementException> { getLinearKernelSizeWidth() }
            getLinearKernelSizeWidthOrNull() shouldBe null
            getLinearKernelSizeWidthOrDefault(linearKernelSizeWidth) shouldBe linearKernelSizeWidth

            shouldThrow<NoSuchElementException> { getLinearKernelSizeHeight() }
            getLinearKernelSizeHeightOrNull() shouldBe null
            getLinearKernelSizeHeightOrDefault(linearKernelSizeHeight) shouldBe linearKernelSizeHeight

            shouldThrow<NoSuchElementException> { getLinearErosionsIterations() }
            getLinearErosionsIterationsOrNull() shouldBe null
            getLinearErosionsIterationsOrDefault(linearErosionsIterations) shouldBe linearErosionsIterations

            shouldThrow<NoSuchElementException> { getLinearDilationsIterations() }
            getLinearDilationsIterationsOrNull() shouldBe null
            getLinearDilationsIterationsOrDefault(linearDilationsIterations) shouldBe linearDilationsIterations

            // ***

            shouldThrow<NoSuchElementException> { getMatrixRotationThresholdDegrees() }
            getMatrixRotationThresholdDegreesOrNull() shouldBe null
            getMatrixRotationThresholdDegreesOrDefault(matrixRotationThresholdDegrees) shouldBe matrixRotationThresholdDegrees

            shouldThrow<NoSuchElementException> { getMatrixAdditionalVerticalTransformation() }
            getMatrixAdditionalVerticalTransformationOrNull() shouldBe null
            getMatrixAdditionalVerticalTransformationOrDefault(matrixAdditionalVerticalTransformation) shouldBe matrixAdditionalVerticalTransformation

            shouldThrow<NoSuchElementException> { getMatrixThresholdValue() }
            getMatrixThresholdValueOrNull() shouldBe null
            getMatrixThresholdValueOrDefault(matrixThresholdValue) shouldBe matrixThresholdValue

            shouldThrow<NoSuchElementException> { getMatrixThresholdMaxVal() }
            getMatrixThresholdMaxValOrNull() shouldBe null
            getMatrixThresholdMaxValOrDefault(matrixThresholdMaxVal) shouldBe matrixThresholdMaxVal

            shouldThrow<NoSuchElementException> { getMatrixKernelSizeWidth() }
            getMatrixKernelSizeWidthOrNull() shouldBe null
            getMatrixKernelSizeWidthOrDefault(matrixKernelSizeWidth) shouldBe matrixKernelSizeWidth

            shouldThrow<NoSuchElementException> { getMatrixKernelSizeHeight() }
            getMatrixKernelSizeHeightOrNull() shouldBe null
            getMatrixKernelSizeHeightOrDefault(matrixKernelSizeHeight) shouldBe matrixKernelSizeHeight

            shouldThrow<NoSuchElementException> { getMatrixErosionsIterations() }
            getMatrixErosionsIterationsOrNull() shouldBe null
            getMatrixErosionsIterationsOrDefault(matrixErosionsIterations) shouldBe matrixErosionsIterations

            shouldThrow<NoSuchElementException> { getMatrixDilationsIterations() }
            getMatrixDilationsIterationsOrNull() shouldBe null
            getMatrixDilationsIterationsOrDefault(matrixDilationsIterations) shouldBe matrixDilationsIterations
        }
    }

    @Test
    fun `zxingOpenCvBarcodeDetectorParameters _ all parameters`() {
        with(
            zxingOpenCvBarcodeDetectorParameters(
                formats = formats,
                regexFilter = regexFilter,
                linearRotationThresholdDegrees = linearRotationThresholdDegrees,
                linearAdditionalVerticalTransformation = linearAdditionalVerticalTransformation,
                linearThresholdValue = linearThresholdValue,
                linearThresholdMaxVal = linearThresholdMaxVal,
                linearKernelSizeWidth = linearKernelSizeWidth,
                linearKernelSizeHeight = linearKernelSizeHeight,
                linearErosionsIterations = linearErosionsIterations,
                linearDilationsIterations = linearDilationsIterations,
                matrixRotationThresholdDegrees = matrixRotationThresholdDegrees,
                matrixAdditionalVerticalTransformation = matrixAdditionalVerticalTransformation,
                matrixThresholdValue = matrixThresholdValue,
                matrixThresholdMaxVal = matrixThresholdMaxVal,
                matrixKernelSizeWidth = matrixKernelSizeWidth,
                matrixKernelSizeHeight = matrixKernelSizeHeight,
                matrixErosionsIterations = matrixErosionsIterations,
                matrixDilationsIterations = matrixDilationsIterations
            )
        ) {
            getFormats() shouldBe formats
            getRegexFilter() shouldBe regexFilter

            // ***

            getLinearRotationThresholdDegrees() shouldBe linearRotationThresholdDegrees
            getLinearAdditionalVerticalTransformation() shouldBe linearAdditionalVerticalTransformation
            getLinearThresholdValue() shouldBe linearThresholdValue
            getLinearThresholdMaxVal() shouldBe linearThresholdMaxVal
            getLinearKernelSizeWidth() shouldBe linearKernelSizeWidth
            getLinearKernelSizeHeight() shouldBe linearKernelSizeHeight
            getLinearErosionsIterations() shouldBe linearErosionsIterations
            getLinearDilationsIterations() shouldBe linearDilationsIterations

            // ***

            getMatrixRotationThresholdDegrees() shouldBe matrixRotationThresholdDegrees
            getMatrixAdditionalVerticalTransformation() shouldBe matrixAdditionalVerticalTransformation
            getMatrixThresholdValue() shouldBe matrixThresholdValue
            getMatrixThresholdMaxVal() shouldBe matrixThresholdMaxVal
            getMatrixKernelSizeWidth() shouldBe matrixKernelSizeWidth
            getMatrixKernelSizeHeight() shouldBe matrixKernelSizeHeight
            getMatrixErosionsIterations() shouldBe matrixErosionsIterations
            getMatrixDilationsIterations() shouldBe matrixDilationsIterations
        }
    }
}