package leetcode

fun rotate1(nums: IntArray, k: Int): Unit {
    val arr = IntArray(nums.size)
    nums.indices.forEach { i ->
        arr[(i + k) % nums.size] = nums[i]
    }
    arr.indices.forEach { i ->
        nums[i] = arr[i]
    }
}

fun rotate2(nums: IntArray, k: Int): Unit {
    fun reverse(arr: IntArray, start: Int, end: Int) {
        var left = start
        var right = end
        while (left < right) {
            val temp = arr[left]
            arr[left] = arr[right]
            arr[right] = temp
            left++
            right--
        }
    }

    val shift = k % nums.size
    reverse(nums, nums.indices.first, nums.indices.last)
    reverse(nums, nums.indices.first, shift - 1)
    reverse(nums, shift, nums.indices.last)
}

fun rotate3(nums: IntArray, k: Int): Unit {
    val shift = k % nums.size
    nums.reverse(nums.indices.first, nums.indices.last)
    nums.reverse(nums.indices.first, shift - 1)
    nums.reverse(shift, nums.indices.last)
    val a = 0
}

private fun IntArray.reverse(from: Int, to: Int) {
    var left = from
    var right = to
    while (left < right) {
        val temp = this[left]
        this[left] = this[right]
        this[right] = temp
        left++
        right--
    }
}

fun main() {
    println("result: " + rotate3(intArrayOf(1, 2), 2))
    println("result: " + rotate3(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3))
}