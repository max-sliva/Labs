package Lab4

import Lab3.Auto
import Lab3.Car
import Lab3.GarageAuto
import Lab3.Truck
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.text.NumberFormat
import javax.swing.*

var northBox = Box(BoxLayout.Y_AXIS)    // верхняя панель формы
var northBoxUp = Box(BoxLayout.X_AXIS)  //верхняя часть верхней панели
var northBoxDown = Box(BoxLayout.X_AXIS) //нижняя часть верхней панели
var listOfAuto = JList<Auto>()          //список для отображения авто
var listModel = DefaultListModel<Auto>()  //модель данных для списка
var garage = GarageAuto()               //гараж
var fieldsMap = HashMap<String, JComponent>()  //HashMap для хранения названий полей ввода данных и самих полей
val butCreate = JButton("Create")   //кнопка для показа полей ввода данных

fun main() {
    var form: JFrame = JFrame("Окно")
    form.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    form.setLocationRelativeTo(null)  // чтоб форма появилась в центре экрана
    buildNorth(form)        //вызываем функцию для заполнения верхней части формы
    buildCenter(form)        //вызываем функцию для заполнения центральной части формы
    form.pack()         //упаковываем форму
    form.minimumSize = form.size    //задаем минимальный размер
    form.isVisible = true           //показываем форму
}

fun buildNorth(form: JFrame) {  //функция для заполнения верхней части формы
    var combo = JComboBox<String>()  //комбо-бокс для выбора между Car и Truck
    combo.addItem("Car")        //добавляем элементы в комбо-бокс
    combo.addItem("Truck")
    northBoxUp.add(combo)   //добавляем комбо-бокс на форму
    northBoxUp.add(Box.createHorizontalStrut(10))  //добавляем промежуток после комбо-бокса
    northBoxUp.add(butCreate)          //добавляем кнопку "Create"
    northBoxUp.add(Box.createHorizontalStrut(10))  //добавляем промежуток после комбо-бокса
    var butPrintGarage = JButton("PrintGarage") //для вывода в консоль содержимого гаража
    butPrintGarage.isEnabled = false  //неактивная в самом начале
    butPrintGarage.addActionListener {
        println("$garage")
    }
    northBoxUp.add(butPrintGarage)          //добавляем кнопку "Create"
    northBoxUp.add(Box.createHorizontalGlue())  //добавляем пружину после кнопки
    var auto = Auto()  //объект класса авто
    val addButton = JButton("Add")  //кнопка для добавления данных в объект и в JList
    butCreate.addActionListener{ //слушатель нажатия на кнопку
        northBoxDown.removeAll()  //удаляем всё с нижней части верхней панели
        northBoxDown.isVisible = true // показываем нижнюю часть верхней панели
        butCreate.isEnabled = false  //делаем неактивной кнопку "Create"
        //создаем HashMap с полями и типами полей либо класса Car, либо клаcса Truck в зависимости от выбора в комбо-боксе
        var fields = if (combo.selectedItem == "Car") hashMapOf("firm" to "String", "maxSpeed" to "Int",
                                                                                    "model" to "String", "numDoors" to "Int", "fullTime" to "Boolean")
                                           else hashMapOf("firm" to "String", "maxSpeed" to "Int", "model" to "String", "power" to "Int", "trailer" to "Boolean")
        //вызываем функцию, которой передаем созданный HashMap и получаем новый HashMap с названиями полей ввода данных и самими полями,
        //а сама функция создает поля ввода данных в зависимости от типа поля и добавляет их в нижнюю часть верхней панели
        fieldsMap=fillNorthBoxDown(fields) 
        northBoxDown.add(addButton) //добавляем кнопку для добавления данных в объект и в JList
        form.pack() //упаковываем форму
    }
    addButton.addActionListener { //слушатель нажатия на кнопку для добавления данных в объект и в JList
        butCreate.isEnabled = true
        northBoxDown.isVisible = false
        if (combo.selectedItem == "Car")  auto = createCar()  else auto = createTruck()
        println(auto)
        listModel.addElement(auto)
        garage.addAuto(auto)
        butPrintGarage.isEnabled = true //активируем кнопку для печати содержимого гаража
        form.pack()
    }
    northBoxDown.isVisible = false
    northBox.add(northBoxUp)
    northBox.add(northBoxDown)
    form.add(northBox, BorderLayout.NORTH)
}

fun fillNorthBoxDown(fields: HashMap<String, String>): HashMap<String, JComponent> {
    var fieldsHash = HashMap<String, JComponent>()
    fields.forEach { k, v ->
        var box = Box(BoxLayout.Y_AXIS)
        box.border = BorderFactory.createLineBorder(Color.black)
        box.alignmentX = Component.LEFT_ALIGNMENT
        box.add(JLabel(k))
        when (v) {
            "String" -> {
                val textField = JTextField(10)
                box.add(textField)
                northBoxDown.add(box)
                fieldsHash.put(k, textField)
            }
            "Int" -> {
                var numberField = JFormattedTextField(NumberFormat.getNumberInstance())
                numberField.columns = 10
                box.add(numberField)
                northBoxDown.add(box)
                fieldsHash.put(k, numberField)
            }
            "Boolean" -> {
                var comboBoolean = JComboBox<Boolean>()
                comboBoolean.addItem(true)
                comboBoolean.addItem(false)
                comboBoolean.preferredSize = Dimension(100, 10)
                box.add(comboBoolean)
                println()
                northBoxDown.add(box)
                fieldsHash.put(k, comboBoolean)
            }
        }
    }
    return fieldsHash
}

fun createCar() : Car{
    val firmField = fieldsMap.get("firm") as JTextField
    val maxSpeedField = fieldsMap.get("maxSpeed") as JFormattedTextField
    val modelField = fieldsMap.get("model") as JTextField
    val numDoorsField = fieldsMap.get("numDoors") as JFormattedTextField
    val fullTimeField = fieldsMap.get("fullTime") as JComboBox<Boolean>
    return Car(firmField.text, maxSpeedField.text.toInt(), modelField.text, numDoorsField.text.toInt(), fullTimeField.selectedItem as Boolean)
}

fun createTruck() : Truck{
    val firmField = fieldsMap.get("firm") as JTextField
    val maxSpeedField = fieldsMap.get("maxSpeed") as JFormattedTextField
    val modelField = fieldsMap.get("model") as JTextField
    val powerField = fieldsMap.get("power") as JFormattedTextField
    val trailerField = fieldsMap.get("trailer") as JComboBox<Boolean>
    return Truck(firmField.text, maxSpeedField.text.toInt(), modelField.text, powerField.text.toInt(), trailerField.selectedItem as Boolean)
}

fun buildCenter(form: JFrame) {
    listOfAuto.model = listModel
    form.add(listOfAuto, BorderLayout.CENTER)
    listOfAuto.addMouseListener(object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent?) {
            super.mousePressed(e)
            val tempList= e!!.source as JList<Auto>
            println(tempList.selectedValue)
            var tempAuto = tempList.selectedValue
            val i = listModel.indexOf(tempAuto)
            northBoxDown.removeAll()
            northBoxDown.isVisible = true
            var fields = if (tempAuto is Car) hashMapOf("firm" to "String", "maxSpeed" to "Int",
                                                                        "model" to "String", "numDoors" to "Int", "fullTime" to "Boolean")
            else hashMapOf("firm" to "String", "maxSpeed" to "Int", "model" to "String",
                            "power" to "Int", "trailer" to "Boolean")
            fieldsMap=fillNorthBoxDown(fields)
            if (tempAuto is Car) {
                (fieldsMap.get("firm") as JTextField).text = tempAuto.firm
                (fieldsMap.get("maxSpeed") as JFormattedTextField).value = tempAuto.maxSpeed
                (fieldsMap.get("model") as JTextField).text = tempAuto.model
                (fieldsMap.get("numDoors") as JFormattedTextField).value = tempAuto.numDoors
                (fieldsMap.get("fullTime") as JComboBox<Boolean>).selectedItem = tempAuto.fullTime
            }
            else {
                (fieldsMap.get("firm") as JTextField).text = tempAuto.firm
                (fieldsMap.get("maxSpeed") as JFormattedTextField).value = tempAuto.maxSpeed
                (fieldsMap.get("model") as JTextField).text = (tempAuto as Truck).model
                (fieldsMap.get("power") as JFormattedTextField).value = (tempAuto as Truck).power
                (fieldsMap.get("trailer") as JComboBox<Boolean>).selectedItem = (tempAuto as Truck).trailer
            }

            val changeButton = JButton("Change")
            northBoxDown.add(changeButton)
            northBoxDown.add(Box.createHorizontalStrut(10))
            val cancelButton = JButton("Cancel")
            northBoxDown.add(cancelButton)
            form.pack()
            changeButton.addActionListener{
                northBoxDown.isVisible = false
                butCreate.isEnabled = true
                if (tempAuto is Car) tempAuto = createCar() else tempAuto = createTruck()
                listModel.set(i, tempAuto)
                garage.changeAuto(i, tempAuto) //меняем объект в гараже
            }
            cancelButton.addActionListener {
                northBoxDown.isVisible = false
                butCreate.isEnabled = true
            }
            butCreate.isEnabled = false
        }})
}