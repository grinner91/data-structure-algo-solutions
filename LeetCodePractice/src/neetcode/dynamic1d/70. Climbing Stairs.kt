package neetcode.dynamic1d

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ClimbingStairsDP {
    fun climbStairs(n: Int): Int {
        //recurrence f(n) = f(n-1) + f(n-2)
        //n = 0, f(0) = 1, valid one way, do nothing, on the ground
        //n = 1, f(1) = 1, one way, single step
        var one = 1 // f(0) one way, do nothing, on the ground
        var two = 1 // f(1) one way to stair up, single
        for (i in 0..(n - 2)) {
            var temp = one
            one += two
            two = temp
        }
        return one
    }
}

class ClimbingStairsDP1 {
    fun climbStairs(n: Int): Int {
        //recurrence f(n) = f(n-1) + f(n-2)
        //n = 0, f(0) = 1, valid one way, do nothing, on the ground
        //n = 1, f(1) = 1, one way, single step
        if (n <= 2) return n
        val dp = IntArray(n + 1)
        dp[1] = 1
        dp[2] = 1
        for (i in 3..n) {
            dp[i] = dp[i - 1] + dp[i - 2]
        }
        return dp[n]
    }
}

class ClimbingStairsTest {

    private val sut = ClimbingStairsDP() // System Under Test

    @Test
    fun testSmallKnownValues() {
        assertEquals(1, sut.climbStairs(0))
        assertEquals(1, sut.climbStairs(1))
        assertEquals(2, sut.climbStairs(2))
        assertEquals(3, sut.climbStairs(3))
        assertEquals(5, sut.climbStairs(4))
        assertEquals(8, sut.climbStairs(5))
    }

    @Test
    fun testLargerValues() {
        assertEquals(13, sut.climbStairs(6))
        assertEquals(21, sut.climbStairs(7))
        assertEquals(55, sut.climbStairs(9))
        assertEquals(1836311903, sut.climbStairs(45)) // LeetCode constraint max n=45
    }

    @Test
    fun testFibonacciProperty() {
        // Verify f(n) = f(n-1) + f(n-2)
        for (n in 2..10) {
            val fn = sut.climbStairs(n)
            val fn1 = sut.climbStairs(n - 1)
            val fn2 = sut.climbStairs(n - 2)
            assertEquals(fn1 + fn2, fn, "Should follow Fibonacci relation for n=$n")
        }
    }
}
