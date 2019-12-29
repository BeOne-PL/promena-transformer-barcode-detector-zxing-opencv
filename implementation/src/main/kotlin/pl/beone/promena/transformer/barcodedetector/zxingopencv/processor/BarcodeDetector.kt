package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.bytedeco.opencv.global.opencv_core.bitwise_or
import org.bytedeco.opencv.opencv_core.Mat
import org.bytedeco.opencv.opencv_core.MatVector
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDecoder.DecodedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.createMatrix
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.toBufferedImage
import java.awt.image.BufferedImage

class BarcodeDetector(
    formats: List<ZxingOpenCvBarcodeDetectorFormat>,
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

    private val decoder = BarcodeDecoder(formats, rotationThresholdDegrees)

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
            .distinctBy(DetectedBarcode::decodedBarcode)

    fun getImmediateMatrices(): ContourVerticesFinder.ImmediateMatrices =
        if (additionalVerticalTransformation) {
            val contoursFinder = contoursFinder.getImmediateMatrices()
            val verticalContoursFinder = verticalContoursFinder.getImmediateMatrices()
            ContourVerticesFinder.ImmediateMatrices(
                merge(contoursFinder.threshold, verticalContoursFinder.threshold),
                merge(contoursFinder.closingKernel, verticalContoursFinder.closingKernel),
                merge(contoursFinder.erosionsAndDilations, verticalContoursFinder.erosionsAndDilations),
                MatVector().also { matrixVector ->
                    contoursFinder.contours.get().forEach { matrixVector.push_back(it) }
                    verticalContoursFinder.contours.get().forEach { matrixVector.push_back(it) }
                }
            )
        } else {
            with(contoursFinder.getImmediateMatrices()) {
                ContourVerticesFinder.ImmediateMatrices(threshold, closingKernel, erosionsAndDilations, contours)
            }
        }

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

    private fun merge(matFirst: Mat, matSecond: Mat): Mat =
        createMatrix { bitwise_or(matFirst, matSecond, it) }
}