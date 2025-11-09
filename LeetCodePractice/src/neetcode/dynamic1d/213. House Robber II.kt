package neetcode.dynamic1d

import org.junit.Assert.assertEquals
import org.junit.Test

//3. Dynamic Programming (Bottom-Up)
class HouseRobberII_DP_BottomUP {
    fun rob(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        if (nums.size == 1) return nums[0]
        if (nums.size == 2) return maxOf(nums[0], nums[1])

        return maxOf(
            robDPBottomUp(nums.copyOfRange(0, nums.size - 1)),
            robDPBottomUp(nums.copyOfRange(1, nums.size))
        )
    }

    private fun robDPBottomUp(nums: IntArray): Int {
        val dp = IntArray(nums.size)
        dp[0] = nums[0]
        dp[1] = maxOf(nums[0], nums[1])

        for (i in 2 until nums.size) {
            dp[i] = maxOf(nums[i] + dp[i - 2], dp[i - 1])
        }
        return dp[nums.size - 1]
    }
}

//DFS  Dynamic Programming (Top-Down)
class HouseRobberII_DP_TopDown {
    fun rob(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        if (nums.size == 1) return nums[0]
        if (nums.size == 2) return maxOf(nums[0], nums[1])
        return maxOf(
            robTopDownDP(nums.copyOfRange(0, nums.size - 1)),
            robTopDownDP(nums.copyOfRange(1, nums.size))
        )
    }

    private fun robTopDownDP(nums: IntArray): Int {
        val memo = mutableMapOf<Int, Int>()
        fun dfs(i: Int): Int {
            if (i >= nums.size) return 0
            if (i in memo) return memo[i]!!

            val robCur = nums[i] + dfs(i + 2)
            val skipCur = dfs(i + 1)

            memo[i] = maxOf(robCur, skipCur)
            return memo[i]!!
        }
        dfs(0)
        return memo[0]!!
    }
}

class Solution213Test {
    private val s = HouseRobberII_DP_TopDown()//HouseRobberII_DP_BottomUP()

    @Test
    fun examplesAndBasics() {
        assertEquals(3, s.rob(intArrayOf(2, 3, 2)))
        assertEquals(4, s.rob(intArrayOf(1, 2, 3, 1)))
        assertEquals(3, s.rob(intArrayOf(1, 2, 3)))
    }

    @Test
    fun varied() {
        assertEquals(11, s.rob(intArrayOf(2, 7, 9, 3, 1)))
        assertEquals(340, s.rob(intArrayOf(200, 3, 140, 20, 10)))
        assertEquals(103, s.rob(intArrayOf(1, 3, 1, 3, 100)))
    }

    @Test
    fun largeSameValues() {
        val arr = IntArray(1000) { 1 }
        assertEquals(500, s.rob(arr))
    }
}
