package Lab9

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.*

fun main(){
    val window = JFrame("DnD Simple Example") //создаем окно
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.size = Dimension(500, 300) //размер окна

    val tArea = JTextArea()  //создаем текстовую область
    val blackLine = BorderFactory.createLineBorder(Color.black) //создаем объект для черной границы
    tArea.border = blackLine //задаем текстовой области границу
    val myFont = Font("Times", Font.ITALIC, 25) //создаем объект для шрифта
    tArea.font = myFont //устанавливаем шрифт для текстовой области
    tArea.dragEnabled = true  //разрешаем использование drag&drop для текстовой области
    val list = JList<String>() //создаем список
    list.dragEnabled = true //разрешаем использование drag&drop для спискаи
    list.preferredSize = Dimension(100, 100)  //задаем предпочитаемый размер для списка
    list.font = myFont  //устанавливаем шрифт для списка
    list.border = blackLine  //задаем списку границу
    val lModel = DefaultListModel<String>()  //создаем модель данных для списка
    list.model = lModel  //устанавливаем модель для списка
    lModel.addAll(arrayListOf("hello", "world", " ", "!", ","))  //вставляем данные в модель
    //создаем компоновку BorderLayout с нужными параметрами отступа между объектами
    val borderLay = BorderLayout(20,20)
    window.layout = borderLay //устанавливаем компоновку на окно
    window.add(tArea, BorderLayout.CENTER)  //вставляем объекты в окно
    window.add(list, BorderLayout.EAST)
    window.setLocationRelativeTo(null)
    window.isVisible = true
}