package neetcode.arrayandhashing

//fun isAnagram(s: String, t: String): Boolean {
//    if(s.length != t.length) return false
//    val sMap = s.groupingBy { it }.eachCount()
//    val tMap = t.groupingBy { it }.eachCount()
//    return sMap == tMap
//}

//fun containsDuplicate1(nums: IntArray): Boolean {
//   return nums.toSet().size < nums.size
//}

//
//fun twoSum(nums: IntArray, target: Int): IntArray {
//    for (i in nums.indices) {
//        for (j in i + 1 until nums.size) {
//            if (nums[i] + nums[j] == target)
//                return intArrayOf(i, j)
//        }
//    }
//    return intArrayOf()
//}

//fun twoSum(nums: IntArray, target: Int): IntArray {
//    val map = HashMap<Int, Int>()
//    nums.forEachIndexed { i, n ->
//        map[target - n]?.let { j ->
//            return intArrayOf(j, i)
//        }
//        map[n] = i
//    }
//    return intArrayOf()
//}

//O(m) - m length of max String
fun groupAnagrams7(strs: Array<String>): List<List<String>> {
    return strs.groupBy { word ->
        val freq = IntArray(26)
        word.forEach { freq[it - 'a']++ }
        freq.joinToString("#")
    }.values.toList()
}

fun groupAnagrams8(strs: Array<String>): List<List<String>> {
    return strs.groupBy { word ->
        word.toCharArray().sorted().joinToString("")
    }.values.toList()
}
