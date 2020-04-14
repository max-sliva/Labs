package Lab3

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
        var c: Int = 0
        var t: Int = 0
        for (a in masAuto){
            if (a is Car) c++
            if (a is Truck) t++
        }
        println("Легковых машин: $c, грузовых: $t")
//         for (a in masAuto) {
//             if (a is Car) println("Легковая машина")
//         }
    }
}