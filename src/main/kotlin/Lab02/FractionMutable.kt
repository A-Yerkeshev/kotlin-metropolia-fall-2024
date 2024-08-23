package Lab02

class FractionMutable(var numerator: Int, var denominator: Int, var sign: Int = 1) {
    init {
        normalize()
    }

    fun add(fraction: FractionMutable) {
        val lcd = lcd(fraction)

        // Bring both fractions to least common denominator
        val num1 = numerator * lcd / denominator
        val num2 = fraction.numerator * lcd / fraction.denominator

        numerator = sign * num1 + fraction.sign * num2
        if (numerator < 0) {
            sign *= -1
            numerator *= -1
        }
        denominator = lcd
        normalize()
    }

    fun negate() {
        sign *= -1
    }

    fun mult(fraction: FractionMutable) {
        numerator *= fraction.numerator
        denominator *= fraction.denominator
        sign *= fraction.sign
        normalize()
    }

    fun div(fraction: FractionMutable) {
        fraction.numerator = fraction.denominator.also { fraction.denominator = fraction.numerator }
        mult(fraction)
    }

    fun intPart(): Int {
        return numerator / denominator
    }

    private fun lcd(fraction: FractionMutable): Int {
        val factors1 = primeFactors(denominator)
        val factors2 = primeFactors(fraction.denominator)

        return (factors1 + factors2).toSet().reduce { sum, n -> sum * n }
    }

    private fun primeFactors(n: Int): List<Int> {
        var num = n
        val factors = mutableListOf<Int>()

        var divisor = 2
        while (num >= 2) {
            while (num % divisor == 0) {
                factors.add(divisor)
                num /= divisor
            }
            divisor++
        }

        return factors
    }

    private fun normalize() {
        var numeratorFactors = primeFactors(numerator).toMutableList()
        var denominatorFactors = primeFactors(denominator).toMutableList()
        val commonFactors = numeratorFactors.intersect(denominatorFactors)

        commonFactors.forEach {
            numeratorFactors.remove(it)
            denominatorFactors.remove(it)
        }

        numerator = numeratorFactors.fold(1) { sum, n -> sum * n }
        denominator = denominatorFactors.fold(1) { sum, n -> sum * n }
    }

    override fun toString(): String {
        var res: String = if (sign < 0) "-" else ""
        res += "$numerator/$denominator"
        return res
    }
}