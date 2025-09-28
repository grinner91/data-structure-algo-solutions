package leetcode


    fun containsDuplicate(nums: IntArray): Boolean {
        val seenNums = HashSet<Int>()
        nums.forEach {
            if(seenNums.contains(it)) return true
            else seenNums.add(it)
        }
        return false
    }