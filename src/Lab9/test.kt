package Lab9

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException
import javax.swing.*


fun main(){
    val window = JFrame("DnD Example")
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.size = Dimension(500, 300)

    val blackLine = BorderFactory.createLineBorder(Color.black)
    val myFont = Font("Times", Font.ITALIC, 25)
    val tArea = JTextArea()
    tArea.font = myFont
    tArea.dragEnabled = true
    tArea.border = blackLine
    val list = JList<String>()
    list.dragEnabled = true
    list.preferredSize = Dimension(100, 100)
    list.border = blackLine
    val lModel = DefaultListModel<String>()
    list.model = lModel
    list.font = myFont
    lModel.addAll(arrayListOf("hello", "world", " ", "!", ","))

    list.dropMode = DropMode.INSERT  //устанавливаем режим приема данных при d&d - вставка эл-та в список
    list.setTransferHandler(object : TransferHandler() { //задаем обработчик приема/передеачи объекта при d&d
        private var index = 0 //индекс текущего элемента
        private var beforeIndex = false //переменная, отвечающая за признак перемещения объекта - перед или после текущего выбора

        override fun getSourceActions(comp: JComponent): Int {
            //перегружаем метод, определяющий действие при d&d (копирование или перемещение)
            return MOVE  //выбираем Перемещение
        }

        override fun createTransferable(comp: JComponent): Transferable {  //перегружаем метод для выбора эл-та для передачи при d&d
            index = list.getSelectedIndex() //получаем индекс выделенного эл-та списка (текущий номер)
            return StringSelection(list.getSelectedValue()) //возвращаем сам выбранный при d&d объект списка
        }
        //перегружаем метод для действия после окончания вставки объекта, чтобы удалить старый объект
        override fun exportDone(comp: JComponent, trans: Transferable, action: Int) {
            if (action == MOVE) { //если выбран способ Перемещение
            //то смотрим на признак индекса перемещения, если новый объект появится перед текущим,
                if (beforeIndex) lModel.remove(index + 1) //то удаляем объект с номером на 1 больше от текущего
                else // иначе удаляем объек с текущим номером
                    lModel.remove(index)
            }
        }

        override fun canImport(support: TransferSupport): Boolean { //перегружаем метод для разрешения импорта объекта
            return support.isDataFlavorSupported(DataFlavor.stringFlavor)
        }

        override fun importData(support: TransferSupport): Boolean { //перегружаем метод для вставки объекта
            //получаем объект для вставки в список
            val s = support.transferable.getTransferData(DataFlavor.stringFlavor) as String
            //получаем объект, представляющий место вставки в список
            val dl = support.dropLocation as JList.DropLocation
            lModel.add(dl.index, s) //вставляем объект в список
            //устанавливаем новое значение для признака перемещения объекта - перед или после текущего выбора
            beforeIndex = if (dl.index < index) true else false
            return true
        }
    })

    val borderLay = BorderLayout(20,20)
    window.layout = borderLay
    window.add(tArea, BorderLayout.CENTER)
    window.add(list, BorderLayout.EAST)
    window.setLocationRelativeTo(null)
    window.isVisible = true
}