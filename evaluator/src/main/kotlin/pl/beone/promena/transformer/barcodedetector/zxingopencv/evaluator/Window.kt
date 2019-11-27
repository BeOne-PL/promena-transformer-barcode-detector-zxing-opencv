package pl.beone.promena.transformer.barcodedetector.zxingopencv.evaluator

import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.highgui.HighGui
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDecoder.DecodedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.ImmediateMatrices
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.createMatrix
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.toMatOfPoint
import java.awt.Image
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.*
import javax.swing.JOptionPane.*
import kotlin.math.min
import kotlin.streams.toList

class Window : JFrame("") {

    class BarcodeDetectorState(
        var rotationThresholdDegrees: Int,
        var additionalVerticalTransformation: Boolean,
        var thresholdValue: Double,
        var thresholdMaxVal: Double,
        var kernelSizeWidth: Double,
        var kernelSizeHeight: Double,
        var erosionsIterations: Int,
        var dilationsIterations: Int
    )

    private val content = Content().also { contentPane = it.panel }

    private var imagePaths = emptyList<Path>()
    private var imagePathsIndex = 0

    private val linearBarcodeDetectorState =
        BarcodeDetectorState(
            rotationThresholdDegrees = 1,
            additionalVerticalTransformation = true,
            thresholdValue = 150.0,
            thresholdMaxVal = 255.0,
            kernelSizeWidth = 25.0,
            kernelSizeHeight = 1.0,
            erosionsIterations = 25,
            dilationsIterations = 25
        )
    private val linearBarcodeFormats = listOf(CODABAR, UPC_A, EAN_13, CODE_39, CODE_128, ITF)

    private val matrixBarcodeDetectorState =
        BarcodeDetectorState(
            rotationThresholdDegrees = 1,
            additionalVerticalTransformation = false,
            thresholdValue = 100.0,
            thresholdMaxVal = 255.0,
            kernelSizeWidth = 20.0,
            kernelSizeHeight = 20.0,
            erosionsIterations = 20,
            dilationsIterations = 20
        )
    private val matrixBarcodeFormats = listOf(QR_CODE, PDF417, AZTEC_CODE, DATA_MATRIX, MAXI_CODE)

    private var currentBarcodeDetectorState = linearBarcodeDetectorState
    private var currentBarcodeFormats = linearBarcodeFormats

    init {
        setMaximizedAndNotResizable()

        addLoadAction()
        addApplyAction()
        addNextImageAction()

        addRadioButtonActions()

        setCurrentStateInBarcodeDetectorComponents()
        addBarcodeDetectorComponentsActions()

        resetAllImageLabelsAndBarcodes()
    }

    private fun setMaximizedAndNotResizable() {
        extendedState = extendedState or MAXIMIZED_BOTH
        isResizable = false

        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    }

    private fun addLoadAction() {
        content.loadButton.addActionListener {
            imagePaths = loadImagePaths(Paths.get(content.imageOrFolderPathTextField.text))
            imagePathsIndex = 0

            update()
        }
    }

    private fun loadImagePaths(path: Path): List<Path> {
        if (path.toString().isEmpty()) {
            throw showErrorDialogAndThrowException("Path can't be empty")
        }

        return when {
            Files.isDirectory(path) -> Files.walk(path)
                .filter { Files.isRegularFile(it) }
                .toList()
                .filter(::isImage)
                .sortedBy { it.fileName }
                .also { paths ->
                    if (paths.isEmpty()) {
                        throw showErrorDialogAndThrowException("Chosen folder or subfolders don't contain any image")
                    }
                }

            Files.isRegularFile(path) -> listOf(path)

            else -> {
                throw showErrorDialogAndThrowException("Chosen path isn't a file or a folder")
            }
        }
    }

    private fun showErrorDialogAndThrowException(message: String): IllegalStateException {
        showMessageDialog(this, message, "Error", ERROR_MESSAGE)
        return IllegalStateException(message)
    }

    private fun addApplyAction() {
        content.applyButton.addActionListener {
            update()
        }
    }

    private fun addNextImageAction() {
        content.nextImageButton.addActionListener {
            imagePathsIndex++

            update()
        }
    }

    private fun addBarcodeDetectorComponentsActions() {
        with(content) {
            rotationThresholdDegreesSpinner.addChangeListener {
                currentBarcodeDetectorState.rotationThresholdDegrees = rotationThresholdDegreesSpinner.intValue()
            }

            additionalVerticalTransformationCheckBox.addActionListener {
                currentBarcodeDetectorState.additionalVerticalTransformation = additionalVerticalTransformationCheckBox.isSelected
            }

            thresholdValueSpinner.addChangeListener {
                currentBarcodeDetectorState.thresholdValue = thresholdValueSpinner.doubleValue()
            }
            thresholdMaxValueSpinner.addChangeListener {
                currentBarcodeDetectorState.thresholdMaxVal = thresholdMaxValueSpinner.doubleValue()
            }

            closingKernelSizeWidthSpinner.addChangeListener {
                currentBarcodeDetectorState.kernelSizeWidth = closingKernelSizeWidthSpinner.doubleValue()
            }
            closingKernelSizeHeightSpinner.addChangeListener {
                currentBarcodeDetectorState.kernelSizeHeight = closingKernelSizeHeightSpinner.doubleValue()
            }

            erosionsIterationsSpinner.addChangeListener {
                currentBarcodeDetectorState.erosionsIterations = erosionsIterationsSpinner.intValue()
            }
            dilationsIterationsSpinner.addChangeListener {
                currentBarcodeDetectorState.dilationsIterations = dilationsIterationsSpinner.intValue()
            }
        }
    }

    private fun addRadioButtonActions() {
        content.linearRadioButton.addActionListener {
            currentBarcodeDetectorState = linearBarcodeDetectorState
            currentBarcodeFormats = linearBarcodeFormats
            setCurrentStateInBarcodeDetectorComponents()
        }
        content.matrixRadioButton.addActionListener {
            currentBarcodeDetectorState = matrixBarcodeDetectorState
            currentBarcodeFormats = matrixBarcodeFormats
            setCurrentStateInBarcodeDetectorComponents()
        }
    }

    private fun setCurrentStateInBarcodeDetectorComponents() {
        with(content) {
            rotationThresholdDegreesSpinner.setEditorWithoutGroupingAndModel(currentBarcodeDetectorState.rotationThresholdDegrees, 1, 360)

            additionalVerticalTransformationCheckBox.isSelected = currentBarcodeDetectorState.additionalVerticalTransformation

            thresholdValueSpinner.setEditorWithoutGroupingAndModel(currentBarcodeDetectorState.thresholdValue, 1.0, 255.0)
            thresholdMaxValueSpinner.setEditorWithoutGroupingAndModel(currentBarcodeDetectorState.thresholdMaxVal, 1.0, 255.0)

            closingKernelSizeWidthSpinner.setEditorWithoutGroupingAndModel(currentBarcodeDetectorState.kernelSizeWidth, 1.0, 1000.0)
            closingKernelSizeHeightSpinner.setEditorWithoutGroupingAndModel(currentBarcodeDetectorState.kernelSizeHeight, 1.0, 1000.0)

            erosionsIterationsSpinner.setEditorWithoutGroupingAndModel(currentBarcodeDetectorState.erosionsIterations, 1, 1000)
            dilationsIterationsSpinner.setEditorWithoutGroupingAndModel(currentBarcodeDetectorState.dilationsIterations, 1, 1000)
        }
    }

    private fun update() {
        if (imagePaths.isEmpty()) {
            showMessageDialog(this, "Load image or folder", "Information", INFORMATION_MESSAGE)
            return
        }

        val path = imagePaths[imagePathsIndex % imagePaths.size].toString()
        try {
            val imageMatrix = Imgcodecs.imread(path)

            val barcodeDetector = BarcodeDetector(
                barcodeFormats = currentBarcodeFormats,
                rotationThresholdDegrees = content.rotationThresholdDegreesSpinner.intValue(),
                additionalVerticalTransformation = content.additionalVerticalTransformationCheckBox.isSelected,
                storeImmediateMatrices = true,
                thresholdValue = content.thresholdValueSpinner.doubleValue(),
                thresholdMaxVal = content.thresholdMaxValueSpinner.doubleValue(),
                kernelSizeWidth = content.closingKernelSizeWidthSpinner.doubleValue(),
                kernelSizeHeight = content.closingKernelSizeHeightSpinner.doubleValue(),
                erosionsIterations = content.erosionsIterationsSpinner.intValue(),
                dilationsIterations = content.dilationsIterationsSpinner.intValue()
            )

            barcodeDetector
                .detect(imageMatrix)
                .also { showImmediateMatrices(imageMatrix, barcodeDetector.getImmediateMatrices()) }
                .also { drawBarcodeContoursOnMatrix(imageMatrix, it.map(BarcodeDetector.DetectedBarcode::foundContour)) }
                .also { showBarcodesOnList(it.map(BarcodeDetector.DetectedBarcode::decodedBarcode)) }

            title = path
        } catch (e: Throwable) {
            showMessageDialog(this, "An error occurred during detecting barcodes in <$path> file. Check logs for more details", "Error", ERROR_MESSAGE)
            resetAllImageLabelsAndBarcodes()
            title = ""

            throw RuntimeException("Couldn't detect barcodes in <$path> file", e)
        }
    }

    private fun showImmediateMatrices(imageMatrix: Mat, immediateMatrices: ImmediateMatrices) {
        immediateMatrices
            .also { showImage(content.thresholdImageLabel, it.threshold) }
            .also { showImage(content.closingKernelImageLabel, it.closingKernel) }
            .also { showImage(content.erosionsAndDilationsLabel, it.erosionsAndDilations) }
            .also { showImage(content.contoursImageLabel, drawContoursOnMatrix(imageMatrix, it.contours.map(Mat::toMatOfPoint))) }
    }

    private fun showImage(imageLabel: JLabel, image: Mat) {
        imageLabel.text = ""

        val bufferedImage = HighGui.toBufferedImage(image) as BufferedImage
        val scale = min((imageLabel.parent.width - 20).toDouble() / bufferedImage.width, (imageLabel.parent.height - 20).toDouble() / bufferedImage.height)

        bufferedImage
            .getScaledInstance((bufferedImage.width * scale).toInt(), (bufferedImage.height * scale).toInt(), Image.SCALE_DEFAULT)
            .let { imageLabel.icon = ImageIcon(it) }
    }

    private fun resetAllImageLabelsAndBarcodes() {
        with(content) {
            resetImageLabel(thresholdImageLabel)
            resetImageLabel(closingKernelImageLabel)
            resetImageLabel(erosionsAndDilationsLabel)
            resetImageLabel(contoursImageLabel)
            resetImageLabel(resultImageLabel)
            barcodeList.model = DefaultListModel<String>()
        }
    }

    private fun resetImageLabel(imageLabel: JLabel) {
        imageLabel.text = """Click on "Apply" to perform detection"""
        imageLabel.icon = null
    }

    private fun drawContoursOnMatrix(imageMatrix: Mat, matOfPoints: List<MatOfPoint>): Mat =
        createMatrix {
            imageMatrix.copyTo(it)
            Imgproc.drawContours(it, matOfPoints, -1, Scalar(0.0, 0.0, 255.0), 10)
        }

    private fun drawBarcodeContoursOnMatrix(imageMatrix: Mat, contourVertices: List<FoundContour>) {
        showImage(content.resultImageLabel, drawContoursOnMatrix(imageMatrix, contourVertices.map(::convertToMatOfPoint)))
    }

    private fun showBarcodesOnList(decodedBarcode: List<DecodedBarcode>) {
        content.barcodeList.model = DefaultListModel<String>().also { model -> model.addAll(decodedBarcode.map(DecodedBarcode::toString)) }
    }

    private fun isImage(path: Path): Boolean =
        try {
            Files.probeContentType(path)
                .split("/")
                .contains("image")
        } catch (e: Exception) {
            false
        }

    private fun JSpinner.setEditorWithoutGroupingAndModel(value: Double, minimum: Double, maximum: Double) {
        model = SpinnerNumberModel(value, minimum, maximum, 0.1)
        editor = JSpinner.NumberEditor(this)
    }

    private fun JSpinner.setEditorWithoutGroupingAndModel(value: Int, minimum: Int, maximum: Int) {
        model = SpinnerNumberModel(value, minimum, maximum, 1)
        editor = JSpinner.NumberEditor(this)
    }

    private fun JSpinner.doubleValue(): Double =
        value.toString().toDouble()

    private fun JSpinner.intValue(): Int =
        value.toString().toInt()

    private fun convertToMatOfPoint(contour: FoundContour): MatOfPoint =
        with(contour) {
            MatOfPoint(vertex.toPoint(), vertex2.toPoint(), vertex3.toPoint(), vertex4.toPoint())
        }

    private fun Vertex.toPoint(): Point =
        Point(x.toDouble(), y.toDouble())
}