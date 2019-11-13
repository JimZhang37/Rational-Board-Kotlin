package rationals

import java.math.BigInteger
import kotlin.math.absoluteValue


class Rational(var numerator: Int, var denominator: Int): Comparable<Rational> {


    init {
        simplify(numerator, denominator)

    }


    private fun simplify(v1: Int, v2: Int) {

        val i = if (v1 > v2) v2 else v1


        for (j: Int in i.absoluteValue downTo 1 step 1) {
            if (this.numerator % j == 0 && this.denominator % j == 0) {

                this.numerator /= j
                this.denominator /= j
                break
            }
        }


    }


    override fun equals(other: Any?): Boolean {

        if (other is Rational) {
            if (this.numerator == other.numerator && this.denominator == other.denominator)
                return true
        }
        return false
    }


    override fun toString(): String {
        if (this.denominator != 1) return this.numerator.toString() + '/' + this.denominator.toString()

        return this.numerator.toString()
    }

    operator fun times(a: Rational): Rational {
        return Rational(this.numerator * a.numerator, this.denominator * a.denominator)
    }

    /***
     *
     */
    operator fun plus(a: Rational): Rational {

        return Rational(this.numerator * a.denominator + this.denominator * a.numerator, this.denominator * a.denominator)
    }

    /***
     * minus is used to support -
     */
    operator fun minus(a: Rational): Rational {
        return Rational(this.numerator * a.denominator - this.denominator * a.numerator, this.denominator * a.denominator)
    }

    /***
     * div is used to support /
     */
    operator fun div(a: Rational): Rational {
        return Rational(this.numerator * a.denominator, this.denominator * a.numerator)
    }

    /***
     * unaryMinus is used to support -x, x-
     */
    operator fun unaryMinus(): Rational {
        return Rational(-this.numerator, this.denominator)
    }

    /***
     *
     */
    operator fun iterator(): Rational {
        return Rational(1, 1)
    }

    /***
     * compareTo is used to support >, <, >=, <=
     */
    override operator fun compareTo(other: Rational): Int {
        var i = 0
        when  {
            (this - other).numerator > 0 -> i = 1
            (this - other).numerator == 0 -> i = 0
            (this - other).numerator < 0 -> i = -1
        }
        return i
    }



    /***
     * rangeTo is used to support ..
     */
    operator fun rangeTo(a: Rational): RationalRange {

        return RationalRange(this, a)
    }


}

class RationalRange(private val start: Rational, private val end : Rational){
    /***
     * contains is used to support in
     */
    operator fun contains(a: Rational): Boolean {
        if(a >= start && a <= end) return true
        return false
    }
}

fun String.toRational(): Rational {
    val stringArray = this.split("/")
    if (stringArray[0].length > 18 || stringArray[1].length > 18){
        println("big")
        val a = stringArray[0].toBigInteger() divBy  stringArray[1].toBigInteger()
        println(a)
        println("a finished")
        return a
    }
    else if(stringArray[0].length > 9 || stringArray[1].length > 9){
        println("long")
        return stringArray[0].toLong() divBy  stringArray[1].toLong()
    }
    println("int")
    return stringArray[0].toInt() divBy  stringArray[1].toInt()
}

infix fun Int.divBy(b: Int): Rational {
    val a: Rational = Rational(this, b)
    return a
}

infix fun Long.divBy(b: Long): Rational {
    val i = if (this.absoluteValue > b.absoluteValue) b.absoluteValue else this.absoluteValue

    var a = 0
    var c = 1
    for (j: Long in i.absoluteValue downTo 1 step 1) {
        if (this % j == 0L && b % j == 0L) {

            a = (this / j).toInt()
            c = (b / j).toInt()
            break
        }
    }
    if (this * b > 0) return Rational(a, c) else return Rational(-a, c)


}



infix fun BigInteger.divBy(b: BigInteger): Rational {

    var i = if (this.abs() > b.abs()) b.abs() else this.abs()

    var a = 0
    var c = 1
    while (i > 1.toBigInteger()) {
        if (this % i == 0.toBigInteger() && b % i == 0.toBigInteger()) {

            a = (this / i).toInt()
            c = (b / i).toInt()
            break
        }
        i --
    }
    println("rational is the first int: $a, the second int: $c")
    if (this * b > 0.toBigInteger()) return Rational(a, c) else return Rational(-a, c)

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

//    println("abc")
//    println("20395802948019459839003802001190283020/32493205934869548609023910932454365628".toRational() in half..twoThirds)
//    println("efg")
}