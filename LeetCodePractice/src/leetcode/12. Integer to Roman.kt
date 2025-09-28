package leetcode
private val numToRomanMap = mapOf(
    1000 to "M",
    900 to "CM",
    500 to "D",
    400 to "CD",
    100 to "C",
    90 to "XC",
    50 to "L",
    40 to "XL",
    10 to "X",
    9 to "IX",
    5 to "V",
    4 to "IV",
    1 to "I",
)

fun intToRoman(num: Int): String {
    val roman = StringBuilder()
    var n = num
    numToRomanMap.forEach { entry ->
        if (n == 0) return roman.toString()
        repeat(n / entry.key) { roman.append(entry.value) }
        n %= entry.key
    }
    return roman.toString()
}

fun main() {
    println(intToRoman(3749)) // "MMMDCCXLIX"
    println(intToRoman(58))  //"LVIII"
}