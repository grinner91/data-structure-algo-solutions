package neetcode.slidingwindow

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BestTimeToBuyAndSellStockBruteForce {
    fun maxProfit(prices: IntArray): Int {
        val n = prices.size
        var maxPro = 0
        for (i in 0 until n - 1) {
            for (j in i + 1 until n) {
                maxPro = maxOf(prices[j] - prices[i], maxPro)
            }
        }
        return maxPro
    }
}

class BestTimeToBuyAndSellStockTwoPointer {
    fun maxProfit(prices: IntArray): Int {
        val n = prices.size
        var maxProf = 0
        var l = 0
        var r = 1
        while (r < n) {
            when {
                prices[l] < prices[r] -> maxProf = maxOf(prices[r] - prices[l], maxProf)
                else -> l = r
            }
            r++
        }
        return maxProf
    }
}

class BestTimeToBuyAndSellStockDynamicProgLowSoFar {
    fun maxProfit(prices: IntArray): Int {
        var maxProf = 0
        var minBuy = Int.MAX_VALUE
        for (sell in prices) {
            maxProf = maxOf(maxProf, sell - minBuy)
            minBuy = minOf(minBuy, sell)
        }
        return maxProf
    }
}

class BestTimeToBuyAndSellStockTest {

    private val impls = listOf(
        BestTimeToBuyAndSellStockBruteForce()::maxProfit,
        BestTimeToBuyAndSellStockTwoPointer()::maxProfit,
        BestTimeToBuyAndSellStockDynamicProgLowSoFar()::maxProfit,
    )

    @Test
    fun example1() {
        val prices = intArrayOf(7, 1, 5, 3, 6, 4)
        impls.forEach { f ->
            assertEquals(5, f(prices), "Failed for impl: $f")
        }
    }

    @Test
    fun example2_decreasing() {
        val prices = intArrayOf(7, 6, 4, 3, 1)
        impls.forEach { f ->
            assertEquals(0, f(prices), "Failed for impl: $f")
        }
    }

    @Test
    fun singleDay() {
        val prices = intArrayOf(5)
        impls.forEach { f ->
            assertEquals(0, f(prices), "Failed for impl: $f")
        }
    }

    @Test
    fun empty() {
        val prices = intArrayOf()
        impls.forEach { f ->
            assertEquals(0, f(prices), "Failed for impl: $f")
        }
    }

    @Test
    fun increasing() {
        val prices = intArrayOf(1, 2, 3, 4, 5)
        impls.forEach { f ->
            assertEquals(4, f(prices), "Failed for impl: $f")
        }
    }

    @Test
    fun multipleValleysAndPeaks() {
        val prices = intArrayOf(3, 2, 6, 5, 0, 3)
        impls.forEach { f ->
            assertEquals(4, f(prices), "Failed for impl: $f")
        }
    }

    @Test
    fun duplicates() {
        val prices = intArrayOf(2, 2, 2, 2)
        impls.forEach { f ->
            assertEquals(0, f(prices), "Failed for impl: $f")
        }
    }

    @Test
    fun bestSellNotLastDay() {
        val prices = intArrayOf(2, 1, 4, 2, 3, 0)
        impls.forEach { f ->
            assertEquals(3, f(prices), "Failed for impl: $f")
        }
    }
}
