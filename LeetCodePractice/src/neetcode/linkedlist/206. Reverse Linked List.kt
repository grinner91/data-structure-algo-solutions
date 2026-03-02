package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

//class ListNode(var `val`: Int) {
//    var next: ListNode? = null
//}

class ReverseLinkedListRecursive {
    fun reverseList(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        val newHead = reverseList(head.next)

        head.next?.next = head //flips link
        head.next = null //cuts original link or cycle

        return newHead
    }
}

class ReverseLinkedListIterative {
    fun reverseList(head: ListNode?): ListNode? {
        if (head == null) return null
        var prev: ListNode? = null
        var cur = head
        while (cur != null) {
            val next = cur.next
            cur.next = prev
            prev = cur
            cur = next
        }
        return prev
    }
}

class ReverseLinkedListTest {
    private val impls = listOf(
//        ReverseLinkedListIterative()::reverseList,
        ReverseLinkedListRecursive()::reverseList
    )

    @Test
    fun `reverse normal list`() {
        val head = buildList(intArrayOf(1, 2, 3, 4, 5))

        impls.forEach { reverse ->
            val result = reverse(head.copy())
            assertArrayEquals(intArrayOf(5, 4, 3, 2, 1), toArray(result))
        }
    }

    @Test
    fun `single element`() {
        val head = buildList(intArrayOf(1))

        impls.forEach { reverse ->
            val result = reverse(head.copy())
            assertArrayEquals(intArrayOf(1), toArray(result))
        }
    }

    @Test
    fun `empty list`() {
        impls.forEach { reverse ->
            val result = reverse(null)
            assertNull(result)
        }
    }

    // Helpers

    private fun buildList(arr: IntArray): ListNode? {
        if (arr.isEmpty()) return null
        val head = ListNode(arr[0])
        var curr = head
        for (i in 1 until arr.size) {
            curr.next = ListNode(arr[i])
            curr = curr.next!!
        }
        return head
    }

    private fun toArray(head: ListNode?): IntArray {
        val list = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            list.add(curr.`val`)
            curr = curr.next
        }
        return list.toIntArray()
    }

    private fun ListNode?.copy(): ListNode? {
        if (this == null) return null
        val newHead = ListNode(this.`val`)
        var currOld = this.next
        var currNew = newHead
        while (currOld != null) {
            currNew.next = ListNode(currOld.`val`)
            currOld = currOld.next
            currNew = currNew.next!!
        }
        return newHead
    }
}