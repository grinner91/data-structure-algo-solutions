package neetcode.arrayandhashing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//
//fun groupAnagrams(strs: Array<String>): List<List<String>> {
//    return strs.groupBy { word -> // O(n)
//        val freq = IntArray(26)
//        word.forEach { ch -> freq[ch - 'a']++ } //O(m)
//        freq.joinToString("#")
//    }.values.toList()
//}

fun groupAnagrams(strs: Array<String>): List<List<String>> {
    return strs.groupBy { word ->
        word.toCharArray().sorted().joinToString("")
    }.values.toList()
}

class GroupAnagrams {
    @Test
    fun `should return correct list`() {
        val strs = arrayOf("eat", "tea", "tan", "ate", "nat", "bat")
        val expected = listOf(
            listOf("bat"),
            listOf("nat", "tan"),
            listOf("ate", "eat", "tea")
        )
        val actual = groupAnagrams(strs)
        assertEquals(
            expected.map { it.toSet() }.toSet(),
            actual.map { it.toSet() }.toSet()
        )
    }
}