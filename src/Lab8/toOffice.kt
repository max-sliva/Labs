package Lab8

import Lab3.Car
import Lab3.Truck
import org.apache.poi.sl.usermodel.PictureData
import org.apache.poi.util.IOUtils
import org.apache.poi.util.Units
import org.apache.poi.xslf.usermodel.SlideLayout
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xslf.usermodel.XSLFTextShape
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.awt.Rectangle
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.swing.ImageIcon
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.valueParameters


fun main(){
//    toWordDocx("Hello world!!!")
    toPPT("my.pptx")
}

//fun toPPT(garage: GarageAuto, fileName: String){
fun toPPT(fileName: String){
    val ppt = XMLSlideShow()

    val slideMaster = ppt.slideMasters[0]
    val titleLayout = slideMaster.getLayout(SlideLayout.TITLE)
    //adding slides to the slodeshow
    val slide0 = ppt.createSlide(titleLayout)
    val title1 = slide0.getPlaceholder(0)
    title1.setText("Супер презентация");
    val title2 = slide0.getPlaceholder(1)
    title2.clearText()
    title2.setText("от лучших создателей презентаций")
    val slideLayout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
    val slide1 = ppt.createSlide(slideLayout)
    val title: XSLFTextShape = slide1.getPlaceholder(0)
    title.text = "Первый слайд"//setting the title in it
//    val body: XSLFTextShape = slide1.getPlaceholder(1)//selection of body placeholder
//    body.clearText()  //clear the existing text in the slide
//    body.addNewTextParagraph().addNewTextRun().setText("Тут крутое содержание слайда")    //adding new paragraph

    val content: XSLFTextShape = slide1.getPlaceholder(1)
    content.clearText()
    val p1 = content.addNewTextParagraph()
    p1.indentLevel = 0
    p1.isBullet = true
    val r1 = p1.addNewTextRun()
    r1.setText("Первый элемент")
    val p2 = content.addNewTextParagraph()
    val r2 = p2.addNewTextRun()
    r2.setText("Второй элемент")

    val picture: ByteArray = IOUtils.toByteArray(FileInputStream("Kotlin-logo.png"))

    val idx = ppt.addPicture(picture, PictureData.PictureType.PNG)
    var pic = slide1.createPicture(idx)
    pic.anchor = Rectangle(320, 230, 100, 92);
    val file = File(fileName)
    val out = FileOutputStream(file)
    ppt.write(out)
    out.close()
    println("Done writing pptx")
}

fun garageToPPTX(garage: GarageAuto, fileName: String){
    val ppt = XMLSlideShow() //создаем документ с презентацией
    val slideMaster = ppt.slideMasters[0] //объект для видов компоновок
    val titleLayout = slideMaster.getLayout(SlideLayout.TITLE) //компоновка титульного слайда

    val slide0 = ppt.createSlide(titleLayout) //создаем титульный слайд с выбранной компоновкой
    val title1 = slide0.getPlaceholder(0) //получаем доступ к основному заголовку титульного слайда
    title1.text = "Гараж"; //делаем свой заголовок
    val title2 = slide0.getPlaceholder(1) //получаем доступ к подзаголовку титульного слайда
    title2.clearText() //очищаем его
    title2.text = "легковых и грузовых машин" //делаем свой подзаголовок
    //далее в цикле по объектам массива гаража будем создавать отдельный слайд для каждого объекта
    garage.getAll().forEach {
        val tempAuto = it //сохраняем ссылку на очередной объект массива
        //получаем объект для компоновки слайда с заголовком и содержимым
        val slideLayout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        val slide1 = ppt.createSlide(slideLayout) //создаем слайд с выбранной компоновкой
        val title = slide1.getPlaceholder(0) //получаем доступ к заголовку слайда
        val jClassStr = tempAuto.javaClass.kotlin //выясняем класс текущего объекта
//        println("class = ${jClassStr.toString().split('.').last()}")
        println("class = ${jClassStr}") //выводим название класса в консоль для проверки
//обычно название класса представлено в виде "class Пакет.Имя", нам нужно только имя, поэтому нужно взять слово
        val objClass = jClassStr.toString().split('.').last() //после последней точки
        title.text = objClass //задаем полученное имя заголовку слайда
        val content: XSLFTextShape = slide1.getPlaceholder(1) //получаем доступ к основному телу слайда
        content.clearText() //очищаем содержимое основного тела слайда
    //полусчаем список полей класса текущего объекта и делаем цикл по списку
        jClassStr.memberProperties.forEach {
            println("\t${it.name}: ${it.get(tempAuto)}") //выводим имя текущего поля и его значение для текущего объекта
            if (it.name!="image") {  //если поле - не изображение
                val p1 = content.addNewTextParagraph() //создаем тестовый объект в виде списка
                p1.indentLevel = 0
                p1.isBullet = true
                val r1 = p1.addNewTextRun()
                r1.setText("${it.name}: ${it.get(tempAuto)}")
            } else {
                val picture: ByteArray = IOUtils.toByteArray(FileInputStream("${it.get(tempAuto)}"))
                val idx = ppt.addPicture(picture, PictureData.PictureType.PNG)
                var pic = slide1.createPicture(idx)
                val icon = ImageIcon("${it.get(tempAuto)}") //Объект ImageIcon для временной загрузки изображения из поля объекта
                val width  = icon.iconWidth //чтоб получить реальные размеры картинки для вычисления
                val height = icon.iconHeight //соотношения сторон
                val imageScale = width / height.toFloat()
                val newHeight = 200 //задаем желаемую высоту картинки
                val newWidth = (newHeight * imageScale)  //получаем соотвествующую ширину картинки
                pic.anchor = Rectangle(350, 130, newWidth.toInt(), newHeight);
            }
        }
    }
    val file = File(fileName)
    val out = FileOutputStream(file)
    ppt.write(out)
    out.close()
    println("Done writing pptx")
}

fun toWordDocx(str: String){
    val document = XWPFDocument() //создаем документ Word в формате docx
    val paragraph = document.createParagraph() //создаем абзац в документе
    val run = paragraph.createRun() //создаем объект для записи в полученный ранее абзац
    run.setText(str) //пишем текст
    run.addBreak() //переход на новую строку
    val icon = ImageIcon("Kotlin-logoe.png") //Объект ImageIcon для временной загрузки изображения
    val width  = icon.iconWidth  //чтоб получить реальные размеры картинки для вычисления
    val height = icon.iconHeight  //соотношения сторон
    val imageScale = width / height.toFloat()
    val newHeight = 100.0  //задаем желаемую высоту картинки
    val newWidth = (newHeight * imageScale)
    val inStream = FileInputStream("Kotlin-logo.png") //поток для загрузки картинки в docx
    //вставляем картинку в документ
    run.addPicture(inStream, XWPFDocument.PICTURE_TYPE_PNG, "Kotlin-logo.png", Units.toEMU(newWidth), Units.toEMU(newHeight))
    inStream.close() //закрываем поток с картинкой
    val file = File("garageToWord.docx") //создаем файл
    val out = FileOutputStream(file) //создаем файловый поток вывода с новым файлом
    document.write(out) //пишем в файл из созданного объекта
    out.close() // закрываем поток вывода
    document.close() //закрываем документ
    println("docx written successfully")
}

fun garageToWord(garage: GarageAuto, fileName: String){
    val document = XWPFDocument() //создаем документ Word
    val paragraph = document.createParagraph() //создаем абзац в документе
    val run = paragraph.createRun() //создаем объект для записи в полученный ранее абзац
    garage.getAll().forEach{ //цикл по всем объектам массива в гараже
        run.setText(it.toString()) //записываем строковое представление объекта в документ
        run.addBreak() //переход на новую строку
        val icon = ImageIcon(it.image) //Объект ImageIcon для временной загрузки изображения из поля объекта
        val width  = icon.iconWidth //чтоб получить реальные размеры картинки для вычисления
        val height = icon.iconHeight //соотношения сторон
        val imageScale = width / height.toFloat()
        val newHeight = 200.0 //задаем желаемую высоту картинки
        val newWidth = (newHeight * imageScale)  //получаем соотвествующую ширину картинки
        val inStream = FileInputStream(it.image) //поток для загрузки картинки в docx
        //вставляем картинку в документ
        run.addPicture(inStream, XWPFDocument.PICTURE_TYPE_PNG, it.image, Units.toEMU(newWidth), Units.toEMU(newHeight))
        inStream.close() //закрываем поток с картинкой
        run.addBreak() //переход на новую строку
    }
    val file = File(fileName) //создаем файл
    val out = FileOutputStream(file) //создаем файловый поток вывода с новым файлом
    document.write(out) //пишем в файл из созданного объекта
    out.close() // закрываем потоки вывода
    document.close() //закрываем документ
    println("garage has been written to word file successfully")
}