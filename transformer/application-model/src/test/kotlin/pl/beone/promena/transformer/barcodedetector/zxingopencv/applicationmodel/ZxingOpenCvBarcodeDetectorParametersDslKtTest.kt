package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat.QR_CODE

class ZxingOpenCvBarcodeDetectorParametersDslKtTest {

    companion object {
        private val formats: List<String> = listOf(QR_CODE.format)
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
        zxingOpenCvBarcodeDetectorParameters().let {
            shouldThrow<NoSuchElementException> {
                it.getFormats()
            }
            it.getFormatsOrNull() shouldBe null
            it.getFormatsOrDefault(formats) shouldBe formats

            shouldThrow<NoSuchElementException> {
                it.getRegexFilter()
            }
            it.getRegexFilterOrNull() shouldBe null
            it.getRegexFilterOrDefault(regexFilter) shouldBe regexFilter

            // ***

            shouldThrow<NoSuchElementException> {
                it.getLinearRotationThresholdDegrees()
            }
            it.getLinearRotationThresholdDegreesOrNull() shouldBe null
            it.getLinearRotationThresholdDegreesOrDefault(linearRotationThresholdDegrees) shouldBe linearRotationThresholdDegrees

            shouldThrow<NoSuchElementException> {
                it.getLinearAdditionalVerticalTransformation()
            }
            it.getLinearAdditionalVerticalTransformationOrNull() shouldBe null
            it.getLinearAdditionalVerticalTransformationOrDefault(linearAdditionalVerticalTransformation) shouldBe linearAdditionalVerticalTransformation

            shouldThrow<NoSuchElementException> {
                it.getLinearThresholdValue()
            }
            it.getLinearThresholdValueOrNull() shouldBe null
            it.getLinearThresholdValueOrDefault(linearThresholdValue) shouldBe linearThresholdValue

            shouldThrow<NoSuchElementException> {
                it.getLinearThresholdMaxVal()
            }
            it.getLinearThresholdMaxValOrNull() shouldBe null
            it.getLinearThresholdMaxValOrDefault(linearThresholdMaxVal) shouldBe linearThresholdMaxVal

            shouldThrow<NoSuchElementException> {
                it.getLinearKernelSizeWidth()
            }
            it.getLinearKernelSizeWidthOrNull() shouldBe null
            it.getLinearKernelSizeWidthOrDefault(linearKernelSizeWidth) shouldBe linearKernelSizeWidth

            shouldThrow<NoSuchElementException> {
                it.getLinearKernelSizeHeight()
            }
            it.getLinearKernelSizeHeightOrNull() shouldBe null
            it.getLinearKernelSizeHeightOrDefault(linearKernelSizeHeight) shouldBe linearKernelSizeHeight

            shouldThrow<NoSuchElementException> {
                it.getLinearErosionsIterations()
            }
            it.getLinearErosionsIterationsOrNull() shouldBe null
            it.getLinearErosionsIterationsOrDefault(linearErosionsIterations) shouldBe linearErosionsIterations

            shouldThrow<NoSuchElementException> {
                it.getLinearDilationsIterations()
            }
            it.getLinearDilationsIterationsOrNull() shouldBe null
            it.getLinearDilationsIterationsOrDefault(linearDilationsIterations) shouldBe linearDilationsIterations

            // ***

            shouldThrow<NoSuchElementException> {
                it.getMatrixRotationThresholdDegrees()
            }
            it.getMatrixRotationThresholdDegreesOrNull() shouldBe null
            it.getMatrixRotationThresholdDegreesOrDefault(matrixRotationThresholdDegrees) shouldBe matrixRotationThresholdDegrees

            shouldThrow<NoSuchElementException> {
                it.getMatrixAdditionalVerticalTransformation()
            }
            it.getMatrixAdditionalVerticalTransformationOrNull() shouldBe null
            it.getMatrixAdditionalVerticalTransformationOrDefault(matrixAdditionalVerticalTransformation) shouldBe matrixAdditionalVerticalTransformation

            shouldThrow<NoSuchElementException> {
                it.getMatrixThresholdValue()
            }
            it.getMatrixThresholdValueOrNull() shouldBe null
            it.getMatrixThresholdValueOrDefault(matrixThresholdValue) shouldBe matrixThresholdValue

            shouldThrow<NoSuchElementException> {
                it.getMatrixThresholdMaxVal()
            }
            it.getMatrixThresholdMaxValOrNull() shouldBe null
            it.getMatrixThresholdMaxValOrDefault(matrixThresholdMaxVal) shouldBe matrixThresholdMaxVal

            shouldThrow<NoSuchElementException> {
                it.getMatrixKernelSizeWidth()
            }
            it.getMatrixKernelSizeWidthOrNull() shouldBe null
            it.getMatrixKernelSizeWidthOrDefault(matrixKernelSizeWidth) shouldBe matrixKernelSizeWidth

            shouldThrow<NoSuchElementException> {
                it.getMatrixKernelSizeHeight()
            }
            it.getMatrixKernelSizeHeightOrNull() shouldBe null
            it.getMatrixKernelSizeHeightOrDefault(matrixKernelSizeHeight) shouldBe matrixKernelSizeHeight

            shouldThrow<NoSuchElementException> {
                it.getMatrixErosionsIterations()
            }
            it.getMatrixErosionsIterationsOrNull() shouldBe null
            it.getMatrixErosionsIterationsOrDefault(matrixErosionsIterations) shouldBe matrixErosionsIterations

            shouldThrow<NoSuchElementException> {
                it.getMatrixDilationsIterations()
            }
            it.getMatrixDilationsIterationsOrNull() shouldBe null
            it.getMatrixDilationsIterationsOrDefault(matrixDilationsIterations) shouldBe matrixDilationsIterations
        }
    }

    @Test
    fun `zxingOpenCvBarcodeDetectorParameters _ all parameters`() {
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
        ).let {
            it.getFormats() shouldBe formats
            it.getRegexFilter() shouldBe regexFilter

            // ***

            it.getLinearRotationThresholdDegrees() shouldBe linearRotationThresholdDegrees
            it.getLinearAdditionalVerticalTransformation() shouldBe linearAdditionalVerticalTransformation
            it.getLinearThresholdValue() shouldBe linearThresholdValue
            it.getLinearThresholdMaxVal() shouldBe linearThresholdMaxVal
            it.getLinearKernelSizeWidth() shouldBe linearKernelSizeWidth
            it.getLinearKernelSizeHeight() shouldBe linearKernelSizeHeight
            it.getLinearErosionsIterations() shouldBe linearErosionsIterations
            it.getLinearDilationsIterations() shouldBe linearDilationsIterations

            // ***

            it.getMatrixRotationThresholdDegrees() shouldBe matrixRotationThresholdDegrees
            it.getMatrixAdditionalVerticalTransformation() shouldBe matrixAdditionalVerticalTransformation
            it.getMatrixThresholdValue() shouldBe matrixThresholdValue
            it.getMatrixThresholdMaxVal() shouldBe matrixThresholdMaxVal
            it.getMatrixKernelSizeWidth() shouldBe matrixKernelSizeWidth
            it.getMatrixKernelSizeHeight() shouldBe matrixKernelSizeHeight
            it.getMatrixErosionsIterations() shouldBe matrixErosionsIterations
            it.getMatrixDilationsIterations() shouldBe matrixDilationsIterations
        }
    }
}