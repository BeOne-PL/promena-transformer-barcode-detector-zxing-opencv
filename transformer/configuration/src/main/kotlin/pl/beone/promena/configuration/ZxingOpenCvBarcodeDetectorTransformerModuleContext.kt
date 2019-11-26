package pl.beone.promena.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackages = ["pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration"])
@PropertySource("classpath:transformer-barcode-detector-zxing-opencv.properties")
class ZxingOpenCvBarcodeDetectorTransformerModuleContext