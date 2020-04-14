package Lab7

import java.awt.BorderLayout
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel


fun main(){
    val frame = JFrame()
    val icon = ImageIcon("no_picture.png")
    val width  = icon.iconWidth
    val height = icon.iconHeight
    val imageScale = width / height.toFloat()
    println("w = $width  h = $height  scale = $imageScale")
    val newWidth = 200
    val newHeight = (newWidth / imageScale).toInt()
    var jLabel = JLabel(ImageIcon(icon.image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH)))
//    var jLabel = JLabel(icon)
//    jLabel.setIcon(
    frame.add(jLabel, BorderLayout.CENTER)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.pack()
    frame.isVisible = true
}
