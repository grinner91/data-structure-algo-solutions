package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class InvertBinaryTreeRecursiveDfs {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null

        val lef = invertTree(root.left)
        val rig = invertTree(root.right)

        root.left = rig
        root.right = lef

        return root
    }
}

class InvertBinaryTreeIterativeDfs {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null

        val stack = ArrayDeque<TreeNode>()
        stack.addLast(root)
        while (stack.isNotEmpty()) {
            val cur = stack.removeLast()
            val temp = cur.left
            cur.left = cur.right
            cur.right = temp

            cur.left?.let { stack.addLast(it) }
            cur.right?.let { stack.addLast(it) }
        }
        return root
    }
}

class InvertBinaryTreeBFS {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null
        val que = ArrayDeque<TreeNode>()
        que.addLast(root)
        while (que.isNotEmpty()) {
            val node = que.removeFirst()

            val temp = node.left
            node.left = node.right
            node.right = temp

            node.left?.let { que.addLast(it) }
            node.right?.let { que.addLast(it) }
        }
        return root
    }
}
//class InvertBinaryTreeRecursive {
//    fun invertTree(root: TreeNode?): TreeNode? {
//
//    }
//}


class InvertBinaryTreeTest {

    private val impls = listOf(
        InvertBinaryTreeRecursiveDfs()::invertTree,
    )

    @Test
    fun example1() {
        impls.forEach { invert ->
            val root = buildTree(listOf(4, 2, 7, 1, 3, 6, 9))
            val actual = invert(root)
            assertEquals(
                listOf(4, 7, 2, 9, 6, 3, 1),
                toLevelOrder(actual)
            )
        }
    }

    @Test
    fun example2() {
        impls.forEach { invert ->
            val root = buildTree(listOf(2, 1, 3))
            val actual = invert(root)
            assertEquals(
                listOf(2, 3, 1),
                toLevelOrder(actual)
            )
        }
    }

    @Test
    fun emptyTree() {
        impls.forEach { invert ->
            val actual = invert(null)
            assertEquals(emptyList<Int?>(), toLevelOrder(actual))
        }
    }

    @Test
    fun singleNode() {
        impls.forEach { invert ->
            val root = buildTree(listOf(1))
            val actual = invert(root)
            assertEquals(
                listOf(1),
                toLevelOrder(actual)
            )
        }
    }

    @Test
    fun leftSkewedTree() {
        impls.forEach { invert ->
            val root = buildTree(listOf(1, 2, null, 3, null, 4))
            val actual = invert(root)
            assertEquals(
                listOf(1, null, 2, null, 3, null, 4),
                toLevelOrder(actual)
            )
        }
    }

    @Test
    fun rightSkewedTree() {
        impls.forEach { invert ->
            val root = buildTree(listOf(1, null, 2, null, 3, null, 4))
            val actual = invert(root)
            assertEquals(
                listOf(1, 2, null, 3, null, 4),
                toLevelOrder(actual)
            )
        }
    }

    private fun buildTree(values: List<Int?>): TreeNode? {
        if (values.isEmpty() || values[0] == null) return null

        val root = TreeNode(values[0]!!)
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)

        var i = 1
        while (i < values.size && queue.isNotEmpty()) {
            val node = queue.removeFirst()

            if (i < values.size && values[i] != null) {
                node.left = TreeNode(values[i]!!)
                queue.addLast(node.left!!)
            }
            i++

            if (i < values.size && values[i] != null) {
                node.right = TreeNode(values[i]!!)
                queue.addLast(node.right!!)
            }
            i++
        }

        return root
    }

    private fun toLevelOrder(root: TreeNode?): List<Int?> {
        if (root == null) return emptyList()

        val result = mutableListOf<Int?>()
        val queue = ArrayDeque<TreeNode?>()
        queue.addLast(root)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            if (node == null) {
                result.add(null)
            } else {
                result.add(node.`val`)
                queue.addLast(node.left)
                queue.addLast(node.right)
            }
        }

        while (result.isNotEmpty() && result.last() == null) {
            result.removeAt(result.lastIndex)
        }

        return result
    }
}