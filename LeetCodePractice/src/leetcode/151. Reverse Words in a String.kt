package leetcode

fun reverseWords(s: String): String {
    return s.trim()
        .split(' ')
        .filter { it.isNotEmpty() }
        .reversed()
        .joinToString(" ")
}

fun main() {
    println(reverseWords("the sky is blue")) // "blue is sky the"
    println(reverseWords(" hello world  ")) //"world hello"
}