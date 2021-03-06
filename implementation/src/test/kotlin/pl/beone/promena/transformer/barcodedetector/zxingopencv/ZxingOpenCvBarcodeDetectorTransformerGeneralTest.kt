package pl.beone.promena.transformer.barcodedetector.zxingopencv

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit.jupiter.external.DockerExtension
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_120_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_150_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_180_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_210_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_240_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_270_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_300_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_30_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_330_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_60_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.model.Resource.Document.General.GENERAL_TEST_90_DEGREES
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.createZxingOpenCvBarcodeDetectorTransformer
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.test
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.validateGeneralMetadata
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.zxingOpenCvBarcodeDetectorTransformerBestDefaultParameters

@ExtendWith(DockerExtension::class)
class ZxingOpenCvBarcodeDetectorTransformerGeneralTest {

    companion object {
        private val zxingOpenCvBarcodeDetectorTransformer = createZxingOpenCvBarcodeDetectorTransformer(
            zxingOpenCvBarcodeDetectorTransformerBestDefaultParameters.copy(
                formats = listOf(
                    CODABAR.value,
                    UPC_A.value,
                    EAN_8.value,
                    EAN_13.value,
                    CODE_39.value,
                    CODE_128.value,
                    ITF.value,
                    QR_CODE.value,
                    PDF417.value,
                    AZTEC_CODE.value,
                    DATA_MATRIX.value,
                    MAXI_CODE.value
                )
            )
        )
    }

    @Test
    fun transform() {
        test(
            GENERAL_TEST,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy30Degrees() {
        test(
            GENERAL_TEST_30_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy60Degrees() {
        test(
            GENERAL_TEST_60_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy90Degrees() {
        test(
            GENERAL_TEST_90_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy120Degrees() {
        test(
            GENERAL_TEST_120_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy150Degrees() {
        test(
            GENERAL_TEST_150_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy180Degrees() {
        test(
            GENERAL_TEST_180_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy210Degrees() {
        test(
            GENERAL_TEST_210_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy240Degrees() {
        test(
            GENERAL_TEST_240_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy270Degrees() {
        test(
            GENERAL_TEST_270_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy300Degrees() {
        test(
            GENERAL_TEST_300_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }

    @Test
    fun transform_rotatedBy330Degrees() {
        test(
            GENERAL_TEST_330_DEGREES,
            zxingOpenCvBarcodeDetectorTransformer = zxingOpenCvBarcodeDetectorTransformer,
            validateMetadata = ::validateGeneralMetadata
        )
    }
}