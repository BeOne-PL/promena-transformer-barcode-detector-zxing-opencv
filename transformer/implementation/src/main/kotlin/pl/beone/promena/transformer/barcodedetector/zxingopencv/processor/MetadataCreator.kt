package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import pl.beone.promena.transformer.barcodedetector.metadata.BarcodeDetectorMetadataBuilder
import pl.beone.promena.transformer.barcodedetector.metadata.BarcodeDetectorMetadataBuilder.BarcodeBuilder
import pl.beone.promena.transformer.barcodedetector.metadata.BarcodeDetectorMetadataBuilder.BarcodeBuilder.VertexBuilder
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector.DetectedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.Processor.PageWithItem
import pl.beone.promena.transformer.contract.model.Metadata

internal object MetadataCreator {

    fun createMetadata(listOfPageWithItem: List<PageWithItem<DetectedBarcode>>): Metadata =
        BarcodeDetectorMetadataBuilder(
            listOfPageWithItem.map { (page, detectedBarcode) ->
                createBarcode(detectedBarcode, page)
            }
        ).build()

    private fun createBarcode(detectedBarcode: DetectedBarcode, page: Int): Metadata =
        BarcodeBuilder()
            .text(detectedBarcode.decodedBarcode.text)
            .format(detectedBarcode.decodedBarcode.format.value)
            .page(page)
            .contourVerticesOnPage(createVertex(detectedBarcode.foundContour.vertex))
            .contourVerticesOnPage(createVertex(detectedBarcode.foundContour.vertex2))
            .contourVerticesOnPage(createVertex(detectedBarcode.foundContour.vertex3))
            .contourVerticesOnPage(createVertex(detectedBarcode.foundContour.vertex4))
            .build()

    private fun createVertex(vertex: Vertex): Metadata =
        VertexBuilder()
            .x(vertex.x)
            .y(vertex.y)
            .build()
}