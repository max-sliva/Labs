package Lab8

import org.apache.poi.util.Units
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.swing.ImageIcon

fun main(){
    toWordDocx("Hello world!!!")
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