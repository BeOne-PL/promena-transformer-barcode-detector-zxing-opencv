package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector.DetectedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.Processor.PageWithItem

internal class BarcodeFilter(
    regexString: String?
) {

    private val regex = regexString?.toRegex()

    fun filter(listOfPageWithDetectedBarcode: List<PageWithItem<DetectedBarcode>>): List<PageWithItem<DetectedBarcode>> =
        filterByRegex(listOfPageWithDetectedBarcode, regex)
            .let(::filterNotEmpty)

    private fun filterByRegex(listOfPageWithDetectedBarcode: List<PageWithItem<DetectedBarcode>>, regex: Regex?): List<PageWithItem<DetectedBarcode>> =
        if (regex != null) {
            listOfPageWithDetectedBarcode.filter { (_, detectedBarcode) -> detectedBarcode.decodedBarcode.text.matches(regex) }
        } else {
            listOfPageWithDetectedBarcode
        }

    private fun filterNotEmpty(listOfPageWithDetectedBarcode: List<PageWithItem<DetectedBarcode>>): List<PageWithItem<DetectedBarcode>> =
        listOfPageWithDetectedBarcode.filter { it.item.decodedBarcode.text.isNotEmpty() }
}