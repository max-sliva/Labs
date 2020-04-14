package Lab4

import java.awt.Dimension
import javax.swing.JFrame


fun main(){
    var form: JFrame = JFrame("Окно")
    form.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    form.size = Dimension(200,200)
    form.setLocationRelativeTo(null)
    form.isVisible = true
}