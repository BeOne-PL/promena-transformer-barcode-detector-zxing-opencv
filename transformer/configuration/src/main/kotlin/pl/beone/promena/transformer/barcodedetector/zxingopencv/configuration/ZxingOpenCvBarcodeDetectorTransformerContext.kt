package pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformer
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformerDefaultParameters

@Configuration
class ZxingOpenCvBarcodeDetectorTransformerContext {

    @Bean
    fun zxingOpenCvBarcodeDetectorTransformer(
        defaultParameters: ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
    ) =
        ZxingOpenCvBarcodeDetectorTransformer(
            defaultParameters
        )
}