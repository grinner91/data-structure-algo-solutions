package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class MergeKSortedListsFlattenAndSort {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        val nums = mutableListOf<Int>()
        for (i in lists.indices) {
            var cur = lists[i]
            while (cur != null) {
                nums.add(cur.`val`)
                cur = cur.next
            }
        }

        if (nums.isEmpty()) return null

        val head: ListNode = ListNode(0)
        var tail: ListNode? = head
        nums.sorted().map {
            val node = ListNode(it)
            tail?.next = node
            tail = node
        }
        return head.next
    }
}

class MergeKSortedListsMergedList {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        var merged: ListNode? = null
        for (i in lists.indices) {
            merged = mergedTwoLists(merged, lists[i])
        }
        return merged
    }

    private fun mergedTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        var a = l1
        var b = l2
        while (a != null && b != null) {
            if (a.`val` <= b.`val`) {
                tail.next = a
                a = a.next
            } else {
                tail.next = b
                b = b.next
            }
            tail = tail.next!!
        }
        tail.next = a ?: b

        return dummy.next
    }
}

class MergeKSortedListsMinHeap {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        val minHeap = PriorityQueue<ListNode>(compareBy { it.`val` })
        for (node in lists) {
            node?.let { minHeap.offer(it) }
        }

        val dummy = ListNode(-1)
        var tail: ListNode? = dummy
        while (minHeap.isNotEmpty()) {
            val cur = minHeap.poll()

            tail?.next = cur
            tail = tail?.next

            cur.next?.let {
                minHeap.offer(it)
            }
        }
        return dummy.next
    }
}

class MergeKSortedListsTest {

    private val impls = listOf(
        // MergeKSortedListsFlattenAndSort()::mergeKLists,
//        MergeKSortedListsMergedList()::mergeKLists,
        MergeKSortedListsMinHeap()::mergeKLists,
    )

    @Test
    fun example1() {
        impls.forEach { f ->
            val input = arrayOf(
                buildList(1, 4, 5),
                buildList(1, 3, 4),
                buildList(2, 6)
            )
            assertEquals(listOf(1, 1, 2, 3, 4, 4, 5, 6), toList(f(input)))
        }
    }

    @Test
    fun emptyArray() {
        impls.forEach { f ->
            val input = emptyArray<ListNode?>()
            assertEquals(emptyList<Int>(), toList(f(input)))
        }
    }

    @Test
    fun arrayWithSingleNullList() {
        impls.forEach { f ->
            val input = arrayOf<ListNode?>(null)
            assertEquals(emptyList<Int>(), toList(f(input)))
        }
    }

    @Test
    fun singleList() {
        impls.forEach { f ->
            val input = arrayOf(buildList(1, 2, 3))
            assertEquals(listOf(1, 2, 3), toList(f(input)))
        }
    }

    @Test
    fun multipleEmptyLists() {
        impls.forEach { f ->
            val input = arrayOf<ListNode?>(null, null, null)
            assertEquals(emptyList<Int>(), toList(f(input)))
        }
    }

    @Test
    fun listsWithDuplicates() {
        impls.forEach { f ->
            val input = arrayOf(
                buildList(1, 1, 2),
                buildList(1, 3, 3),
                buildList(2, 2)
            )
            assertEquals(listOf(1, 1, 1, 2, 2, 2, 3, 3), toList(f(input)))
        }
    }

    @Test
    fun listsWithNegativeNumbers() {
        impls.forEach { f ->
            val input = arrayOf(
                buildList(-10, -3, 0),
                buildList(-5, 2),
                buildList(-7, -1, 4)
            )
            assertEquals(listOf(-10, -7, -5, -3, -1, 0, 2, 4), toList(f(input)))
        }
    }

    @Test
    fun unevenLengths() {
        impls.forEach { f ->
            val input = arrayOf(
                buildList(1),
                buildList(2, 3, 4, 5, 6),
                buildList(0, 7)
            )
            assertEquals(listOf(0, 1, 2, 3, 4, 5, 6, 7), toList(f(input)))
        }
    }

    private fun buildList(vararg values: Int): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy

        for (value in values) {
            tail.next = ListNode(value)
            tail = tail.next!!
        }

        return dummy.next
    }

    private fun toList(head: ListNode?): List<Int> {
        val result = ArrayList<Int>()
        var curr = head

        while (curr != null) {
            result.add(curr.`val`)
            curr = curr.next
        }

        return result
    }
}