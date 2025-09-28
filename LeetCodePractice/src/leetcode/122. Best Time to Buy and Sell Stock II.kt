package leetcode

fun maxProfit2(prices: IntArray): Int {
    var totalProfit = 0
    for(i in 1 until prices.size) {
        if(prices[i] > prices[i-1]) {
            totalProfit += prices[i] - prices[i-1]
        }
    }
   return totalProfit
}

fun main() {
    println(maxProfit2(intArrayOf(7,1,5,3,6,4)))
    println(maxProfit2(intArrayOf(7,6,4,3,1)))
}