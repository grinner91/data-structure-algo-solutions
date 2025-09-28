package leetcode

fun removeElement(nums: IntArray, v: Int): Int {
    var k = 0
    for (i in 0 until nums.size) {
        if (nums[i] != v) {
            nums[k] = nums[i]
            k += 1
        }
    }
    return k
}

fun testremoveElement() {

    println("result: ")
    println(removeElement(intArrayOf(3, 2, 2, 3), 3))
    println(removeElement(intArrayOf(0, 1, 2, 2, 3, 0, 4, 2), 2))
}
