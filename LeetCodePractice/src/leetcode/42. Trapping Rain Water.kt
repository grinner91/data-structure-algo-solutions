package leetcode

import kotlin.math.max
import kotlin.math.min

fun trap(height: IntArray): Int {
    if (height.size <= 1) return 0
    val leftMax = IntArray(height.size) { 0 }
    val indices = height.indices
    leftMax[0] = height[0]
    for (i in (indices.first + 1)..indices.last) {
        leftMax[i] = max(leftMax[i - 1], height[i])
    }
    val rightMax = IntArray(height.size) { 0 }
    rightMax[indices.last] = height[indices.last]
    for (i in (indices.last - 1) downTo indices.first) {
        rightMax[i] = max(rightMax[i + 1], height[i])
    }
    var water = 0
    for (i in indices) {
        water += min(leftMax[i], rightMax[i]) - height[i]
    }
    return water
}


fun main() {
    println(trap(intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1))) //6
    println(trap(intArrayOf(4, 2, 0, 3, 2, 5))) //9
}