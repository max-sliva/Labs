package Lab7

class Car(firm: String = "No", maxSpeed: Int = 0, image: String = "no_picture.png", var model: String = "Нет", var numDoors: Int = 4, var fullTime: Boolean = false): Auto(firm, maxSpeed, image) {
    override fun toString() = "Легковая машина (фирма=$firm, максСкорость=$maxSpeed, модель=$model, кол-во дверей=$numDoors, " +
                                "полноприводный=${if (fullTime) "да" else "нет"})"

}

