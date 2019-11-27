package pl.beone.promena.transformer.barcodedetector.zxingopencv.util

import io.kotlintest.Failures.failure
import io.kotlintest.matchers.collections.shouldHaveAtLeastSize
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.withClue
import io.kotlintest.shouldBe
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformer
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorBarcodeFormat.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.zxingOpenCvBarcodeDetectorParameters
import pl.beone.promena.transformer.contract.data.singleDataDescriptor
import pl.beone.promena.transformer.contract.model.Metadata
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.internal.model.data.memory.toMemoryData
import pl.beone.promena.transformer.internal.model.metadata.emptyMetadata

internal val zxingOpenCvBarcodeDetectorTransformerBestDefaultParameters = ZxingOpenCvBarcodeDetectorTransformerDefaultParameters(
    formats = listOf(
        CODABAR.format, CODE_39.format, CODE_128.format, ITF.format, QR_CODE.format, PDF417.format, AZTEC_CODE.format, DATA_MATRIX.format, MAXI_CODE.format
    ),
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
)

internal fun createZxingOpenCvBarcodeDetectorTransformer(
    defaultParameters: ZxingOpenCvBarcodeDetectorTransformerDefaultParameters = zxingOpenCvBarcodeDetectorTransformerBestDefaultParameters
): ZxingOpenCvBarcodeDetectorTransformer =
    ZxingOpenCvBarcodeDetectorTransformer(defaultParameters)

internal fun test(
    resourcePath: String,
    parameters: Parameters = zxingOpenCvBarcodeDetectorParameters(),
    zxingOpenCvBarcodeDetectorTransformer: ZxingOpenCvBarcodeDetectorTransformer = createZxingOpenCvBarcodeDetectorTransformer(),
    validateMetadata: (Metadata) -> Unit
) {
    val data = getResourceAsBytes(resourcePath).toMemoryData()

    with(
        zxingOpenCvBarcodeDetectorTransformer.transform(
            singleDataDescriptor(data, MediaTypeConstants.APPLICATION_PDF, emptyMetadata()),
            MediaTypeConstants.APPLICATION_PDF, parameters
        )
    ) {
        withClue("Transformed data should contain only <1> element") { descriptors shouldHaveSize 1 }

        with(descriptors[0]) {
            this.data shouldBe data
            validateMetadata(metadata)
        }
    }
}

internal fun validateBarcode(barcodes: List<Barcode>, text: String, barcodeFormat: ZxingOpenCvBarcodeDetectorBarcodeFormat, page: Int) {
    val barcode = barcodes.firstOrNull { it.getText() == text }
        ?: throw failure("There is no <$text> barcode. Available barcodes: ${barcodes.map { "(" + it.getText() + ";" + it.getFormat() + ";" + it.getPage() + ")" }}")
    barcode.getFormat() shouldBe barcodeFormat.format
    barcode.getPage() shouldBe page
}

internal fun validateGeneralMetadata(metadata: Metadata) {
    with(ZxingOpenCvBarcodeDetectorMetadata(metadata).getBarcodes()) {
        this shouldHaveAtLeastSize 13
        with(this) {
            validateBarcode(this, "0123456789", CODABAR, 1)
            validateBarcode(this, "000012345670", UPC_A, 1)
            validateBarcode(this, "01234565", EAN_8, 1)
            validateBarcode(this, "8412345678905", EAN_13, 1)
            validateBarcode(this, "0123456789A", CODE_39, 1)
            validateBarcode(this, "C39+E+X", CODE_39, 1)
            validateBarcode(this, "01234567890abcdefg", CODE_128, 1)
            validateBarcode(this, "30712345000010", ITF, 1)

            validateBarcode(this, "TEST123456", QR_CODE, 2)
            validateBarcode(this, "ABCDEF", PDF417, 2)
            validateBarcode(this, "Test Aztec", AZTEC_CODE, 2)
            validateBarcode(this, "Test Data Matrix", DATA_MATRIX, 2)
            validateBarcode(this, "Test MaxiCode", MAXI_CODE, 2)
        }
    }
}