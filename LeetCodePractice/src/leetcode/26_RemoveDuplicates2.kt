package leetcode

fun removeDuplicates2(nums: IntArray): Int {
    if (nums.size <= 2) return nums.size
    var count = 1
    var repeats = if (nums[0] == nums[1]) 1 else 0
    for (i in 1 until nums.size) {
        if (nums[i - 1] != nums[i]) {
            nums[count] = nums[i]
            count += 1
            repeats = 1
        } else if (nums[i - 1] == nums[i] && repeats < 2) {
            nums[count] = nums[i]
            count += 1
            repeats += 1
        } else {
            repeats += 1
        }
    }
    return count
}

fun removeDuplicates2ndSolu(nums: IntArray): Int {
    if (nums.size <= 2) return nums.size
    var idx = 0
    for (n in nums) {
        if (idx < 2 || nums[idx - 2] != n) {
            nums[idx] = n
            idx++
        }
    }
    return idx
}


fun testRemoveDuplicates2() {
    println(removeDuplicates2ndSolu(intArrayOf(0, 0, 1, 1, 1, 1, 2, 3, 3)))
}