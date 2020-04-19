package Lab8

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.File
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
var currentImageFile = "no_picture.png"

fun main() {
    var form: JFrame = JFrame("Окно")
    form.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    form.setLocationRelativeTo(null)  // чтоб форма появилась в центре экрана
    buildNorth(form)        //вызываем функцию для заполнения верхней части формы
    buildCenter(form)        //вызываем функцию для заполнения центральной части формы
    buildSouth(form)
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
    butPrintGarage.isVisible = false
    butPrintGarage.addActionListener {
        println("$garage")
    }
    northBoxUp.add(butPrintGarage)          //добавляем кнопку "Create"
    northBoxUp.add(Box.createHorizontalGlue())  //добавляем пружину после кнопки
    var auto = Auto()  //объект класса авто
    val addButton = JButton("Add")  //кнопка для добавления данных в объект и в JList
    butCreate.addActionListener{ //слушатель нажатия на кнопку
        currentImageFile = "no_picture.png"
        northBoxDown.removeAll()  //удаляем всё с нижней части верхней панели
        northBoxDown.isVisible = true // показываем нижнюю часть верхней панели
        butCreate.isEnabled = false  //делаем неактивной кнопку "Create"
        //создаем HashMap с полями и типами полей либо класса Car, либо клаcса Truck в зависимости от выбора в комбо-боксе
        var fields = if (combo.selectedItem == "Car") hashMapOf("firm" to "String", "maxSpeed" to "Int",
                                                                                    "model" to "String", "numDoors" to "Int", "fullTime" to "Boolean")
                                           else hashMapOf("firm" to "String", "maxSpeed" to "Int",
                                                          "model" to "String", "power" to "Int", "trailer" to "Boolean")
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
        println("curImage = $currentImageFile")
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
                if (k!="image") {
                    val textField = JTextField(10)
                    box.add(textField)
                    northBoxDown.add(box)
                    fieldsHash.put(k, textField)
                }
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

//    currentImageFile = "no_picture.png"
    var jLabel = JLabel()
    loadImage(jLabel, currentImageFile)
    northBoxDown.add(jLabel)
    jLabel.addMouseListener(object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent?) {
            super.mousePressed(e)
            var fileDialog = FileDialog(northBoxDown.parent.parent.parent.parent.parent as JFrame)
            fileDialog.mode = FileDialog.LOAD
            fileDialog.title = "Открыть изображение"
            fileDialog.isVisible = true
            val fileName = fileDialog.directory+fileDialog.file
            println(fileName)
            loadImage(jLabel, fileName)
            currentImageFile = fileName
        }
    })

    return fieldsHash
}

fun loadImage(label: JLabel, imagePath: String) {// функция для загрузки изображения в лейбл
    val icon = ImageIcon(imagePath)
    val width  = icon.iconWidth
    val height = icon.iconHeight
    val imageScale = width / height.toFloat()
    val newWidth = 70
    val newHeight = (newWidth / imageScale).toInt()
    label.icon = ImageIcon(icon.image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH))
}

fun createCar() : Car{//TODO add getting image path from label icon
    val firmField = fieldsMap.get("firm") as JTextField
    val maxSpeedField = fieldsMap.get("maxSpeed") as JFormattedTextField
    val modelField = fieldsMap.get("model") as JTextField
    val numDoorsField = fieldsMap.get("numDoors") as JFormattedTextField
    val fullTimeField = fieldsMap.get("fullTime") as JComboBox<Boolean>
    return Car(firmField.text, maxSpeedField.text.toInt(), currentImageFile, modelField.text, numDoorsField.text.toInt(), fullTimeField.selectedItem as Boolean)
}

fun createTruck() : Truck{
    val firmField = fieldsMap.get("firm") as JTextField
    val maxSpeedField = fieldsMap.get("maxSpeed") as JFormattedTextField
    val modelField = fieldsMap.get("model") as JTextField
    val powerField = fieldsMap.get("power") as JFormattedTextField
    val trailerField = fieldsMap.get("trailer") as JComboBox<Boolean>
    return Truck(firmField.text, maxSpeedField.text.toInt(), currentImageFile, modelField.text, powerField.text.toInt(), trailerField.selectedItem as Boolean)
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

            currentImageFile = tempAuto.image

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

fun buildSouth(form: JFrame) {
    var southBox = Box(BoxLayout.X_AXIS)
    val butSave = JButton("Save")
    val butLoad = JButton("Load")
    val butToPDF = JButton("toPDF") //создаем новую кнопку
    val butToWord = JButton("toWord") //создаем новую кнопку
    val butToPPTX = JButton("toPPTX") //создаем новую кнопку
    southBox.add(butSave)
    southBox.add(Box.createHorizontalGlue())
    southBox.add(butLoad)
    southBox.add(Box.createHorizontalGlue()) // пружина
    southBox.add(butToPDF)
    southBox.add(Box.createHorizontalGlue()) // пружина
    southBox.add(butToWord)
    southBox.add(Box.createHorizontalGlue()) // пружина
    southBox.add(butToPPTX)
    var fileDialog = FileDialog(form)
    butSave.addActionListener {
        fileDialog.mode = FileDialog.SAVE
        fileDialog.title = "Сохранить файл"
        fileDialog.isVisible = true
        val fileName = fileDialog.directory+fileDialog.file
        println(fileName)
        garage.writeToFile(fileName)
    }
    butLoad.addActionListener {
        fileDialog.mode = FileDialog.LOAD
        fileDialog.title = "Открыть файл"
        fileDialog.isVisible = true
        val fileName = fileDialog.directory+fileDialog.file
        println(fileName)
        garage.readFromFile(fileName)
        listModel.clear()
        listModel.addAll(garage.getAll())
        form.pack()
    }
    butToPDF.addActionListener { //слушатель нажатия кнопки
        fileDialog.mode = FileDialog.SAVE //диалог в режим сохранения
        fileDialog.title = "Сохранить в PDF" //заголовок диалога сохранения
        fileDialog.setFile("*.pdf") //фильтр для файлов
        fileDialog.isVisible = true //показываем диалог сохранения
//если пользователь выбрал каталог и файл, т.е. они не содержат null
//это нужно, чтоб обработать отказ от сохранения, иначе будет ошибка
        if (!(fileDialog.directory+fileDialog.file).contains("null")) {
            val fileName = fileDialog.directory+fileDialog.file //записываем путь к файлу
            println("fileName=$fileName") //выводим полное имя файла в консоль
            val doc = PDDocument() //создаем pdf-документ
            val page = PDPage() //создаем страницу для документа
            doc.addPage(page) //добавляем страницу в документ
            val contentStream = PDPageContentStream(doc, page) //создаем поток для вывода в документ
            contentStream.beginText() //начинаем работу с потоком
//устанавливаем шрифт для документа, этот файл должен быть в корне проекта (есть на сервере)
            val font = PDType0Font.load(doc, File("arial.ttf"))
            contentStream.setFont(font, 10f) //задаем размер шрифта
            contentStream.setLeading(24.5f) //устанавливаем межстрочный интервал
            contentStream.endText()
            var i : Float = 0f
            garage.getAll().forEach { //цикл по содержимому гаража
//записываем в переменную text содержимое каждого элемента гаража, причем заменяем переводы строки на
//пробелы, а табы – на пустой символ, это нужно для корректного вывода в pdf-файл
                val text = it.toString().replace("\n", " ", true).replace("\t", "", true)
                println("text = $text") //выводим текст в консоль для проверки
                contentStream.beginText()
                val y: Float = 725f-150f*(i++)
                contentStream.newLineAtOffset(25f, y) //задаем позицию для первой строки,
                contentStream.showText(text) //добавляем текст из переменной в поток вывода
                contentStream.newLine() //добавляем переход на новую строку
                contentStream.endText()
                val pdImage = PDImageXObject.createFromFile(it.image, doc)
                val width  = pdImage.width
                val height = pdImage.height
                val imageScale = width / height.toFloat()
                val newHeight = 100f
                val newWidth = (newHeight * imageScale)
                contentStream.drawImage(pdImage, 150F, y-130, newWidth, newHeight)
            }
//            contentStream.endText() //окончание работы с текстом
            println("Content to pdf added")
            contentStream.close() //закрываем поток вывод
            doc.save(fileName) //сохраняем документ
            doc.close() //закрываем документ
            println("end pdf")
        }
    }

    butToWord.addActionListener{
        fileDialog.mode = FileDialog.SAVE //диалог в режим сохранения
        fileDialog.title = "Сохранить в word file" //заголовок диалога сохранения
        fileDialog.setFile("*.docx") //фильтр для файлов
        fileDialog.isVisible = true //показываем диалог сохранения
//если пользователь выбрал каталог и файл, т.е. они не содержат null
//это нужно, чтоб обработать отказ от сохранения, иначе будет ошибка
        if (!(fileDialog.directory+fileDialog.file).contains("null")) {
            var fileName = fileDialog.directory+fileDialog.file
            if (!fileName.contains(".docx")) fileName = fileName.plus(".docx")
            garageToWord(garage, fileName)
        }
    }

    butToPPTX.addActionListener{
        fileDialog.mode = FileDialog.SAVE //диалог в режим сохранения
        fileDialog.title = "Сохранить в pptx" //заголовок диалога сохранения
        fileDialog.setFile("*.pptx") //фильтр для файлов
        fileDialog.isVisible = true //показываем диалог сохранения
//если пользователь выбрал каталог и файл, т.е. они не содержат null
//это нужно, чтоб обработать отказ от сохранения, иначе будет ошибка
        if (!(fileDialog.directory+fileDialog.file).contains("null")) {
            var fileName = fileDialog.directory+fileDialog.file
            if (!fileName.contains(".pptx")) fileName = fileName.plus(".pptx")
            garageToPPTX(garage, fileName)
        }
    }
    form.add(southBox, BorderLayout.SOUTH)
}