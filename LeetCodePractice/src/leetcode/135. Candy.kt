package leetcode

import kotlin.math.max

fun candy(ratings: IntArray): Int {
    val candy = IntArray(ratings.size) { 1 }
    for (i in ratings.indices.first + 1..ratings.indices.last) {
        if (ratings[i] > ratings[i - 1]) {
            candy[i] = candy[i - 1] + 1
        }
    }
    for (i in ratings.indices.last - 1 downTo ratings.indices.first) {
        if (ratings[i] > ratings[i + 1]) {
            candy[i] = max(candy[i], candy[i + 1] + 1)
        }
    }
    return candy.sum()
}

fun main() {
    println(candy(intArrayOf(1, 0, 2)))
    println(candy(intArrayOf(1, 2, 2)))
}