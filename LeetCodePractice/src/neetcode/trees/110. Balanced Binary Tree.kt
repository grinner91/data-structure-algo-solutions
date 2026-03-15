package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

//optimal
class BalancedBinaryBottomUpDFS {
    fun isBalanced(root: TreeNode?): Boolean {
        data class Result(val balanced: Boolean, val height: Int)

        fun dfs(node: TreeNode?): Result {
            if (node == null) return Result(true, 0)
            val lh = dfs(node.left)
            val rh = dfs(node.right)

            val balanced = lh.balanced && rh.balanced && abs(lh.height - rh.height) <= 1
            val height = 1 + maxOf(lh.height, rh.height)
            return Result(balanced, height)
        }
        return dfs(root).balanced
    }
}

class BalancedBinaryIterativePostOrder {
    fun isBalanced(root: TreeNode?): Boolean {
        if (root == null) return true
        val stack = ArrayDeque<Pair<TreeNode, Boolean>>()  //node to visited
        val heights = HashMap<TreeNode, Int>()
        stack.addLast(root to false)
        while (stack.isNotEmpty()) {
            val (cur, visited) = stack.removeLast()
            if (visited.not()) {
                stack.addLast(cur to true)
                cur.right?.let { stack.addLast(it to false) }
                cur.left?.let { stack.addLast(it to false) }
            } else {
                val lh = cur.left?.let { heights[it] ?: 0 } ?: 0
                val rh = cur.right?.let { heights[it] ?: 0 } ?: 0

                if (abs(lh - rh) > 1) return false

                heights[cur] = 1 + maxOf(lh, rh)
            }
        }
        return true
    }
}

class BalancedBinaryTreeTest {

    private val impls = listOf<(TreeNode?) -> Boolean>(
        // BalancedBinaryBottomUpDFS()::isBalanced,
        BalancedBinaryIterativePostOrder()::isBalanced
    )

    @Test
    fun `empty tree is balanced`() {
        impls.forEach { f ->
            assertEquals(true, f(null))
        }
    }

    @Test
    fun `single node tree is balanced`() {
        val root = TreeNode(1)

        impls.forEach { f ->
            assertEquals(true, f(cloneTree(root)))
        }
    }

    @Test
    fun `example balanced tree`() {
        val root = buildTree(
            3,
            9, 20,
            null, null, 15, 7
        )

        impls.forEach { f ->
            assertEquals(true, f(cloneTree(root)))
        }
    }

    @Test
    fun `example unbalanced tree`() {
        val root = buildTree(
            1,
            2, 2,
            3, 3, null, null,
            4, 4
        )

        impls.forEach { f ->
            assertEquals(false, f(cloneTree(root)))
        }
    }

    @Test
    fun `left skewed tree is unbalanced`() {
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(4)
                }
            }
        }

        impls.forEach { f ->
            assertEquals(false, f(cloneTree(root)))
        }
    }

    @Test
    fun `perfect tree is balanced`() {
        val root = buildTree(
            1,
            2, 3,
            4, 5, 6, 7
        )

        impls.forEach { f ->
            assertEquals(true, f(cloneTree(root)))
        }
    }

    @Test
    fun `tree with one deeper subtree but still balanced`() {
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(4)
            }
            right = TreeNode(3)
        }

        impls.forEach { f ->
            assertEquals(true, f(cloneTree(root)))
        }
    }

    @Test
    fun `unbalanced not only at root`() {
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(4)
                }
                right = TreeNode(5)
            }
            right = TreeNode(6)
        }

        impls.forEach { f ->
            assertEquals(false, f(cloneTree(root)))
        }
    }

    private fun buildTree(vararg values: Int?): TreeNode? {
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

    private fun cloneTree(node: TreeNode?): TreeNode? {
        if (node == null) return null

        val copy = TreeNode(node.`val`)
        copy.left = cloneTree(node.left)
        copy.right = cloneTree(node.right)
        return copy
    }
}