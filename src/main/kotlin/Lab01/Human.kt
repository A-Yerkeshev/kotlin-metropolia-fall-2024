package Lab01

open class Human(var name: String, age: Int) {
    var age = age
    private set

    fun getOlder() {
        age++
    }
}