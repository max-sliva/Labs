package Lab8

import java.io.*


class GarageAuto {
    private var masAuto = ArrayList<Auto>();
    fun addAuto (auto: Auto) {
        masAuto.add(auto)
    }
    fun findAuto(a: Auto) : Boolean{
        return masAuto.contains(a)
    }
    fun changeAuto(i: Int, a: Auto){
        masAuto.set(i, a)
    }

    override fun toString(): String {
        var str = "В гараже:\n "
        for (a in masAuto) {
            str = str+("\t" + a+"\n")
        }
        return str
    }
    fun count(){
//        var c: Int = 0
//        var t: Int = 0
//        for (a in masAuto){
//            if (a is Car) c++
//            if (a is Truck) t++
//        }
//        println("Легковых машин: $c, грузовых: $t")
         for (a in masAuto) {
             if (a is Car) println("Легковая машина")
         }
    }

    fun writeToFile(fileName: String) { //метод для записи массива объектов в файл
        try { //секция для небезопасной работы с файлами
            val fos = FileOutputStream(fileName) //поток вывода данных в файл
            val oos = ObjectOutputStream(fos) //поток передачи объектов в другой поток
            oos.writeObject(masAuto) //записываем весь массив сразу
            println("Done writing")
            oos.close() //закрываем все потоки
            fos.close()
        } catch (e: IOException) { //ловим исключения
            e.printStackTrace()
        }
    }

    fun readFromFile(fileName: String) { //метод для чтения массива объектов из файла
        try {
            val fin = FileInputStream(fileName) //поток ввода данных из файла
            val ois = ObjectInputStream(fin)  //поток получения объектов из другого потока
            masAuto.clear()  //очищаем массив
            masAuto = ois.readObject() as ArrayList<Auto> //получаем массив из потока объектов
            println("From file: ")
            masAuto.forEach { println("$it") } //выводим в консоль полученный массив для проверки
            fin.close()  //закрываем все потоки
            ois.close()
        } catch (ex: Exception) {  //ловим исключения
            ex.printStackTrace()
        }
    }

    fun getAll(): ArrayList<Auto> { //для получения массива объектов из гаража
        return masAuto
    }

}