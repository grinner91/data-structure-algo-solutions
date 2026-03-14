package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaximumDepthOfBinaryTreeDfsBottomUp {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        return 1 + maxOf(maxDepth(root.left), maxDepth(root.right))
    }
}

class MaximumDepthOfBinaryTreeDfsTopDown {
    fun maxDepth(root: TreeNode?): Int {
        var res = 0
        fun dfs(node: TreeNode?, depth: Int) {
            if (node == null) return

            res = maxOf(res, depth)

            dfs(node.left, depth + 1)
            dfs(node.right, depth + 1)
        }
        dfs(root, 1)
        return res
    }
}

class MaximumDepthOfBinaryTreeIterativeStack {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        val stack = ArrayDeque<Pair<TreeNode, Int>>()
        stack.addLast(root to 1)
        var res = 0
        while (stack.isNotEmpty()) {
            val (node, depth) = stack.removeLast()

            res = maxOf(res, depth)
            node.left?.let { stack.addLast(it to depth + 1) }
            node.right?.let { stack.addLast(it to depth + 1) }
        }
        return res
    }
}

class MaximumDepthOfBinaryTreeBFS {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        val que = ArrayDeque<TreeNode>()
        que.addLast(root)
        var depth = 0

        while (que.isNotEmpty()) {
            depth++
            val levelSize = que.size
            repeat(levelSize) {
                val cur = que.removeFirst()
                cur.left?.let { que.addLast(it) }
                cur.right?.let { que.addLast(it) }
            }
        }
        return depth
    }
}

class MaximumDepthOfBinaryTreeTest {

    private val impls = listOf(
//        MaximumDepthOfBinaryTreeDfsBottomUp()::maxDepth,
//        MaximumDepthOfBinaryTreeDfsTopDown()::maxDepth,
        MaximumDepthOfBinaryTreeBFS()::maxDepth
    )

    @Test
    fun emptyTree() {
        impls.forEach { f ->
            assertEquals(0, f(null))
        }
    }

    @Test
    fun singleNode() {
        val root = node(1)

        impls.forEach { f ->
            assertEquals(1, f(root))
        }
    }

    @Test
    fun example1() {
        val root = treeOf(3, 9, 20, null, null, 15, 7)

        impls.forEach { f ->
            assertEquals(3, f(root))
        }
    }

    @Test
    fun example2() {
        val root = treeOf(1, null, 2)

        impls.forEach { f ->
            assertEquals(2, f(root))
        }
    }

    @Test
    fun leftSkewedTree() {
        val root = treeOf(1, 2, null, 3, null, 4, null)

        impls.forEach { f ->
            assertEquals(4, f(root))
        }
    }

    @Test
    fun rightSkewedTree() {
        val root = treeOf(1, null, 2, null, 3, null, 4)

        impls.forEach { f ->
            assertEquals(4, f(root))
        }
    }

    @Test
    fun completeTree() {
        val root = treeOf(1, 2, 3, 4, 5, 6, 7)

        impls.forEach { f ->
            assertEquals(3, f(root))
        }
    }

    @Test
    fun unevenTree() {
        val root = treeOf(1, 2, 3, 4, null, null, null, 5)

        impls.forEach { f ->
            assertEquals(4, f(root))
        }
    }

    private fun node(value: Int): TreeNode = TreeNode(value)

    private fun treeOf(vararg values: Int?): TreeNode? {
        if (values.isEmpty() || values[0] == null) return null

        val root = TreeNode(values[0]!!)
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)

        var i = 1
        while (i < values.size && queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (i < values.size && values[i] != null) {
                current.left = TreeNode(values[i]!!)
                queue.addLast(current.left!!)
            }
            i++

            if (i < values.size && values[i] != null) {
                current.right = TreeNode(values[i]!!)
                queue.addLast(current.right!!)
            }
            i++
        }

        return root
    }
}