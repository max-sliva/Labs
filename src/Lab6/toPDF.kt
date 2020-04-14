package Lab6

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.awt.Desktop
import java.io.File
import java.io.IOException

fun main() {
    //Creating a PDF Document
    val doc = PDDocument()
    val page = PDPage()
    doc.addPage(page)

    val contentStream = PDPageContentStream(doc, page)

    //Begin the Content stream
    contentStream.beginText()


    //загружаем нужный шрифт с поддержкой кириллицы
    val font = PDType0Font.load( doc, File( "arial.ttf" ) )
    contentStream.setFont(font, 16f)

    //межстрочный интервал
    contentStream.setLeading(24.5f)

//    Setting the position for the line
    contentStream.newLineAtOffset(25f, 725f)

    val text1 = "Пример добавления документа в pdf-файл. Hello, world "
    val text2 = "in pdf style! ☺"

    //Adding text in the form of string
    contentStream.showText(text1)
    contentStream.newLine()
    contentStream.showText(text2)
    //Ending the content stream
    contentStream.endText()

    println("Content added")

    //Closing the content stream
    contentStream.close()

    //Saving the document
    doc.save("new1.pdf")

    //Closing the document
    doc.close()
//    val proc = Runtime.getRuntime().exec("cmd F:\\KtlnProjects\\Labs\\new1.pdf")
    try {
        val desktop = Desktop.getDesktop()
        if (desktop.isSupported(Desktop.Action.OPEN)) {
            desktop.open(File("new1.pdf"))
        } else {
            println("Open is not supported")
        }
    } catch (exp: IOException) {
        exp.printStackTrace()
    }
    println("end")
}

