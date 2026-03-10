package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Example:
 * var ti = Node(5)
 * var v = ti.`val`
 * Definition for a Node.
 * class Node(var `val`: Int) {
 *     var next: Node? = null
 *     var random: Node? = null
 * }
 */

class Node(var `val`: Int) {
    var next: Node? = null
    var random: Node? = null
}

class CopyListWithRandomPointerTwoPass {
    fun copyRandomList(head: Node?): Node? {
        val map = hashMapOf<Node?, Node?>(null to null)
        var cur: Node? = head
        while (cur != null) {
            map[cur] = Node(cur.`val`)
            cur = cur.next
        }
        cur = head
        while (cur != null) {
            val copy = map[cur]
            copy?.next = map[cur.next]
            copy?.random = map[cur.random]
            cur = cur.next
        }
        return map[head]
    }
}

class CopyListWithRandomPointerOnePass {
    fun copyRandomList(head: Node?): Node? {
        if (head == null) return null
        val copies = HashMap<Node, Node>()

        fun getCopy(node: Node?): Node? {
            if (node == null) return null
            return copies.getOrPut(node) { Node(node.`val`) }
        }

        var cur = head
        while (cur != null) {
            val copy = getCopy(cur)!!
            copy.next = getCopy(cur.next)
            copy.random = getCopy(cur.random)
            cur = cur.next
        }
        return copies[head]
    }
}

class CopyListWithRandomPointerInterleaving {
    fun copyRandomList(head: Node?): Node? {
        if (head == null) return null
        //copy list, and next
        var cur = head
        while (cur != null) {
            val next = cur.next
            val copy = Node(cur.`val`)
            copy.next = next
            cur.next = copy
            cur = next
        }
        //assign copy.random point to new node
        cur = head
        while (cur != null) {
            val copy = cur.next
            copy?.random = cur.random?.next
            cur = copy?.next
        }
        //restore original list, detach new list
        cur = head
        val dummy = Node(0)
        var copyTail: Node? = dummy
        while (cur != null) {
            val copy = cur.next
            val nextOld = copy?.next
            copyTail?.next = copy
            copyTail = copy
            cur.next = nextOld
            cur = nextOld
        }

        return dummy.next
    }
}

class CopyListWithRandomPointerTest {

    private val impls = listOf(
        // CopyListWithRandomPointerTwoPass()::copyRandomList,
        //CopyListWithRandomPointerOnePass()::copyRandomList,
        CopyListWithRandomPointerInterleaving()::copyRandomList
    )

    @Test
    fun `null list`() {
        impls.forEach { copyFn ->
            assertNull(copyFn(null))
        }
    }

    @Test
    fun `single node with null random`() {
        val head = buildList(
            values = intArrayOf(7),
            randomIndices = arrayOf(null),
        )

        impls.forEach { copyFn ->
            val copied = copyFn(head)
            assertDeepCopy(head, copied)
            assertListStructure(
                copied,
                expectedValues = intArrayOf(7),
                expectedRandomIndices = arrayOf(null),
            )
        }
    }

    @Test
    fun `single node pointing random to itself`() {
        val head = buildList(
            values = intArrayOf(1),
            randomIndices = arrayOf(0),
        )

        impls.forEach { copyFn ->
            val copied = copyFn(head)
            assertDeepCopy(head, copied)
            assertListStructure(
                copied,
                expectedValues = intArrayOf(1),
                expectedRandomIndices = arrayOf(0),
            )
        }
    }

    @Test
    fun `leetcode example 1`() {
        val head = buildList(
            values = intArrayOf(7, 13, 11, 10, 1),
            randomIndices = arrayOf(null, 0, 4, 2, 0),
        )

        impls.forEach { copyFn ->
            val copied = copyFn(head)
            assertDeepCopy(head, copied)
            assertListStructure(
                copied,
                expectedValues = intArrayOf(7, 13, 11, 10, 1),
                expectedRandomIndices = arrayOf(null, 0, 4, 2, 0),
            )
        }
    }

    @Test
    fun `leetcode example 2`() {
        val head = buildList(
            values = intArrayOf(1, 2),
            randomIndices = arrayOf(1, 1),
        )

        impls.forEach { copyFn ->
            val copied = copyFn(head)
            assertDeepCopy(head, copied)
            assertListStructure(
                copied,
                expectedValues = intArrayOf(1, 2),
                expectedRandomIndices = arrayOf(1, 1),
            )
        }
    }

    @Test
    fun `leetcode example 3`() {
        val head = buildList(
            values = intArrayOf(3, 3, 3),
            randomIndices = arrayOf(null, 0, null),
        )

        impls.forEach { copyFn ->
            val copied = copyFn(head)
            assertDeepCopy(head, copied)
            assertListStructure(
                copied,
                expectedValues = intArrayOf(3, 3, 3),
                expectedRandomIndices = arrayOf(null, 0, null),
            )
        }
    }

    @Test
    fun `copy is independent from original`() {
        impls.forEach { copyFn ->
            val head = buildList(
                values = intArrayOf(5, 6, 7),
                randomIndices = arrayOf(2, null, 1),
            )

            val copied = copyFn(head)
            assertDeepCopy(head, copied)

            head!!.`val` = 100
            head.next!!.`val` = 200
            head.random = null
            head.next!!.random = head

            assertListStructure(
                copied,
                expectedValues = intArrayOf(5, 6, 7),
                expectedRandomIndices = arrayOf(2, null, 1),
            )
        }
    }

    private fun buildList(values: IntArray, randomIndices: Array<Int?>): Node? {
        if (values.isEmpty()) return null

        require(values.size == randomIndices.size) {
            "values and randomIndices must have the same size"
        }

        val nodes = values.map { Node(it) }

        for (i in 0 until nodes.lastIndex) {
            nodes[i].next = nodes[i + 1]
        }

        for (i in randomIndices.indices) {
            val randomIndex = randomIndices[i]
            if (randomIndex != null) {
                require(randomIndex in nodes.indices) {
                    "random index out of bounds at position $i"
                }
                nodes[i].random = nodes[randomIndex]
            }
        }

        return nodes[0]
    }

    private fun assertListStructure(
        head: Node?,
        expectedValues: IntArray,
        expectedRandomIndices: Array<Int?>,
    ) {
        val nodes = collectNodes(head)

        assertEquals(expectedValues.size, nodes.size, "List length mismatch")

        for (i in expectedValues.indices) {
            assertEquals(expectedValues[i], nodes[i].`val`, "Value mismatch at index $i")
        }

        val indexMap = HashMap<Node, Int>(nodes.size)
        nodes.forEachIndexed { index, node -> indexMap[node] = index }

        for (i in nodes.indices) {
            val actualRandomIndex = nodes[i].random?.let { indexMap[it] }
            assertEquals(
                expectedRandomIndices[i],
                actualRandomIndex,
                "Random pointer mismatch at index $i",
            )
        }
    }

    private fun assertDeepCopy(original: Node?, copied: Node?) {
        if (original == null || copied == null) {
            assertEquals(original, copied)
            return
        }

        val originalNodes = collectNodes(original)
        val copiedNodes = collectNodes(copied)

        assertEquals(originalNodes.size, copiedNodes.size, "Copied list length mismatch")

        for (i in originalNodes.indices) {
            assertNotSame(originalNodes[i], copiedNodes[i], "Node at index $i was not copied")
            assertEquals(originalNodes[i].`val`, copiedNodes[i].`val`, "Value mismatch at index $i")
        }

        val copiedSet = copiedNodes.toHashSet()
        for (node in copiedNodes) {
            node.random?.let {
                assertTrue(it in copiedSet, "Copied random should point only within copied list")
            }
            node.next?.let {
                assertTrue(it in copiedSet, "Copied next should point only within copied list")
            }
        }

        val originalIndexMap = HashMap<Node, Int>(originalNodes.size)
        val copiedIndexMap = HashMap<Node, Int>(copiedNodes.size)

        originalNodes.forEachIndexed { index, node -> originalIndexMap[node] = index }
        copiedNodes.forEachIndexed { index, node -> copiedIndexMap[node] = index }

        for (i in originalNodes.indices) {
            val originalRandomIndex = originalNodes[i].random?.let { originalIndexMap[it] }
            val copiedRandomIndex = copiedNodes[i].random?.let { copiedIndexMap[it] }
            assertEquals(
                originalRandomIndex,
                copiedRandomIndex,
                "Random structure mismatch at index $i",
            )
        }
    }

    private fun collectNodes(head: Node?): List<Node> {
        val nodes = mutableListOf<Node>()
        var curr = head
        while (curr != null) {
            nodes.add(curr)
            curr = curr.next
        }
        return nodes
    }
}