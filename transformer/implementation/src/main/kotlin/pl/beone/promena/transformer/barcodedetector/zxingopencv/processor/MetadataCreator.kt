package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorConstants.TRANSFORMER_NAME
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Companion.CONTOUR_ON_PAGE
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Companion.FORMAT
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Companion.PAGE
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Companion.TEXT
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Contour.Companion.VERTEX
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Contour.Companion.VERTEX2
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Contour.Companion.VERTEX3
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Contour.Companion.VERTEX4
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Contour.Vertex.Companion.X
import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorMetadata.Barcode.Contour.Vertex.Companion.Y
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.BarcodeDetector.DetectedBarcode
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.ContourVerticesFinder.FoundContour.Vertex
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.Processor.PageWithItem
import pl.beone.promena.transformer.contract.model.Metadata
import pl.beone.promena.transformer.internal.model.metadata.MapMetadata
import pl.beone.promena.transformer.internal.model.metadata.emptyMetadata
import pl.beone.promena.transformer.internal.model.metadata.plus

internal object MetadataCreator {

    fun createMetadata(listOfPageWithItem: List<PageWithItem<DetectedBarcode>>): Metadata =
        emptyMetadata() +
                (TRANSFORMER_NAME to listOfPageWithItem.map { (page, detectedBarcode) -> createBarcode(page, detectedBarcode) })

    private fun createBarcode(page: Int, detectedBarcode: DetectedBarcode): MapMetadata =
        with(detectedBarcode) {
            emptyMetadata() +
                    (TEXT to decodedBarcode.text) +
                    (FORMAT to decodedBarcode.format.value) +
                    (PAGE to page) +
                    (CONTOUR_ON_PAGE to createContour(foundContour))
        }

    private fun createContour(foundContour: FoundContour): MapMetadata =
        with(foundContour) {
            emptyMetadata() +
                    (VERTEX to createVertex(vertex)) +
                    (VERTEX2 to createVertex(vertex2)) +
                    (VERTEX3 to createVertex(vertex3)) +
                    (VERTEX4 to createVertex(vertex4))
        }

    private fun createVertex(vertex: Vertex): MapMetadata =
        emptyMetadata() +
                (X to vertex.x) +
                (Y to vertex.y)

}