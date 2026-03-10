package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LRUCacheBruteForce(capacity: Int) {
    private val cache = mutableListOf<Pair<Int, Int>>()
    private val cap = capacity

    fun get(key: Int): Int {
        val i = cache.indexOfFirst { it.first == key }
        if (i == -1) return -1
        val entry = cache.removeAt(i)
        //append to end of the list
        // most recently used item
        cache.add(entry)
        return entry.second
    }

    fun put(key: Int, value: Int) {
        val i = cache.indexOfFirst { it.first == key }
        if (i != -1) cache.removeAt(i)
        if (cache.size == cap)
            cache.removeAt(0)
        cache.add(key to value)
    }
}

class LRUCacheMapAndQue(capacity: Int) {
    private val cap = capacity
    private val map = HashMap<Int, Int>()
    private val que = ArrayDeque<Int>()

    fun get(key: Int): Int {
        val v = map[key] ?: return -1
        que.remove(key)
        que.addLast(key)
        return v
    }

    fun put(key: Int, value: Int) {
        if (map.containsKey(key)) {
            que.remove(key)
        } else if (map.size == cap) {
            val lru = que.removeFirst()
            map.remove(lru)
        }
        map[key] = value
        que.addLast(key)
    }
}


class LRUCacheLinkedList(capacity: Int) {
    data class LruNode(val key: Int, var value: Int) {
        var next: LruNode? = null
        var prev: LruNode? = null
    }

    private val cap = capacity
    private val cache = HashMap<Int, LruNode>()
    private var head: LruNode? = LruNode(0, 0) //LRU
    private var tail: LruNode? = LruNode(0, 0) //MRU

    init {
        head?.next = tail
        tail?.prev = head
    }

    fun get(key: Int): Int {
        val cur = cache[key]
        cur ?: return -1

        removeNode(cur)
        addNodeLast(cur)

        return cur.value
    }
   //head <-> least recently used ... most recently used <-> tail
    fun put(key: Int, value: Int) {
        cache[key]?.let { cur ->
            cur.value = value
            removeNode(cur)
            addNodeLast(cur)
            return
        }

        val node = LruNode(key, value)
        cache[key] = node
        addNodeLast(node)

        if (cache.size > cap) {
            val first = head?.next!!
            cache.remove(first.key)
            removeNode(first)
        }
    }

    private fun removeNode(node: LruNode) {
        val prev = node.prev
        val next = node.next
        prev?.next = next
        next?.prev = prev
    }

    private fun addNodeLast(node: LruNode) {
        val prevLast = tail?.prev
        prevLast?.next = node
        node.prev = prevLast
        node.next = tail
        tail?.prev = node
    }

}

class LRUCacheTest {

    private val impls = listOf<(Int) -> Any>(
        { LRUCacheLinkedList(it) },
    )

    @Test
    fun example1() {
        impls.forEach { factory ->
            val cache = factory(2)

            when (cache) {
                is LRUCacheLinkedList -> {
                    cache.put(1, 1)
                    cache.put(2, 2)
                    assertEquals(1, cache.get(1))
                    cache.put(3, 3)
                    assertEquals(-1, cache.get(2))
                    cache.put(4, 4)
                    assertEquals(-1, cache.get(1))
                    assertEquals(3, cache.get(3))
                    assertEquals(4, cache.get(4))
                }
            }
        }
    }
}