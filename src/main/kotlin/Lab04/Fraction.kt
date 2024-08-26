package Lab04

import kotlin.math.pow

class Fraction(numerator: Int, denominator: Int, val sign: Int = 1) {
    var numerator = numerator
        private set
    var denominator = denominator
        private set

    init {
        normalize()
    }

    fun add(fraction: Fraction): Fraction {
        val lcd = lcd(fraction)
        val resNum: Int
        val resDen: Int
        val resSign: Int

        // Bring both fractions to least common denominator
        val num1 = numerator * lcd / denominator
        val num2 = fraction.numerator * lcd / fraction.denominator

        resNum = sign * num1 + fraction.sign * num2

        if (resNum < 0) {
            resSign = -1
        } else {
            resSign = 1
        }
        resDen = lcd

        return Fraction(resNum, resDen, resSign)
    }

    fun mult(fraction: Fraction): Fraction {
        val resNum = numerator * fraction.numerator
        val resDen = denominator * fraction.denominator
        val resSign = sign * fraction.sign
        return Fraction(resNum, resDen, resSign)
    }

    operator fun div(fraction: Fraction): Fraction  {
        fraction.numerator = fraction.denominator.also { fraction.denominator = fraction.numerator }
        return mult(fraction)
    }

    fun intPart(): Int {
        return numerator / denominator
    }

    fun negate(): Fraction {
        return Fraction(numerator, denominator, sign * -1)
    }

    private fun lcd(fraction: Fraction): Int {
        val factors1 = primeFactors(denominator).groupingBy { it }.eachCount()
        val factors2 = primeFactors(fraction.denominator).groupingBy { it }.eachCount()

        val allPrimes = factors1.keys.union(factors2.keys)
        var lcd = 1

        for (prime in allPrimes) {
            val maxPower = maxOf(factors1.getOrDefault(prime, 0), factors2.getOrDefault(prime, 0))
            lcd *= prime.toDouble().pow(maxPower).toInt()
        }

        return lcd
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
        val commonFactors = intersectWithDups(numeratorFactors, denominatorFactors)

        commonFactors.forEach {
            numeratorFactors.remove(it)
            denominatorFactors.remove(it)
        }

        numerator = numeratorFactors.fold(1) { sum, n -> sum * n }
        denominator = denominatorFactors.fold(1) { sum, n -> sum * n }
    }

    private fun intersectWithDups(list1: List<Int>, list2: List<Int>): List<Int> {
        val commonElements = mutableListOf<Int>()
        val list2Mutable = list2.toMutableList()

        for (element in list1) {
            if (list2Mutable.contains(element)) {
                commonElements.add(element)
                list2Mutable.remove(element)
            }
        }
        return commonElements
    }

    override fun toString(): String {
        var res: String = if (sign < 0) "-" else ""
        res += "$numerator/$denominator"
        return res
    }

    operator fun unaryMinus(): Fraction {
        return Fraction(numerator, denominator, -sign)
    }

    operator fun unaryPlus(): Fraction {
        return this
    }

    operator fun plus(fraction: Fraction): Fraction {
        return add(fraction)
    }

    operator fun minus(fraction: Fraction): Fraction {
        return add(-fraction)
    }

    operator fun times(fraction: Fraction): Fraction {
        return mult(fraction)
    }

    operator fun compareTo(fraction: Fraction): Int {
        if (sign < fraction.sign) {
            return -1
        } else if (sign > fraction.sign) {
            return 1
        } else {
            val lcd = lcd(fraction)
            val num1 = numerator * lcd / denominator
            val num2 = fraction.numerator * lcd / fraction.denominator
            return num1 - num2
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Fraction) {
            return false
        }

        return compareTo(other) == 0
    }
}