package Lab8;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFErrorConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.examples.CellStyleDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ToOffice {

//метод для записи данных в docx файл, параметры - массив с заголовками для столбцов и список List с содержимым таблицы 
	public static File toWordDocx(String str){
		XWPFDocument document=new XWPFDocument(); //создаем документ Word
		XWPFParagraph paragraph = document.createParagraph(); //создаем абзац в документе
		XWPFRun run=paragraph.createRun(); //создаем объект для записи в полученный ранее абзац
		run.setText(str); //текст перед таблицей
		File file = new File("createparagraph.docx"); //создаем файл
		try {
			FileOutputStream out = new FileOutputStream(file); //создаем файловый поток вывода с новым файлом
			document.write(out); //пишем в файл из созданного объекта
			out.close(); // закрываем потоки вывода
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("createparagraph.docx written successfully");
		return file;  //возвращаем файл
	}
}
