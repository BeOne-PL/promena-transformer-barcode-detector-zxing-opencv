package pl.beone.promena.transformer.barcodedetector.zxingopencv

import io.kotlintest.matchers.collections.shouldHaveAtLeastSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit.jupiter.external.DockerExtension
import pl.beone.promena.transformer.barcodedetector.metadata.BarcodeDetectorMetadataGetter
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.zxingOpenCvBarcodeDetectorParameters
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.createZxingOpenCvBarcodeDetectorTransformer
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.test
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.validateBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.validateGeneralMetadata

@ExtendWith(DockerExtension::class)
class ZxingOpenCvBarcodeDetectorTransformerParametersTest {

    @Test
    fun transform_formats() {
        test(GENERAL_TEST, zxingOpenCvBarcodeDetectorParameters(formats = listOf(QR_CODE.value, CODE_128.value))) {
            with(BarcodeDetectorMetadataGetter(it).getBarcodes()) {
                this shouldHaveAtLeastSize 2
                validateBarcode(this, "01234567890abcdefg", CODE_128, 1)
                validateBarcode(this, "TEST123456", QR_CODE, 2)
            }

        }
    }

    @Test
    fun transform_regexFilter() {
        test(GENERAL_TEST, zxingOpenCvBarcodeDetectorParameters(regexFilter = "[a-zA-Z ]+")) {
            with(BarcodeDetectorMetadataGetter(it).getBarcodes()) {
                this shouldHaveAtLeastSize 4
                validateBarcode(this, "ABCDEF", PDF417, 2)
                validateBarcode(this, "Test Aztec", AZTEC_CODE, 2)
                validateBarcode(this, "Test Data Matrix", DATA_MATRIX, 2)
                validateBarcode(this, "Test MaxiCode", MAXI_CODE, 2)
            }
        }
    }

    @Test
    fun transform_linearAndMatrixParameters() {
        test(
            GENERAL_TEST,
            zxingOpenCvBarcodeDetectorParameters(
                linearRotationThresholdDegrees = 1,
                linearAdditionalVerticalTransformation = true,
                linearThresholdValue = 150.0,
                linearThresholdMaxVal = 255.0,
                linearKernelSizeWidth = 25.0,
                linearKernelSizeHeight = 1.0,
                linearErosionsIterations = 25,
                linearDilationsIterations = 25,
                matrixRotationThresholdDegrees = 1,
                matrixAdditionalVerticalTransformation = false,
                matrixThresholdValue = 100.0,
                matrixThresholdMaxVal = 255.0,
                matrixKernelSizeWidth = 20.0,
                matrixKernelSizeHeight = 20.0,
                matrixErosionsIterations = 20,
                matrixDilationsIterations = 20
            ),
            createZxingOpenCvBarcodeDetectorTransformer(
                ZxingOpenCvBarcodeDetectorTransformerDefaultParameters(
                    linearRotationThresholdDegrees = 0,
                    linearAdditionalVerticalTransformation = false,
                    linearThresholdValue = 1.0,
                    linearThresholdMaxVal = 1.0,
                    linearKernelSizeWidth = 1.0,
                    linearKernelSizeHeight = 1.0,
                    linearErosionsIterations = 1,
                    linearDilationsIterations = 1,
                    matrixRotationThresholdDegrees = 0,
                    matrixAdditionalVerticalTransformation = false,
                    matrixThresholdValue = 1.0,
                    matrixThresholdMaxVal = 1.0,
                    matrixKernelSizeWidth = 1.0,
                    matrixKernelSizeHeight = 1.0,
                    matrixErosionsIterations = 1,
                    matrixDilationsIterations = 1
                )
            ),
            ::validateGeneralMetadata
        )
    }
}