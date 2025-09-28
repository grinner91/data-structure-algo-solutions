package leetcode

fun lengthOfLastWord(s: String): Int {
    return s.split(' ').last { it.isNotEmpty() }.length
}

fun main() {
    println(lengthOfLastWord("Hello World"))//4
    println(lengthOfLastWord("   fly me   to   the moon  "))//4
}