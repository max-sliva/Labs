package Lab5

import java.io.IOException
import java.io.ObjectInputStream
import java.io.FileInputStream



fun main(){
    val myAuto: Auto = Auto("Ford", 400)
//    println("${myAuto.firm} ${myAuto.maxSpeed}")
//    val myAuto2 = Auto()
//    println("${myAuto2.firm} ${myAuto2.maxSpeed}")
//    val myAuto3 = Auto(maxSpeed = 200)
//    println("${myAuto3.firm} ${myAuto3.maxSpeed}")
//    println(myAuto3)

    val myCar1 = Car(firm = "Mers", numDoors = 2)
    val myTruck = Truck("Kamaz", 200, "Masters", 500, false)
    var myGarage = GarageAuto()
//    myGarage.addAuto(myAuto)
    myGarage.addAuto(myCar1)
    myGarage.addAuto(myTruck)
    println(myGarage)
    myGarage.writeToFile("autos1.dat")
    myGarage.readFromFile("autos1.dat")
//    if (myGarage.findAuto(myCar1)) println("Есть машина $myCar1")
//    myGarage.count()
}
