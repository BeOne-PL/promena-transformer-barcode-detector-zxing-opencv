package pl.beone.promena.transformer.barcodedetector.zxingopencv

import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import pl.beone.lib.junit.jupiter.external.DockerExtension
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorSupport
import pl.beone.promena.transformer.barcodedetector.zxingopencv.util.createZxingOpenCvBarcodeDetectorTransformer

@ExtendWith(DockerExtension::class)
class ZxingOpenCvBarcodeDetectorTransformerSupportTest {

    @Test
    fun isSupported() {
        val dataDescriptor = mockk<DataDescriptor>()
        val targetMediaType = mockk<MediaType>()
        val parameters = mockk<Parameters>()

        mockkStatic(ZxingOpenCvBarcodeDetectorSupport::class)
        every { ZxingOpenCvBarcodeDetectorSupport.isSupported(dataDescriptor, targetMediaType, parameters) } just Runs

        createZxingOpenCvBarcodeDetectorTransformer()
            .isSupported(dataDescriptor, targetMediaType, parameters)

        verify(exactly = 1) { ZxingOpenCvBarcodeDetectorSupport.isSupported(dataDescriptor, targetMediaType, parameters) }
    }
}