package neetcode.linkedlist

import java.util.*

class SolutionMergeKLists {

    fun mergeKLists1(lists: Array<ListNode?>): ListNode? {
        // find min head among lists
        //move min head to next
        //add min head to result list
        var heads = lists.filterNotNull().toMutableList()
        val dummy = ListNode(0)
        var tail: ListNode? = dummy

        while (heads.size > 0) {
            var minHead: ListNode? = heads.first()
            var minIdx = 0
            heads.forEachIndexed { i, head ->
                if ((head?.`val` ?: 0) < (minHead?.`val` ?: 0)) {
                    minHead = head
                    minIdx = i
                }
            }
            tail?.next = minHead
            tail = tail?.next
            if (minHead?.next == null) {
                heads.removeAt(minIdx)
            } else {
                heads[minIdx] = minHead?.next!!
            }
        }

        return dummy.next
    }

    //optimal - min heap
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null

        val pq = PriorityQueue(compareBy<ListNode> { it.`val` })

        lists.forEach { it?.let(pq::add) }

        val dummy = ListNode(0)
        var tail: ListNode = dummy

        while (pq.isNotEmpty()) {
            val node = pq.poll()
            tail.next = node
            tail = node
            node.next?.let(pq::add)
        }

        return dummy.next
    }
}

class SolutionMergeKListsTest {

}