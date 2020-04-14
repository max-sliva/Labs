package Lab4_extra
import Lab3.Car
import Lab3.Truck
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import Lab3.Auto
import Lab3.GarageAuto
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

var northBox = Box(BoxLayout.Y_AXIS)
var northBoxUp = Box(BoxLayout.X_AXIS)
var northBoxDown = Box(BoxLayout.X_AXIS)

var listOfAuto = JList<Auto>()
var listModel = DefaultListModel<Auto>()
var garage = GarageAuto()
var fieldsArray = ArrayList<JTextField>()
var fieldsMap = HashMap<String, JTextField>()

fun main(){
    var form: JFrame = JFrame("Окно")
    form.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    form.size = Dimension(200,200)
    form.setLocationRelativeTo(null)
    buildNorth1(form);
    listOfAuto.model = listModel
    form.add(listOfAuto, BorderLayout.CENTER)
    listOfAuto.addMouseListener(object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent?) {
            super.mousePressed(e)
            val tempList= e!!.source as JList<Auto>
            println(tempList.selectedValue)
            val tempAuto = tempList.selectedValue
            northBoxDown.removeAll()
            northBoxDown.isVisible = true
            var fields = if (tempAuto is Car) Car::class.memberProperties else Truck::class.memberProperties
            fillNorthBoxDown(fields)
            val changeButton = JButton("Change")
            northBoxDown.add(changeButton)
            northBoxDown.add(Box.createHorizontalStrut(10))
            val cancelButton = JButton("Cancel")
            northBoxDown.add(cancelButton)
            form.pack()
            changeButton.addActionListener{
                northBoxDown.isVisible = false
            }
            cancelButton.addActionListener {
                northBoxDown.isVisible = false
            }
        }
    })
    form.pack()
    form.minimumSize = form.size
    form.isVisible = true
}

fun buildNorth1(form: JFrame) {
    var combo = JComboBox<String>()
    combo.addItem("Car")
    combo.addItem("Truck")
    northBoxUp.add(combo)
    northBoxUp.add(Box.createHorizontalStrut(10))
    val butCreate = JButton("Create")
    northBoxUp.add(butCreate)
    northBoxUp.add(Box.createHorizontalGlue())
    val addButton = JButton("Add")
    var auto = Auto()
    butCreate.addActionListener{
        northBoxDown.removeAll()
        northBoxDown.isVisible = true
        butCreate.isEnabled = false
        var fields = if (combo.selectedItem == "Car") Car::class.memberProperties else Truck::class.memberProperties
        fillNorthBoxDown(fields)
        northBoxDown.add(addButton)
        form.pack()
    }
    addButton.addActionListener {
        butCreate.isEnabled = true
        northBoxDown.isVisible = false
        if (combo.selectedItem == "Car") auto = Car(
            fieldsMap.get("firm")!!.text,
                                                    fieldsMap.get("maxSpeed")!!.text.toInt(),
                                                    fieldsMap.get("model")!!.text,
                                                    fieldsMap.get("numDoors")!!.text.toInt(),
                                                    fieldsMap.get("fullTime")!!.text.toBoolean())
        else auto = Truck(
            fieldsMap.get("firm")!!.text,
            fieldsMap.get("maxSpeed")!!.text.toInt(),
            fieldsMap.get("model")!!.text,
            fieldsMap.get("power")!!.text.toInt(),
            fieldsMap.get("trailer")!!.text.toBoolean())

        println("auto = $auto")
        listModel.addElement(auto)
        garage.addAuto(auto)
        form.pack()
    }
    northBoxDown.isVisible = false
    northBox.add(northBoxUp)
    northBox.add(northBoxDown)
    form.add(northBox, BorderLayout.NORTH)
}

fun fillNorthBoxDown(fields : Collection<KProperty1<out Auto, *>>){
    for (field in fields){
        fieldsArray.clear()
        println(field.name)
        var box = Box(BoxLayout.Y_AXIS)
        box.add(JLabel(field.name))
        fieldsArray.add(JTextField(10))
        box.add(fieldsArray.get(fieldsArray.size-1))
        fieldsMap.set(field.name, fieldsArray.get(fieldsArray.size-1))
        northBoxDown.add(box)
        northBoxDown.add(Box.createHorizontalStrut(10))
    }
}