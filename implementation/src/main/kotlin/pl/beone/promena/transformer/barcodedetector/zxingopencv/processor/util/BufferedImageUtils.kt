package pl.beone.promena.transformer.barcodedetector.zxingopencv.processor.util

import org.bytedeco.javacv.Java2DFrameUtils
import org.bytedeco.opencv.opencv_core.Mat
import java.awt.Color
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

fun BufferedImage.toMat(): Mat =
    Java2DFrameUtils.toMat(Java2DFrameUtils.toFrame(this))

fun BufferedImage.applyWhiteBackground() {
    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, width, height)

    graphics.dispose()
}

fun BufferedImage.drawCentred(destination: BufferedImage) {
    AffineTransform()
        .also { it.translate((destination.width.toDouble() - width) / 2, (destination.height.toDouble() - height) / 2) }
        .also { destination.createGraphics().drawImage(this, it, null) }
}

fun BufferedImage.rotate(angleRadians: Double): BufferedImage =
    AffineTransform()
        .also { it.rotate(angleRadians, (width / 2).toDouble(), (height / 2).toDouble()) }
        .let { AffineTransformOp(it, AffineTransformOp.TYPE_BILINEAR).filter(this, null) }