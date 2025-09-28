package leetcode

fun productExceptSelf(nums: IntArray): IntArray {
    val products = IntArray(nums.size) { 1 }
    var leftProd = 1
    for (i in nums.indices) {
        products[i] = leftProd
        leftProd *= nums[i]
    }
    var rightProd = 1
    for (i in nums.indices.reversed()) {
        products[i] = products[i] * rightProd
        rightProd *= nums[i]
    }
    return products
}

fun main() {
    println(productExceptSelf(intArrayOf(1, 2, 3, 4)).joinToString())
    println(productExceptSelf(intArrayOf(-1, 1, 0, -3, 3)).joinToString())
}