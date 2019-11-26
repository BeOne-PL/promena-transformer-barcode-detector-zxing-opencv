package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.support

import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import pl.beone.promena.transformer.applicationmodel.exception.transformer.TransformationNotSupportedException
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat.QR_CODE
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorSupport.ParametersSupport.isSupported
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.zxingOpenCvBarcodeDetectorParameters

class ZxingOpenCvBarcodeDetectorParametersSupportTest {

    @Test
    fun `isSupported _ default parameters`() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters())
        }
    }

    @Test
    fun `isSupported _ all parameters`() {
        shouldNotThrow<TransformationNotSupportedException> {
            isSupported(
                zxingOpenCvBarcodeDetectorParameters(
                    formats = listOf(QR_CODE.format),
                    regexFilter = ".*",
                    linearRotationThresholdDegrees = 10,
                    linearAdditionalVerticalTransformation = false,
                    linearThresholdValue = 100.0,
                    linearThresholdMaxVal = 255.0,
                    linearKernelSizeWidth = 15.0,
                    linearKernelSizeHeight = 1.0,
                    linearErosionsIterations = 10,
                    linearDilationsIterations = 15,
                    matrixRotationThresholdDegrees = 10,
                    matrixAdditionalVerticalTransformation = true,
                    matrixThresholdValue = 200.0,
                    matrixThresholdMaxVal = 210.0,
                    matrixKernelSizeWidth = 20.0,
                    matrixKernelSizeHeight = 25.0,
                    matrixErosionsIterations = 5,
                    matrixDilationsIterations = 6
                )
            )
        }
    }

    @Test
    fun `isSupported _ formats`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(formats = listOf(QR_CODE.format, "absent")))
        }
    }

    @Test
    fun `isSupported _ rotationThresholdDegrees`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearRotationThresholdDegrees = 0))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearRotationThresholdDegrees = 181))
        }

        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixRotationThresholdDegrees = 0))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixRotationThresholdDegrees = 181))
        }
    }

    @Test
    fun `isSupported _ thresholdValue`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearThresholdValue = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearThresholdValue = 255.01))
        }

        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixThresholdValue = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixThresholdValue = 255.01))
        }
    }

    @Test
    fun `isSupported _ thresholdMaxVal`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearThresholdMaxVal = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearThresholdMaxVal = 255.01))
        }

        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixThresholdMaxVal = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixThresholdMaxVal = 255.01))
        }
    }

    @Test
    fun `isSupported _ kernelSizeWidth`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearKernelSizeWidth = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearKernelSizeWidth = 1000.01))
        }

        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixKernelSizeWidth = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixKernelSizeWidth = 1000.01))
        }
    }

    @Test
    fun `isSupported _ kernelSizeHeight`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearKernelSizeHeight = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearKernelSizeHeight = 1000.01))
        }

        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixKernelSizeHeight = 0.99))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixKernelSizeHeight = 1000.01))
        }
    }

    @Test
    fun `isSupported _ erosionsIterations`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearErosionsIterations = 0))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearErosionsIterations = 1001))
        }

        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixErosionsIterations = 0))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixErosionsIterations = 1001))
        }
    }

    @Test
    fun `isSupported _ dilationsIterations`() {
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearDilationsIterations = 0))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(linearDilationsIterations = 1001))
        }

        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixDilationsIterations = 0))
        }
        shouldThrow<TransformationNotSupportedException> {
            isSupported(zxingOpenCvBarcodeDetectorParameters(matrixDilationsIterations = 1001))
        }
    }
}