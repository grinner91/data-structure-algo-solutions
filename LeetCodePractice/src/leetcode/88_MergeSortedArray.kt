package leetcode//https://leetcode.com/problems/merge-sorted-array/description/?envType=study-plan-v2&envId=top-interview-150

fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
    var r1 = m - 1
    var r2 = n - 1
    var right = m + n - 1

    while (r2 >= 0) {
        if (r1 < 0 || nums1[r1] < nums2[r2]) {
            nums1[right] = nums2[r2]
            r2 -= 1
            right -= 1
        }
        else {
            nums1[right] = nums1[r1]
            r1 -= 1
            right -= 1
        }
    }

}


fun testMergeSortedArray() {
    merge(intArrayOf(1, 2, 3, 0, 0, 0), 3, intArrayOf(2, 5, 6), 3)
}