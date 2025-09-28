package neetcode.twopointer

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
//two pointer
//fun twoSum(numbers: IntArray, target: Int): IntArray {
//    var left = 0
//    var right = numbers.lastIndex
//    while (left < right) {
//        val currentSum = numbers[left] + numbers[right]
//        when {
//            currentSum == target -> return intArrayOf(left + 1, right + 1)
//            currentSum < target -> left++
//            else -> right--
//        }
//    }
//    return intArrayOf()
//}

//
//binary search
fun twoSum(numbers: IntArray, target: Int): IntArray {
   for (i in numbers.indices) {
       var left = i + 1
       var right = numbers.lastIndex
       while (left <= right) {
           val mid = left + (right - left) / 2
           val currentSum = numbers[i] + numbers[mid]
           when {
               currentSum == target -> return intArrayOf(i + 1, mid + 1)
               currentSum < target -> left = mid + 1
               else -> right = mid - 1
           }
       }
   }
    return intArrayOf()
}

class TwoSumTests1 {
    @Test
    fun `should return correct indices`() {
        val input = intArrayOf(2, 7, 11, 15)
        val expected = intArrayOf(1, 2)
        val actual = twoSum(input, 9)
        assertTrue(
            expected.contentEquals(actual)
        )
    }
}
