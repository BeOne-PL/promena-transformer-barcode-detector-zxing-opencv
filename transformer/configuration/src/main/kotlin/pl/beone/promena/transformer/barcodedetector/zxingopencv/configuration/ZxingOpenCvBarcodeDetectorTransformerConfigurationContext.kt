package pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
import pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration.extension.getNotBlankProperty
import pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration.extension.getRequiredNotBlankProperty
import pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration.extension.toDuration

@Configuration
class ZxingOpenCvBarcodeDetectorTransformerConfigurationContext {

    companion object {
        private const val PROPERTY_PREFIX = "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv"
    }

    @Bean
    fun zxingOpenCvBarcodeDetectorTransformerDefaultParameters(environment: Environment): ZxingOpenCvBarcodeDetectorTransformerDefaultParameters =
        ZxingOpenCvBarcodeDetectorTransformerDefaultParameters(
            environment.getNotBlankProperty("$PROPERTY_PREFIX.default.parameters.formats")?.split(","),
            environment.getNotBlankProperty("$PROPERTY_PREFIX.default.parameters.regex-filter"),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-rotation-threshold-degrees").toInt(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-additional-vertical-transformation").toBoolean(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-threshold-value").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-threshold-max-val").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-kernel-size-width").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-kernel-size-height").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-erosions-iterations").toInt(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.linear-dilations-iterations").toInt(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-rotation-threshold-degrees").toInt(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-additional-vertical-transformation").toBoolean(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-threshold-value").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-threshold-max-val").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-kernel-size-width").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-kernel-size-height").toDouble(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-erosions-iterations").toInt(),
            environment.getRequiredNotBlankProperty("$PROPERTY_PREFIX.default.parameters.matrix-dilations-iterations").toInt(),
            environment.getNotBlankProperty("$PROPERTY_PREFIX.default.parameters.timeout")?.toDuration()
        )
}