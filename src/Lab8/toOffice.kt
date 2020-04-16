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
import kotlin.reflect.full.memberProperties


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
    val ppt = XMLSlideShow()
    val slideMaster = ppt.slideMasters[0]
    val titleLayout = slideMaster.getLayout(SlideLayout.TITLE)
    //adding slides to the slodeshow
    val slide0 = ppt.createSlide(titleLayout)
    val title1 = slide0.getPlaceholder(0)
    title1.setText("Гараж");
    val title2 = slide0.getPlaceholder(1)
    title2.clearText()
    title2.setText("легковых и грузовых машин")

    garage.getAll().forEach {
        val slideLayout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        val slide1 = ppt.createSlide(slideLayout)
        val title: XSLFTextShape = slide1.getPlaceholder(0)
        title.text = "Первый слайд"//setting the title in it
        println("class = ${it.javaClass}")
        var fields = if (it.javaClass.toString().contains("Car")) Car::class.memberProperties else Truck::class.memberProperties
        fields.forEach {
            println("\t${it.name}")
        }
//        it.
    }
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