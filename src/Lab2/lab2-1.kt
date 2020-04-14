package Lab2

import java.util.*
import kotlin.random.Random

fun main() {
//    val x: IntArray = intArrayOf(5, 2, 8)
//    x.forEach { print("$it ") } //выведет на экран 5 2 8
//    println()
//
//    var arr = IntArray(5)
//    for (i in arr.indices){
//        arr[i] = Random.nextInt(10)
//        print("${arr[i]} ")
//    }
//    println()
//
//    var mas = IntArray(5) {Random.nextInt(10)} // var mas = IntArray(5, {Random.nextInt(10)})
//    mas.forEach { print("$it ") } //выведет на экран 5 2 8

    var ArrNums = intArrayOf(5,4,3,2,3,5,1)
    println("Уникальные эл-ты в ArrNum: " +ArrNums.distinct())
}
