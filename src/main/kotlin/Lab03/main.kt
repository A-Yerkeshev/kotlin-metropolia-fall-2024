package Lab03

fun main() {
    playLotto()
}

fun readNDistinct(low: Int, high: Int, n: Int): List<Int> {
    var input: List<Int>

    do {
        print("Give ${n} numbers from ${low} to ${high}, separated by commas: ")
        input = readLine()!!.split(",").map { it.toIntOrNull() }.filterNotNull()
    } while (input.toSet().size != n || input.any { it < low || it > high })

    return input
}

fun playLotto() {
    var exit = false

    do {
        val lotto = Lotto(7, 1..40)
        val guess = readNDistinct(1, 40, 7)
        val guessedCorrectly = lotto.checkGuess(guess)
        val computerGuess = findLotto(lotto)
        println("lotto numbers: ${guess}, you got ${guessedCorrectly} correct. computer guess in ${computerGuess.first} steps is ${computerGuess.second}")
        print("More? (Y/N): ")
        val command = readLine()!!.uppercase()
        if (command == "N") {
            exit = true
        }
    } while(!exit)
}

fun findLotto(lotto: Lotto): Pair<Int, List<Int>> {
    var guess = lotto.pickNDistinct(1..40, 7)
    var count = 0

    while (lotto.checkGuess(guess) != 7) {
        guess = lotto.pickNDistinct(1..40, 7)
        count++
    }

    return Pair(count, guess)
}