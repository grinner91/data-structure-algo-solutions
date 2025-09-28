package leetcode

fun maxProfit(prices: IntArray): Int {
    var lowest = prices[0]
    var maxProfit = 0
    prices.forEach { p ->
        lowest = if (p < lowest) p else lowest
        maxProfit = maxOf(maxProfit, p - lowest)
    }
    return maxProfit
}

fun main() {
    println(maxProfit(intArrayOf(7,1,5,3,6,4)))
    println(maxProfit(intArrayOf(7,6,4,3,1)))
}