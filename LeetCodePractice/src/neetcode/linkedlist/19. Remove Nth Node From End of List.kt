package neetcode.linkedlist

/*
Input: head = [1,2,3,4], n = 2

Output: [1,2,4]
* */

fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    var size = 0
    var cur = head
    while (cur != null) {
        size++
        cur = cur.next
    }
    val removeIdx = size - n

    if(removeIdx == 0 )
        return head?.next

    cur = head
    for (i in 0 until size - 1) {
        if(i + 1 == removeIdx) {
            cur?.next = cur?.next?.next
            break
        }
        cur = cur?.next
    }
    return head
}

class RemoveNthFromEndTest {

}