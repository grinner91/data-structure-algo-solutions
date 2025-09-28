package leetcode

fun isAnagram(s: String, t: String): Boolean {
    return s.toList().sorted() == t.toList().sorted()
}