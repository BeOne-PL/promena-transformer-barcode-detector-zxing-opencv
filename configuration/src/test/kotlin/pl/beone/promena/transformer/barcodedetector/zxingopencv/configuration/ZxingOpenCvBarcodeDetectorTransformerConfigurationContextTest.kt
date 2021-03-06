package pl.beone.promena.transformer.barcodedetector.zxingopencv.configuration

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.mock.env.MockEnvironment
import pl.beone.promena.transformer.barcodedetector.zxingopencv.ZxingOpenCvBarcodeDetectorTransformerDefaultParameters
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.EAN_13
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorFormat.MAXI_CODE
import java.time.Duration

class ZxingOpenCvBarcodeDetectorTransformerConfigurationContextTest {

    @Test
    fun `setting context _ default`() {
        val environment = createEnvironment(
            mapOf(
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.formats" to "",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.regex-filter" to "",

                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-rotation-threshold-degrees" to "5",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-additional-vertical-transformation" to "true",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-threshold-value" to "150.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-threshold-max-val" to "255.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-kernel-size-width" to "15.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-kernel-size-height" to "1.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-erosions-iterations" to "25",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-dilations-iterations" to "30",

                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-rotation-threshold-degrees" to "3",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-additional-vertical-transformation" to "false",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-threshold-value" to "100.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-threshold-max-val" to "200.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-kernel-size-width" to "20.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-kernel-size-height" to "25.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-erosions-iterations" to "10",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-dilations-iterations" to "15",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.timeout" to ""
            )
        )

        val applicationContext = createConfigApplicationContext(environment, ZxingOpenCvBarcodeDetectorTransformerConfigurationContext::class.java)
        with(applicationContext.getBean(ZxingOpenCvBarcodeDetectorTransformerDefaultParameters::class.java)) {
            formats shouldBe null
            regexFilter shouldBe null

            linearRotationThresholdDegrees shouldBe 5
            linearAdditionalVerticalTransformation shouldBe true
            linearThresholdValue shouldBe 150.0
            linearThresholdMaxVal shouldBe 255.0
            linearKernelSizeWidth shouldBe 15.0
            linearKernelSizeHeight shouldBe 1.0
            linearErosionsIterations shouldBe 25
            linearDilationsIterations shouldBe 30

            matrixRotationThresholdDegrees shouldBe 3
            matrixAdditionalVerticalTransformation shouldBe false
            matrixThresholdValue shouldBe 100.0
            matrixThresholdMaxVal shouldBe 200.0
            matrixKernelSizeWidth shouldBe 20.0
            matrixKernelSizeHeight shouldBe 25.0
            matrixErosionsIterations shouldBe 10
            matrixDilationsIterations shouldBe 15

            timeout shouldBe null
        }
    }

    @Test
    fun `setting context _ all`() {
        val environment = createEnvironment(
            mapOf(
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.formats" to "MaxiCode,EAN-13",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.regex-filter" to ".*",

                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-rotation-threshold-degrees" to "5",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-additional-vertical-transformation" to "true",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-threshold-value" to "150.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-threshold-max-val" to "255.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-kernel-size-width" to "15.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-kernel-size-height" to "1.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-erosions-iterations" to "25",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.linear-dilations-iterations" to "30",

                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-rotation-threshold-degrees" to "3",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-additional-vertical-transformation" to "false",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-threshold-value" to "100.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-threshold-max-val" to "200.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-kernel-size-width" to "20.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-kernel-size-height" to "25.0",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-erosions-iterations" to "10",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.matrix-dilations-iterations" to "15",
                "transformer.pl.beone.promena.transformer.barcodedetector.zxingopencv.default.parameters.timeout" to "5m"
            )
        )

        val applicationContext = createConfigApplicationContext(environment, ZxingOpenCvBarcodeDetectorTransformerConfigurationContext::class.java)
        with(applicationContext.getBean(ZxingOpenCvBarcodeDetectorTransformerDefaultParameters::class.java)) {
            formats shouldBe listOf(MAXI_CODE.value, EAN_13.value)
            regexFilter shouldBe ".*"

            linearRotationThresholdDegrees shouldBe 5
            linearAdditionalVerticalTransformation shouldBe true
            linearThresholdValue shouldBe 150.0
            linearThresholdMaxVal shouldBe 255.0
            linearKernelSizeWidth shouldBe 15.0
            linearKernelSizeHeight shouldBe 1.0
            linearErosionsIterations shouldBe 25
            linearDilationsIterations shouldBe 30

            matrixRotationThresholdDegrees shouldBe 3
            matrixAdditionalVerticalTransformation shouldBe false
            matrixThresholdValue shouldBe 100.0
            matrixThresholdMaxVal shouldBe 200.0
            matrixKernelSizeWidth shouldBe 20.0
            matrixKernelSizeHeight shouldBe 25.0
            matrixErosionsIterations shouldBe 10
            matrixDilationsIterations shouldBe 15

            timeout shouldBe Duration.ofMinutes(5)
        }
    }

    private fun createEnvironment(properties: Map<String, String>): MockEnvironment =
        MockEnvironment()
            .apply { properties.forEach { (key, value) -> setProperty(key, value) } }

    private fun createConfigApplicationContext(environment: ConfigurableEnvironment, clazz: Class<*>): AnnotationConfigApplicationContext =
        AnnotationConfigApplicationContext().apply {
            this.environment = environment
            register(clazz)
            refresh()
        }
}