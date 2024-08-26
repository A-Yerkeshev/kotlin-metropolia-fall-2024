package Classworks.cw23_08

class numToWords() {
    val words = mapOf(Pair("0", "zero"), Pair("1", "one"), Pair("2", "two"), Pair("3", "three"), Pair("4", "four"), Pair("5", "five"), Pair("6", "six"), Pair("7", "seven"), Pair("8", "eight"), Pair("9", "nine"))
    fun translate(num: Any): String {
        val num = num.toString()

        if (num == "") return ""
        return words[num[0].toString()] + "-" + translate(num.substring(1))
    }
}