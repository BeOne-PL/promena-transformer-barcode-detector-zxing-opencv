package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import pl.beone.promena.transformer.barcodedetector.metadata.*
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector.DetectedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.Processor.PageWithItem
import pl.beone.promena.transformer.contract.model.Metadata

internal object MetadataCreator {

    fun createMetadata(listOfPageWithItem: List<PageWithItem<DetectedBarcode>>): Metadata =
        barcodeDetectorMetadata(listOfPageWithItem.map { (page, detectedBarcode) -> createBarcode(page, detectedBarcode) })

    private fun createBarcode(page: Int, detectedBarcode: DetectedBarcode): BarcodeDetectorMetadata.Barcode =
        with(detectedBarcode) {
            barcode() addText
                    decodedBarcode.text addFormat
                    decodedBarcode.format.value addPage
                    page addContourVertexOnPage
                    createVertex(foundContour.vertex) addContourVertexOnPage
                    createVertex(foundContour.vertex2) addContourVertexOnPage
                    createVertex(foundContour.vertex3) addContourVertexOnPage
                    createVertex(foundContour.vertex4)
        }

    private fun createVertex(vertex: Vertex): Pair<Int, Int> =
        vertex.x to vertex.y
}