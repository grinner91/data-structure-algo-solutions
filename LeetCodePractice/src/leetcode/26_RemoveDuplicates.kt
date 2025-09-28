package leetcode

fun removeDuplicates(nums: IntArray): Int {
    var count = 1
    for (i in 1 until nums.size) {
        if (nums[i-1] != nums[i]) {
            nums[count] = nums[i]
            count += 1
        }
    }
   return count
}

fun testRemoveDuplicates() {
    println(removeDuplicates(intArrayOf(0,0,1,1,1,2,2,3,3,4)))
}