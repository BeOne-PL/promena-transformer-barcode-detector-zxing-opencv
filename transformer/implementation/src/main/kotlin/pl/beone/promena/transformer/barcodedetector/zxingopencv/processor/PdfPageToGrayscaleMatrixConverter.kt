package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.opencv.core.CvType
import org.opencv.core.Mat
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte

object PdfPageToGrayscaleMatrixConverter {

    fun convert(document: PDDocument, page: Int): Mat =
        PDFRenderer(document).renderImageWithDPI(page, 300f, ImageType.GRAY).toGrayscaleMatrix()

    private fun BufferedImage.toGrayscaleMatrix(): Mat =
        Mat(height, width, CvType.CV_8UC1).apply {
            val data = (raster.dataBuffer as DataBufferByte).data
            put(0, 0, data)
        }
}