package Lab1

fun main(){
    val a = 5
    val b = 3
    var x = if (a>b) a else b
    println(x)

    val x1:Int = readLine()!!.toInt()
    when {
        x1 % 3 == 0 -> println("число делится на 3")
        x1 % 5 == 0 -> println("число делится на 5")
        else -> println("другое")
    }

    when (x1) {
        in 0..9 -> println("x - от 0 до 9")
        in 10..99 -> println("x - от 11 до 99")
        else -> println("другое")
    }


}

