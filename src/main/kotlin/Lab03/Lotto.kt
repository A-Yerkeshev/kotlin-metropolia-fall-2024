package Lab03

class Lotto(private val n: Int, private val lottoRange: IntRange, secretNumbers: List<Int>? = null) {
    private val secretNumbers = secretNumbers ?: pickNDistinct(lottoRange, n)

    fun pickNDistinct(range: IntRange, n: Int): List<Int> {
        val res = mutableSetOf<Int>()
        while (res.size < n) {
            res.add(range.random())
        }
        return res.toList()
    }

    fun numDistinct(list: List<Int>): Int {
        return list.toSet().size
    }

    fun numCommon(list1: List<Int>, list2: List<Int>): Int {
        return list1.intersect(list2).size
    }

    fun isLegalLottoGuess(guess: List<Int>, range: IntRange = lottoRange, count: Int = n): Boolean {
        if (guess.size != count) { return false }
        if (numDistinct(guess) != count) { return false }

        for (i in guess) {
            if (i !in range) { return false }
        }

        return true
    }

    fun checkGuess(guess: List<Int>, secret: List<Int> = secretNumbers): Int {
        if (!isLegalLottoGuess(guess)) { return -1 }
        return numCommon(guess, secret)
    }
}