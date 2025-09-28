package leetcode

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

fun hasCycle(head: ListNode?): Boolean {
    var slow: ListNode? = head
    var fast: ListNode? = head
    while (slow?.next != null && fast?.next?.next != null) {
        slow = slow.next
        fast = fast.next?.next
        if (slow == fast) return true
    }
    return false
}

class TestCyecles {
    @Test
    fun test1() {
        val n1 = ListNode(3)
        val n2 = ListNode(2)
        val n3 = ListNode(0)
        val n4 = ListNode(-4)
        n1.next = n2
        n2.next = n3
        n3.next = n4
        n4.next = n2

        Assertions.assertTrue(hasCycle(n1))
    }
}

