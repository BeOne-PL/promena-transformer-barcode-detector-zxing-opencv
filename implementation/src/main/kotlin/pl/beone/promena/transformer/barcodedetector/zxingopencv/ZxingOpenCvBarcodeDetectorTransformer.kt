package pl.beone.promena.transformer.barcodedetector.zxingopencv

import org.opencv.core.Core
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorSupport
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.Processor
import pl.beone.promena.transformer.contract.Transformer
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.data.TransformedDataDescriptor
import pl.beone.promena.transformer.contract.data.toTransformedDataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters

class ZxingOpenCvBarcodeDetectorTransformer(
    defaultParameters: ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
) : Transformer {

    companion object {
        init {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
        }
    }

    private val processor = Processor(defaultParameters)

    override fun transform(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters): TransformedDataDescriptor =
        dataDescriptor.descriptors
            .map { processor.process(it, parameters) }
            .toTransformedDataDescriptor()

    override fun isSupported(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters) {
        ZxingOpenCvBarcodeDetectorSupport.isSupported(dataDescriptor, targetMediaType, parameters)
    }
}