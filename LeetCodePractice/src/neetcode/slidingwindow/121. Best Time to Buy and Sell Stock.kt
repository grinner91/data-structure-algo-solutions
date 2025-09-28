package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun maxProfit(prices: IntArray): Int {
    var l = 0
    var r = 1
    var maxProfit = 0
    while (r < prices.size) {
        when {
            prices[l] < prices[r] -> {
                maxProfit = maxOf(maxProfit, prices[r] - prices[l])
            }

            else -> {
                l = r
            }
        }
        r += 1
    }
    return maxProfit
}

class MaxProfitTest() {
    @Test
    fun test1() {
        assertEquals(5, maxProfit(intArrayOf(7, 1, 5, 3, 6, 4)))

    }

    @Test
    fun test2() {
        assertEquals(0, maxProfit(intArrayOf(  7,6,4,3,1)))

    }
}