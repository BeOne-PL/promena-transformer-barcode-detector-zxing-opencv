package pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel

import pl.beone.promena.transformer.barcodedetector.zxingopencv.applicationmodel.ZxingOpenCvBarcodeDetectorConstants.TRANSFORMER_NAME
import pl.beone.promena.transformer.contract.model.Metadata

class ZxingOpenCvBarcodeDetectorMetadata(
    metadata: Metadata
) : Metadata by metadata {

    class Barcode(
        metadata: Metadata
    ) : Metadata by metadata {

        companion object {
            const val TEXT = "text"
            const val FORMAT = "format"
            const val PAGE = "page"
            const val CONTOUR_ON_PAGE = "contourOnPage"
        }

        class Contour(
            metadata: Metadata
        ) : Metadata by metadata {

            companion object {
                const val VERTEX = "vertex"
                const val VERTEX2 = "vertex2"
                const val VERTEX3 = "vertex3"
                const val VERTEX4 = "vertex4"
            }

            class Vertex(
                metadata: Metadata
            ) : Metadata by metadata {

                companion object {
                    const val X = "x"
                    const val Y = "y"
                }

                fun getX(): Int =
                    get(X, Int::class.java)

                fun getY(): Int =
                    get(Y, Int::class.java)
            }

            fun getVertex(): Vertex =
                Vertex(getMetadata(VERTEX))

            fun getVertex2(): Vertex =
                Vertex(getMetadata(VERTEX2))

            fun getVertex3(): Vertex =
                Vertex(getMetadata(VERTEX3))

            fun getVertex4(): Vertex =
                Vertex(getMetadata(VERTEX4))
        }

        fun getText(): String =
            get(TEXT, String::class.java)

        fun getFormat(): String =
            get(FORMAT, String::class.java)

        fun getPage(): Int =
            get(PAGE, Int::class.java)

        fun getContourOnPage(): Contour =
            Contour(getMetadata(CONTOUR_ON_PAGE))
    }

    fun getBarcodes(): List<Barcode> =
        getList(TRANSFORMER_NAME, Metadata::class.java).map(::Barcode)
}