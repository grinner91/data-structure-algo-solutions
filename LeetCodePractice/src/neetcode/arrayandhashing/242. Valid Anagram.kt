package neetcode.arrayandhashing

import org.junit.jupiter.api.Test

fun isAnagram_sort(s: String, t: String): Boolean {
    if(s.length != t.length) return  false
    return s.toCharArray().sorted() == t.toCharArray().sorted()
}

fun isAnagramMap(s: String, t: String): Boolean {
    if(s.length != t.length) return  false
    return s.groupingBy { it }.eachCount() == t.groupingBy { it }.eachCount()
}

//s.toCharArray().sort() == t.toCharArray().sort()

class AnagramTests {
    @Test
    fun `should return true when given angrams`() {
        isAnagramMap("racecar", "carrace")
    }

    @Test
    fun `should return false`() {
        isAnagramMap("jar", "jam")
    }
}