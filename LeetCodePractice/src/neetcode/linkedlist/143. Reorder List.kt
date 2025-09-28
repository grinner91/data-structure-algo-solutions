package neetcode.linkedlist

class SolutionReorderList {
    //memory limit exceeded
    fun reorderList1(head: ListNode?): Unit {
        val nodes = mutableListOf<ListNode>()
        var cur = head
        while (cur != null) {
            nodes.add(cur)
        }
        var l = nodes.indices.first
        var r = nodes.indices.last

        while (l < r) {
            val temp = nodes[l].next
            nodes[l].next = nodes[r]
            l++
            if (l < r) {
                nodes[r].next = temp
                r--
            }
        }
        nodes[l].next = null
    }
    //optimal
    fun reorderList(head: ListNode?): Unit {
        //find middle node
        var slow = head
        var fast = head
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }
        //reverse 2nd half
        var second = slow?.next
        slow?.next = null
        var prev: ListNode? = null
        var curr = second
        while (curr != null) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        }
        //head of reversed 2nd half list
        second = prev

        var l1 = head
        var l2 = second
        while (l2 != null) {
            val n1 = l1?.next
            val n2 = l2.next
            l1?.next = l2
            l2.next = n1
            l1 = n1
            l2 = n2
        }
    }
}

