package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Floyd's Tortoise & Hare (Two pointers)
 * Time: O(n), Space: O(1)
 */
class LinkedListCycleFloyd {
    fun hasCycle(head: ListNode?): Boolean {
        var slow = head
        var fast = head?.next
        while (slow != null && fast != null) {
            if (slow == fast) return true
            slow = slow.next
            fast = fast.next?.next
        }
        return false
    }
}

class LinkedListCycleHashSet {
    fun hasCycle(head: ListNode?): Boolean {
        val seen = HashSet<ListNode>()
        var cur = head
        while (cur != null) {
            if(!seen.add(cur)) return true
            cur = cur.next
        }
        return false
    }
}


class LinkedListCycleTest {

    private val impls = listOf(
        LinkedListCycleFloyd()::hasCycle,
        LinkedListCycleHashSet()::hasCycle,
    )

    @Test
    fun `empty list - no cycle`() {
        impls.forEach { hasCycle ->
            assertFalse(hasCycle(null))
        }
    }

    @Test
    fun `single node no cycle`() {
        impls.forEach { hasCycle ->
            val head = ListNode(1)
            assertFalse(hasCycle(head))
        }
    }

    @Test
    fun `single node with self cycle`() {
        impls.forEach { hasCycle ->
            val head = ListNode(1)
            head.next = head
            assertTrue(hasCycle(head))
        }
    }

    @Test
    fun `two nodes no cycle`() {
        impls.forEach { hasCycle ->
            val a = ListNode(1)
            val b = ListNode(2)
            a.next = b
            assertFalse(hasCycle(a))
        }
    }

    @Test
    fun `two nodes cycle back to head`() {
        impls.forEach { hasCycle ->
            val a = ListNode(1)
            val b = ListNode(2)
            a.next = b
            b.next = a
            assertTrue(hasCycle(a))
        }
    }

    @Test
    fun `cycle starts in the middle`() {
        impls.forEach { hasCycle ->
            // 3 -> 2 -> 0 -> -4
            //           ^      |
            //           |______|
            val n3 = ListNode(3)
            val n2 = ListNode(2)
            val n0 = ListNode(0)
            val n4 = ListNode(-4)

            n3.next = n2
            n2.next = n0
            n0.next = n4
            n4.next = n2

            assertTrue(hasCycle(n3))
        }
    }

    @Test
    fun `longer list no cycle`() {
        impls.forEach { hasCycle ->
            val nodes = (1..10).map { ListNode(it) }
            for (i in 0 until nodes.size - 1) nodes[i].next = nodes[i + 1]
            assertFalse(hasCycle(nodes[0]))
        }
    }
}

