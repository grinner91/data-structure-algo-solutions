package leetcode

import java.util.*

class RandomizedSet() {
    private val list = ArrayList<Int>()
    private val map = HashMap<Int, Int>()
    private val random = Random(0)

    fun insert(x: Int): Boolean {
        if (map.containsKey(x)) return false
        map[x] = list.size
        list.add(x)
        return true
    }

    fun remove(x: Int): Boolean {
        if (!map.containsKey(x)) return false
        map[x]?.let {
            list[it] = list.last()
            map[list.last()] = it
            list.removeLast()
            map.remove(x)
        }
        return true
    }

    fun getRandom(): Int {
        return list[random.nextInt(list.size)]
    }
}

fun main() {
    val randomizedSet = RandomizedSet()
    println(randomizedSet.insert(1))
    println(randomizedSet.remove(2))
    println(randomizedSet.insert(2))
    println(randomizedSet.getRandom())
    println(randomizedSet.remove(1))
    println(randomizedSet.insert(2))
    println(randomizedSet.getRandom())
}
