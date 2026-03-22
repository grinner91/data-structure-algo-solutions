package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BinaryTreeRightSideViewBfs {
    fun rightSideView(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()
        val res = mutableListOf<Int>()
        val que = ArrayDeque<TreeNode>()
        que.addLast(root)
        while (que.isNotEmpty()) {
            val count = que.size
            var cur: TreeNode? = null
            repeat(count) {
                cur = que.removeFirst()
                cur?.left?.let { que.addLast(it) }
                cur?.right?.let { que.addLast(it) }
            }
            cur?.let { res.add(it.`val`) }
        }
        return res
    }
}

class BinaryTreeRightSideViewDfsRecursive {
    fun rightSideView(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()
        val res = mutableListOf<Int>()
        fun dfs(node: TreeNode?, depth: Int) {
            if (node == null) return
            if (depth == res.size) {
                res.add(node.`val`)
            }
            dfs(node.right, depth + 1)
            dfs(node.left, depth + 1)
        }
        dfs(root, 0)
        return res
    }
}

class BinaryTreeRightSideViewDfsIterative {
    fun rightSideView(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()
        val res = mutableListOf<Int>()
        val stack = ArrayDeque<Pair<TreeNode, Int>>()
        stack.addLast(root to 0)
        while (stack.isNotEmpty()) {
            val (cur, depth) = stack.removeLast()
            if (depth == res.size) {
                res.add(cur.`val`)
            }
            cur.left?.let { stack.add(it to depth + 1) }
            cur.right?.let { stack.addLast(it to depth + 1) }
        }
        return res
    }
}

class BinaryTreeRightSideViewTest {

    private val impls = listOf(
     //   BinaryTreeRightSideViewBfs()::rightSideView,
//        BinaryTreeRightSideViewDfsIterative()::rightSideView,
        BinaryTreeRightSideViewDfsRecursive()::rightSideView
    )

    @Test
    fun example1() {
        val root = buildTree<Any>(arrayOf(1, 2, 3, null, 5, null, 4))
        val expected = listOf(1, 3, 4)

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    @Test
    fun example2() {
        val root = buildTree<Any>(arrayOf(1, null, 3))
        val expected = listOf(1, 3)

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    @Test
    fun example3() {
        val root = buildTree<Int?>(arrayOf())
        val expected = emptyList<Int>()

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    @Test
    fun singleNode() {
        val root = buildTree<Any>(arrayOf(1))
        val expected = listOf(1)

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    @Test
    fun leftSkewedTree() {
        val root = buildTree<Any>(arrayOf(1, 2, null, 3, null, 4))
        val expected = listOf(1, 2, 3, 4)

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    @Test
    fun rightSkewedTree() {
        val root = buildTree<Any>(arrayOf(1, null, 2, null, 3, null, 4))
        val expected = listOf(1, 2, 3, 4)

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    @Test
    fun fullTree() {
        val root = buildTree<Any>(arrayOf(1, 2, 3, 4, 5, 6, 7))
        val expected = listOf(1, 3, 7)

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    @Test
    fun mixedShapeTree() {
        val root = buildTree<Any>(arrayOf(1, 2, 3, null, 5, 6, null, null, null, 7))
        val expected = listOf(1, 3, 6, 7)

        impls.forEach { f ->
            assertEquals(expected, f(root))
        }
    }

    private fun <T> buildTree(values: Array<Int?>): TreeNode? {
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
}
