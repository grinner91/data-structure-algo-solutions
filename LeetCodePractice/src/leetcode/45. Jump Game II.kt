package leetcode

fun jump(nums: IntArray): Int {
    if(nums.size <= 1) return 0
    var jumpCount = 0
    var furthest = 0
    var currentEnd = 0
    for (i in 0..nums.size - 1) {
        furthest = maxOf(furthest, i + nums[i])
        if (i == currentEnd) {
            jumpCount++
            currentEnd = furthest
            if (currentEnd >= nums.size - 1) break
        }
    }
    return jumpCount
}

fun main() {
    println(jump(intArrayOf(2, 3, 1, 1, 4))) //2
    println(jump(intArrayOf(2, 3, 0, 1, 4))) //2
}