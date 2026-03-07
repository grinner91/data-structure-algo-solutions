package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReorderListBruteForce {
    fun reorderList(head: ListNode?): Unit {
        val nodes = mutableListOf<ListNode>()
        var cur = head
        while (cur != null) {
            nodes.add(cur)
            cur = cur.next
        }
        var l = 0
        var r = nodes.lastIndex
        while (l < r) {
            nodes[l].next = nodes[r]
            l++
            if (l > r) break
            nodes[r].next = nodes[l]
            r--
        }
         nodes[l].next = null
    }
}

class ReorderListTest {

    private val impls: List<(ListNode?) -> Unit> = listOf(
//        SolutionReverseMerge()::reorderList,
//        SolutionStack()::reorderList,
        ReorderListBruteForce()::reorderList,
    )

    @Test
    fun emptyList() {
        impls.forEach { f ->
            val head: ListNode? = null
            f(head)
            assertEquals(emptyList<Int>(), head.toListSafe())
        }
    }

    @Test
    fun singleNode() {
        impls.forEach { f ->
            val head = listOf(1).toListNode()
            f(head)
            assertEquals(listOf(1), head.toListSafe())
        }
    }

    @Test
    fun twoNodes() {
        impls.forEach { f ->
            val head = listOf(1, 2).toListNode()
            f(head)
            assertEquals(listOf(1, 2), head.toListSafe())
        }
    }

    @Test
    fun oddLength() {
        impls.forEach { f ->
            val head = listOf(1, 2, 3, 4, 5).toListNode()
            f(head)
            assertEquals(listOf(1, 5, 2, 4, 3), head.toListSafe())
        }
    }

    @Test
    fun evenLength() {
        impls.forEach { f ->
            val head = listOf(1, 2, 3, 4).toListNode()
            f(head)
            assertEquals(listOf(1, 4, 2, 3), head.toListSafe())
        }
    }

    @Test
    fun duplicates() {
        impls.forEach { f ->
            val head = listOf(1, 1, 1, 1, 1, 1).toListNode()
            f(head)
            assertEquals(listOf(1, 1, 1, 1, 1, 1), head.toListSafe())
        }
    }

    // ---------- Helpers (test-only) ----------

    private fun List<Int>.toListNode(): ListNode? {
        if (isEmpty()) return null
        val dummy = ListNode(0)
        var cur = dummy
        for (v in this) {
            cur.next = ListNode(v)
            cur = cur.next!!
        }
        return dummy.next
    }

    private fun ListNode?.toListSafe(limit: Int = 10_000): List<Int> {
        // Safety guard against cycles in buggy implementations.
        val out = ArrayList<Int>()
        var cur = this
        var steps = 0
        while (cur != null) {
            out.add(cur.`val`)
            cur = cur.next
            steps++
            if (steps > limit) error("Possible cycle detected (exceeded $limit steps)")
        }
        return out
    }
}