package leetcode

class AnagramGroups {
    fun groupAnagrams(strs: Array<String>): List<List<String>> =
        strs.groupBy { str ->
            val groupMap = str.groupingBy { it }.eachCount()
            println(groupMap)
            groupMap
        }.values.toList()


    fun test() {

    }
}

/*
*

* fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        strs.forEach {
            val key = it.toCharArray().sorted().joinToString("")
            if (!map.containsKey(key)) map[key] = mutableListOf()
            map[key]?.add(it)
        }

        return map.values.toList()
    }
*
*
*
* */