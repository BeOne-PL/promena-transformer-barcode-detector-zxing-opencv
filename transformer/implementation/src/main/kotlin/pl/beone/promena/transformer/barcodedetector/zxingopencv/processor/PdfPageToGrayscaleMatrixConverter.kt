package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.bytedeco.opencv.opencv_core.Mat
import pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util.toMat

object PdfPageToGrayscaleMatrixConverter {

    fun convert(document: PDDocument, page: Int): Mat =
        PDFRenderer(document).renderImageWithDPI(page, 300f, ImageType.GRAY).toMat()
}
