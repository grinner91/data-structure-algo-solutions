package leetcode

fun canJump3(nums: IntArray): Boolean {
    var targetPosition = nums.size - 1
    for (i in nums.size - 2 downTo 0) {
        if (i + nums[i] >= targetPosition) {
            targetPosition = i
        }
    }
    return targetPosition == 0
}

enum class STATUS { REACHABLE, NOT_REACHABLE, UNKNOWN }

fun canJump2(nums: IntArray): Boolean {
    val memo = Array(nums.size) { STATUS.UNKNOWN }
    memo[nums.size - 1] = STATUS.REACHABLE
    for (current in nums.size - 2 downTo 0) {
        val maxJump = minOf(current + nums[current], nums.size - 1)
        for (next in maxJump downTo current + 1) {
            if (memo[next] == STATUS.REACHABLE) {
                memo[current] = STATUS.REACHABLE
                break
            }
        }
    }
    return memo[0] == STATUS.REACHABLE
}

fun main() {
    println(canJump2(intArrayOf(2, 3, 1, 1, 4)))
    println(canJump2(intArrayOf(3, 2, 1, 0, 4)))
}