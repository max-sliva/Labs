package Lab3

fun main(){
    var fields = HashMap<String, Int>()
    fields["Иванов"] = 25
    fields["Петров"] = 30
    fields["Сидоров"] = 35
    fields.forEach { (k, v) ->
        println("$k  -  $v")
    }

}