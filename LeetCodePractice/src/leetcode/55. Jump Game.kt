package leetcode

enum class PositionType { GOOD, BAD, UNKNOWN }
var memo: Array<PositionType> = arrayOf()

fun canJumpFromPosition(currentPosition: Int, nums: IntArray): Boolean {
    if (memo[currentPosition] != PositionType.UNKNOWN) {
        return memo[currentPosition] == PositionType.GOOD
    }

    val maxJump = minOf(currentPosition + nums[currentPosition], nums.size - 1)
    for (nextPosition in maxJump downTo currentPosition + 1) {
        if (canJumpFromPosition(nextPosition, nums)) {
            memo[currentPosition] = PositionType.GOOD
            return true
        }
    }
    memo[currentPosition] = PositionType.BAD
    return false
}

fun canJump(nums: IntArray): Boolean {
    memo = Array(nums.size) { PositionType.UNKNOWN }
    memo[nums.size - 1] = PositionType.GOOD
    return canJumpFromPosition(0, nums)
}


fun main() {
    println(canJump(intArrayOf(2, 3, 1, 1, 4)))
    println(canJump(intArrayOf(3,2,1,0,4)))
}