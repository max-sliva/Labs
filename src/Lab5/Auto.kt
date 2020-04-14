package Lab5

import java.io.Serializable

open class Auto(var firm: String = "Без названия", var maxSpeed: Int = 0) : Serializable{
    companion object { //специальный дополнительный объект для сериализации
        private const val serialVersionUID: Long = 123
    }
}
