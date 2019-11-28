package pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration

import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformer
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
import javax.annotation.PostConstruct

@Configuration
class ZxingOpenCvBarcodeDetectorTransformerLogger(
    private val defaultParameters: ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @PostConstruct
    private fun log() {
        logger.info {
            "Run <${ZxingOpenCvBarcodeDetectorTransformer::class.java.canonicalName}> with <$defaultParameters>"
        }
    }
}