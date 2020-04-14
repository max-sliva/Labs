package Lab1

fun main() {
    for (i in 1..3) {
        println(i)
    }

    println()

    for (i in 6 downTo 0 step 2) {
        println(i)
    }
    var x = 10
    while (x > 0) {
        print("x=$x ")
        x--
    }
    println()
    var y = 0
    do{
        println("${y++}")
    } while (y < 10)
}
