package Lab8

class Truck (firm: String = "No",
             maxSpeed: Int = 0,
             image: String = "no_picture.png",
             var model: String = "Нет",
             var power: Int = 0,
             var trailer: Boolean = false) : Auto(firm, maxSpeed, image){

    fun setAllInfo() {
         print("Введите фирму-производитель грузового авто: ")
         firm = readLine()!!
         print("Введите максимальную скорость грузового авто: ")
         maxSpeed = readLine()!!.toInt();
         print("Введите модель грузового авто: ")
         model = readLine()!!
         print("Введите мощность грузового авто: ");
         power = readLine()!!.toInt()
         print("Введите признак прицепа грузового авто (true/false): ");
         trailer = readLine()!!.toBoolean()
         println()
    }

    override fun toString() = "\n\tГрузовик"+"\n\t"+" Фирма: "+firm+"\n\t"+" Максимальная скорость: " +maxSpeed+
            "\n\t"+" Модель: "+model+"\n\t"+" Мощность: "+power+"\n\t"+" Признак прицепа: " +trailer+"\n"


}