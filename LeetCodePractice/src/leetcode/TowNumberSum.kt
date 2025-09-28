package leetcode

public class TowNumberSum {

     fun twoNumberSum(array: MutableList<Int>, targetSum: Int): List<Int> {
        val map = mutableMapOf<Int, Boolean>()
        for (n in array) {
            val x = targetSum - n
            if (map.containsKey(x)) {
                return listOf<Int>(x, n)
            } else {
                map[x] = true
            }
        }

        return listOf<Int>()
    }

    fun test() {
        var res = twoNumberSum(mutableListOf(3, 5, -4, 8, 11, 1, -1, 6), 10)
        print(res)
    }
}