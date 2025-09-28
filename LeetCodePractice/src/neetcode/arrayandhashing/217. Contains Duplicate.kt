package neetcode.arrayandhashing

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun containsDuplicate(nums: IntArray): Boolean {
    val seen = HashSet<Int>()
    nums.forEach { n ->
        if (seen.contains(n)) return true
        else seen.add(n)
    }
    return false
}

class ContainsDuplicateTests {
    @Test
    fun `should return true with duplicates`() {
        assertTrue(containsDuplicate(intArrayOf(1,2,3,1)))
    }
}

