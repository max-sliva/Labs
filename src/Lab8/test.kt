package Lab8

fun main(){
    val myAuto: Auto = Auto("Ford", 400)
    println("${myAuto.firm} ${myAuto.maxSpeed}")
    val myAuto2 = Auto()
    println("${myAuto2.firm} ${myAuto2.maxSpeed}")
    val myAuto3 = Auto(maxSpeed = 200)
    println("${myAuto3.firm} ${myAuto3.maxSpeed}")
    println(myAuto3)

    val myCar1 = Car(firm = "Mers", numDoors = 2, fullTime = true)
    println(myCar1)
    val myTruck = Truck("Kamaz", 200,"no_picture.png", "Masters", 500, false)
    var myGarage = GarageAuto()
    myGarage.addAuto(myAuto)
    myGarage.addAuto(myCar1)
    myGarage.addAuto(myTruck)
    println(myGarage)
//    if (myGarage.findAuto(myCar1)) println("Есть машина $myCar1")
//    myGarage.count()
    garageToPPTX(myGarage, "1.pptx")
}