package neetcode.arrayandhashing


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class SolutionHashSetLCS {
    fun longestConsecutive(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        var lcs = 0
        val set = nums.toSet()
        set.forEach { x ->
            if (x - 1 !in set) {
                var cur = x + 1
                while (cur in set) cur++
                lcs = maxOf(lcs, cur - x)
            }
        }
        return lcs
    }
}

class SolutionTest {
    private val sol = SolutionHashSetLCS()

    @Test
    fun example1() {
        assertEquals(4, sol.longestConsecutive(intArrayOf(100, 4, 200, 1, 3, 2)))
    }

    @Test
    fun example2() {
        assertEquals(9, sol.longestConsecutive(intArrayOf(0, 3, 7, 2, 5, 8, 4, 6, 0, 1)))
    }

    @Test
    fun empty() {
        assertEquals(0, sol.longestConsecutive(intArrayOf()))
    }

    @Test
    fun single() {
        assertEquals(1, sol.longestConsecutive(intArrayOf(42)))
    }

    @Test
    fun duplicates() {
        assertEquals(3, sol.longestConsecutive(intArrayOf(1, 2, 2, 3)))
    }

    @Test
    fun negatives() {
        assertEquals(4, sol.longestConsecutive(intArrayOf(-2, -1, 0, 1, 10)))
    }
}
