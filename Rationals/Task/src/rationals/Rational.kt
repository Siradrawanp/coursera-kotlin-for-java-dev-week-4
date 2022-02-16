package rationals

import java.lang.IllegalArgumentException
import java.math.BigInteger

class Rational (private val numerator: BigInteger, private val denominator: BigInteger) : Comparable<Rational> {

    init {
        if (denominator == BigInteger.ZERO) {
            throw IllegalArgumentException()
        }
    }

    operator fun plus(r: Rational): Rational {
        val n = numerator * r.denominator + r.numerator * denominator
        val d = denominator * r.denominator
        return n divBy d
    }

    operator fun minus(r: Rational): Rational {
        val n = numerator * r.denominator - r.numerator * denominator
        val d = denominator * r.denominator
        return  n divBy d
    }

    operator fun times(r: Rational): Rational {
        val n = numerator * r.numerator
        val d = denominator * r.denominator
        return n divBy d
    }

    operator fun div(r: Rational): Rational {
        val n = numerator * r.denominator
        val d = denominator * r.numerator
        return n divBy d
    }

    operator fun unaryMinus(): Rational = Rational(numerator.negate(), denominator)
    override fun compareTo(r: Rational): Int {
        val a = this.numerator * r.denominator divBy this.denominator * r.denominator
        val b = r.numerator * this.denominator divBy r.denominator * this.denominator
        return (a.numerator).compareTo(b.numerator)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rational) return false

        val a = this.normalize()
        val b = other.normalize()

        return a.numerator == b.numerator
    }

    override fun toString(): String {
        return if (denominator == BigInteger.ONE || numerator % denominator == BigInteger.ZERO) {
            (numerator / denominator).toString()
        } else {
            val r = normalize()
            return "${r.numerator}/${r.denominator}"
        }
    }

    private fun normalize(): Rational {
        val gcd = numerator.gcd(denominator)
        return if (denominator < BigInteger.ZERO) {
            Rational(-numerator / gcd, -denominator / gcd)
        } else {
            Rational(numerator / gcd, denominator / gcd)
        }
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }
}
infix fun BigInteger.divBy(other: BigInteger): Rational = Rational(this, other)
infix fun Int.divBy(other: Int): Rational = Rational(this.toBigInteger(), other.toBigInteger())
infix fun Long.divBy(other: Long): Rational = Rational(this.toBigInteger(), other.toBigInteger())

fun String.toRational(): Rational {
    val number = split("/")
    return when (number.size) {
        1 -> Rational(number[0].toBigInteger(), 1.toBigInteger())
        2 -> Rational(number[0].toBigInteger(), number[1].toBigInteger())
        else -> throw IllegalArgumentException()
    }
}


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}