package leetcode

fun majorityElement1(nums: IntArray): Int {
    val freqMap = mutableMapOf<Int, Int>()
    nums.forEach {
        freqMap[it] = freqMap.getOrDefault(it, 0) + 1
    }
    return freqMap.entries.maxBy { it.value }.key
}

fun majorityElement(nums: IntArray): Int {
    return nums
        .groupBy { it }
        .maxBy { it.value.size }
        .key
}

fun majorityElement2(nums: IntArray): Int {
    var candidate = 0
    var count = 0
    for (num in nums) {
        if (count == 0)
            candidate = num
        if (num == candidate)
            count++
        else
            count--
    }
    return candidate
}

fun testMajorityElement() {

    //println("result: " + majorityElement2(intArrayOf(3, 2, 3)))
    println("result: " + majorityElement2(intArrayOf(2, 2, 1, 3, 1, 2, 4, 3)))
}