package neetcode.dynamic1d

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

object HouseRobber {
    fun rob(nums: IntArray): Int {
        var rob1 = 0
        var rob2 = 0
        nums.forEach { n ->
            val curMax = maxOf(n + rob1, rob2)
            rob1 = rob2
            rob2 = curMax
        }
        return rob2
    }
}

class HouseRobberTest {

    @Test
    fun emptyArray_returnsZero() {
        assertEquals(0, HouseRobber.rob(intArrayOf()))
    }

    @Test
    fun singleHouse_returnsThatValue() {
        assertEquals(1, HouseRobber.rob(intArrayOf(1)))
        assertEquals(7, HouseRobber.rob(intArrayOf(7)))
    }

    @Test
    fun twoHouses_picksMax() {
        assertEquals(3, HouseRobber.rob(intArrayOf(2, 3)))
        assertEquals(9, HouseRobber.rob(intArrayOf(9, 1)))
    }

    @Test
    fun typicalExamples() {
        assertEquals(4, HouseRobber.rob(intArrayOf(1, 2, 3, 1)))          // 1 + 3
        assertEquals(12, HouseRobber.rob(intArrayOf(2, 7, 9, 3, 1)))      // 2 + 9 + 1
        assertEquals(4, HouseRobber.rob(intArrayOf(2, 1, 1, 2)))          // 2 + 2
    }

    @Test
    fun largeValues_noOverflowWithinInt() {
        // Sum stays within 32-bit signed int
        assertEquals(2000000000, HouseRobber.rob(intArrayOf(1_000_000_000, 1, 1_000_000_000)))
        assertEquals(1000000000, HouseRobber.rob(intArrayOf(1_000_000_000)))
    }
}
