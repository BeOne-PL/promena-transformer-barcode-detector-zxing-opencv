package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.opencv.core.Core
import org.opencv.core.Core.bitwise_or
import org.opencv.core.Mat
import org.opencv.highgui.HighGui
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDecoder.DecodedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import java.awt.image.BufferedImage

class BarcodeDetector(
    barcodeFormats: List<ZxingOpenCvBarcodeDetectorBarcodeFormat>,
    rotationThresholdDegrees: Int,
    private val additionalVerticalTransformation: Boolean,
    private val storeImmediateMatrices: Boolean,
    private val thresholdValue: Double,
    private val thresholdMaxVal: Double,
    kernelSizeWidth: Double,
    kernelSizeHeight: Double,
    private val erosionsIterations: Int,
    private val dilationsIterations: Int
) {

    data class DetectedBarcode(
        val decodedBarcode: DecodedBarcode,
        val foundContour: FoundContour
    )

    private val decoder = BarcodeDecoder(barcodeFormats, rotationThresholdDegrees)

    private val contoursFinder = createContoursFinder(kernelSizeWidth, kernelSizeHeight)
    private val verticalContoursFinder = createContoursFinder(kernelSizeHeight, kernelSizeWidth)

    private fun createContoursFinder(kernelSizeWidth: Double, kernelSizeHeight: Double): ContourVerticesFinder =
        ContourVerticesFinder(
            storeImmediateMatrices,
            thresholdValue,
            thresholdMaxVal,
            kernelSizeWidth,
            kernelSizeHeight,
            erosionsIterations,
            dilationsIterations
        )

    fun detect(imageMatrix: Mat): List<DetectedBarcode> =
        (detect(imageMatrix, contoursFinder) +
                if (additionalVerticalTransformation) detect(imageMatrix, verticalContoursFinder) else emptyList())
            .distinctBy { it.decodedBarcode }

    fun getImmediateMatrices(): ContourVerticesFinder.ImmediateMatrices {
        if (additionalVerticalTransformation) {
            val contoursFinder = contoursFinder.getImmediateMatrices()
            val verticalContoursFinder = verticalContoursFinder.getImmediateMatrices()
            return ContourVerticesFinder.ImmediateMatrices(
                merge(contoursFinder.threshold, verticalContoursFinder.threshold),
                merge(contoursFinder.closingKernel, verticalContoursFinder.closingKernel),
                merge(contoursFinder.erosionsAndDilations, verticalContoursFinder.erosionsAndDilations),
                contoursFinder.contours + verticalContoursFinder.contours
            )
        } else {
            with(contoursFinder.getImmediateMatrices()) {
                return ContourVerticesFinder.ImmediateMatrices(
                    threshold,
                    closingKernel,
                    erosionsAndDilations,
                    contours
                )
            }
        }
    }

    private fun merge(matFirst: Mat, matSecond: Mat): Mat =
        createMatrix { bitwise_or(matFirst, matSecond, it) }

    private fun detect(imageMatrix: Mat, contourVerticesFinder: ContourVerticesFinder): List<DetectedBarcode> =
        contourVerticesFinder.find(imageMatrix)
            .map { foundContour -> foundContour to ContourVerticesCutter.cutOut(imageMatrix, foundContour).toBufferedImage() }
            .mapNotNull { (foundContour, bufferedImage) -> detectBarcode(foundContour, bufferedImage, AngleRadiansCalculator.calculate(foundContour)) }

    private fun detectBarcode(foundContour: FoundContour, bufferedImage: BufferedImage, possibleAngleRadians: List<Double>): DetectedBarcode? =
        try {
            DetectedBarcode(decoder.decode(bufferedImage, possibleAngleRadians), foundContour)
        } catch (e: NoSuchElementException) {
            null
        }

    private fun Mat.toBufferedImage(): BufferedImage =
        HighGui.toBufferedImage(this) as BufferedImage
}