package Lab6

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.io.File

//fun my(var number : Int) { number ++ }

fun main() {

//    val doc = PDDocument() //создаем pdf-документ
//    val page = PDPage() //создаем страницу для документа
//    doc.addPage(page) //добавляем страницу в документ
//    val contentStream = PDPageContentStream(doc, page) //создаем поток для вывода данных в документ
//    contentStream.beginText() //начинаем работу с потоком
//    val font = PDType0Font.load( doc, File("arial.ttf")) //загружаем нужный шрифт с поддержкой кириллицы
////этот файл должен лежать в корневой папке проекта
//    contentStream.setFont(font, 16f) //задаем размер шрифта
//    contentStream.setLeading(24.5f) //устанавливаем межстрочный интервал
//    contentStream.newLineAtOffset(25f, 725f) //задаем позицию для первой строки,
////относительно нижнего левого края документа
////создаем строки с текстом, который поместим в pdf-файл
//    val text1 = "Пример добавления документа в pdf-файл. Hello, world "
//    val text2 = "in pdf style! ☺ lol"
//    contentStream.showText(text1) //добавляем текст из первой переменной в поток вывода
//    contentStream.newLine() //добавляем переход на новую строку
//    contentStream.showText(text2) //добавляем текст из второй переменной в поток вывода
//    contentStream.endText() //окончание работы с текстом
//    println("Content added") //вывод в консоль для отладки
//    contentStream.close() //закрываем поток вывода
//    doc.save("new1.pdf") //сохраняем документ
//    doc.close() //закрываем документ
}