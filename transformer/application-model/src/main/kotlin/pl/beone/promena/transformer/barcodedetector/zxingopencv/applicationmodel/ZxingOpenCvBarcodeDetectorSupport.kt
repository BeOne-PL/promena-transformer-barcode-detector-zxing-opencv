package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import pl.beone.lib.typeconverter.applicationmodel.exception.TypeConversionException
import pl.beone.promena.transformer.applicationmodel.exception.transformer.TransformationNotSupportedException
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaType
import pl.beone.promena.transformer.applicationmodel.mediatype.MediaTypeConstants.APPLICATION_PDF
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.Formats
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearAdditionalVerticalTransformation
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearDilationsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearErosionsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearKernelSizeHeight
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearKernelSizeWidth
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearRotationThresholdDegrees
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearThresholdMaxVal
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.LinearThresholdValue
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixAdditionalVerticalTransformation
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixDilationsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixErosionsIterations
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixKernelSizeHeight
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixKernelSizeWidth
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixRotationThresholdDegrees
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixThresholdMaxVal
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.MatrixThresholdValue
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorParametersConstants.RegexFilter
import pl.beone.promena.transformer.contract.data.DataDescriptor
import pl.beone.promena.transformer.contract.model.Parameters

object ZxingOpenCvBarcodeDetectorSupport {

    @JvmStatic
    fun isSupported(dataDescriptor: DataDescriptor, targetMediaType: MediaType, parameters: Parameters) {
        dataDescriptor.descriptors.forEach { (_, mediaType) -> MediaTypeSupport.isSupported(mediaType, targetMediaType) }
        ParametersSupport.isSupported(parameters)
    }

    object MediaTypeSupport {
        private val supportedMediaType = setOf(
            APPLICATION_PDF to APPLICATION_PDF
        )

        @JvmStatic
        fun isSupported(mediaType: MediaType, targetMediaType: MediaType) {
            if (!supportedMediaType.contains(mediaType to targetMediaType)) {
                throw TransformationNotSupportedException.unsupportedMediaType(mediaType, targetMediaType)
            }
        }
    }

    object ParametersSupport {
        @JvmStatic
        fun isSupported(parameters: Parameters) {
            val barcodeFormats = ZxingOpenCvBarcodeDetectorBarcodeFormat.values().map(ZxingOpenCvBarcodeDetectorBarcodeFormat::format)
            parameters.validate(Formats.NAME, Formats.CLASS, false, "(${barcodeFormats.joinToString(", ")})")
            { formats -> formats.all { barcodeFormats.contains(it) } }

            parameters.validate(RegexFilter.NAME, RegexFilter.CLASS, false)

            parameters.validate(LinearRotationThresholdDegrees.NAME, LinearRotationThresholdDegrees.CLASS, false, "<1, 360>")
            { (1..180).contains(it) }
            parameters.validate(LinearAdditionalVerticalTransformation.NAME, LinearAdditionalVerticalTransformation.CLASS, false)
            parameters.validate(LinearThresholdValue.NAME, LinearThresholdValue.CLASS, false, "<1.0, 255.0>")
            { (1.0..255.0).contains(it) }
            parameters.validate(LinearThresholdMaxVal.NAME, LinearThresholdMaxVal.CLASS, false, "<1.0, 255.0>")
            { (1.0..255.0).contains(it) }
            parameters.validate(LinearKernelSizeWidth.NAME, LinearKernelSizeWidth.CLASS, false, "<1.0, 1000.0>")
            { (1.0..1000.0).contains(it) }
            parameters.validate(LinearKernelSizeHeight.NAME, LinearKernelSizeHeight.CLASS, false, "<1.0, 1000.0>")
            { (1.0..1000.0).contains(it) }
            parameters.validate(LinearErosionsIterations.NAME, LinearErosionsIterations.CLASS, false, "<1, 1000>")
            { (1..1000).contains(it) }
            parameters.validate(LinearDilationsIterations.NAME, LinearDilationsIterations.CLASS, false, "<1, 1000>")
            { (1..1000).contains(it) }

            parameters.validate(MatrixRotationThresholdDegrees.NAME, MatrixRotationThresholdDegrees.CLASS, false, "<1, 360>")
            { (1..180).contains(it) }
            parameters.validate(MatrixAdditionalVerticalTransformation.NAME, MatrixAdditionalVerticalTransformation.CLASS, false)
            parameters.validate(MatrixThresholdValue.NAME, MatrixThresholdValue.CLASS, false, "<1.0, 255.0>")
            { (1.0..255.0).contains(it) }
            parameters.validate(MatrixThresholdMaxVal.NAME, MatrixThresholdMaxVal.CLASS, false, "<1.0, 255.0>")
            { (1.0..255.0).contains(it) }
            parameters.validate(MatrixKernelSizeWidth.NAME, MatrixKernelSizeWidth.CLASS, false, "<1.0, 1000.0>")
            { (1.0..1000.0).contains(it) }
            parameters.validate(MatrixKernelSizeHeight.NAME, MatrixKernelSizeHeight.CLASS, false, "<1.0, 1000.0>")
            { (1.0..1000.0).contains(it) }
            parameters.validate(MatrixErosionsIterations.NAME, MatrixErosionsIterations.CLASS, false, "<1, 1000>")
            { (1..1000).contains(it) }
            parameters.validate(MatrixDilationsIterations.NAME, MatrixDilationsIterations.CLASS, false, "<1, 1000>")
            { (1..1000).contains(it) }
        }

        private fun <T> Parameters.validate(
            name: String,
            clazz: Class<T>,
            mandatory: Boolean,
            valueVerifierMessage: String? = null,
            valueVerifier: (T) -> Boolean = { true }
        ) {
            try {
                val value = get(name, clazz)
                if (!valueVerifier(value)) {
                    throw TransformationNotSupportedException.unsupportedParameterValue(name, value, valueVerifierMessage)
                }
            } catch (e: NoSuchElementException) {
                if (mandatory) {
                    throw TransformationNotSupportedException.mandatoryParameter(name)
                }
            } catch (e: TypeConversionException) {
                throw TransformationNotSupportedException.unsupportedParameterType(name, clazz)
            }
        }
    }
}