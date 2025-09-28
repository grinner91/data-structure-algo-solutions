package leetcode

fun romanToInt(s: String): Int {
    var num = 0
    var prev = Int.MAX_VALUE
    s.forEach { roman ->
        val current = romanToNum(roman)
        num += current
        if (current > prev)
            num -= 2 * prev
        prev = current
    }
    return num
}

private fun romanToNum(roman: Char) = when (roman) {
    'I' -> 1
    'V' -> 5
    'X' -> 10
    'L' -> 50
    'C' -> 100
    'D' -> 500
    'M' -> 1000
    else -> 0
}

fun main() {
    println(romanToInt("III")) //3
    println(romanToInt("LVIII")) //58
}