package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KthSmallestElementInABstInorderList {
    fun kthSmallest(root: TreeNode?, k: Int): Int {
        val values = mutableListOf<Int>()
        fun dfs(node: TreeNode?) {
            if (node == null) return
            dfs(node.left)
            values.add(node.`val`)
            dfs(node.right)
        }
        dfs(root)
        return values[k - 1]
    }
}

class KthSmallestElementInABstIterativeDfsOptimal {
    fun kthSmallest(root: TreeNode?, k: Int): Int {
        val stack = ArrayDeque<TreeNode>()
        var cur = root
        var count = k
        while (cur != null || stack.isNotEmpty()) {
            while (cur != null) {
                stack.addLast(cur)
                cur = cur.left
            }
            val node = stack.removeLast()
            count--
            if (count == 0) return node.`val`
            cur = node.right
        }
        return -1
    }
}

class KthSmallestElementInABstTest {

    private val impls = listOf(
//        KthSmallestElementInABstInorderList()::kthSmallest,
        KthSmallestElementInABstIterativeDfsOptimal()::kthSmallest
    )

    @Test
    fun `example 1`() {
        val root = buildTree(arrayOf(3, 1, 4, null, 2))

        impls.forEach { f ->
            assertEquals(1, f(root, 1))
        }
    }

    @Test
    fun `example 2`() {
        val root = buildTree(arrayOf(5, 3, 6, 2, 4, null, null, 1))

        impls.forEach { f ->
            assertEquals(3, f(root, 3))
        }
    }

    @Test
    fun `single node`() {
        val root = buildTree(arrayOf(1))

        impls.forEach { f ->
            assertEquals(1, f(root, 1))
        }
    }

    @Test
    fun `left skewed tree`() {
        val root = buildTree(arrayOf(5, 4, null, 3, null, 2, null, 1))

        impls.forEach { f ->
            assertEquals(1, f(root, 1))
            assertEquals(3, f(root, 3))
            assertEquals(5, f(root, 5))
        }
    }

    @Test
    fun `right skewed tree`() {
        val root = buildTree(arrayOf(1, null, 2, null, 3, null, 4, null, 5))

        impls.forEach { f ->
            assertEquals(1, f(root, 1))
            assertEquals(4, f(root, 4))
            assertEquals(5, f(root, 5))
        }
    }

    @Test
    fun `balanced bst`() {
        val root = buildTree(arrayOf(4, 2, 6, 1, 3, 5, 7))

        impls.forEach { f ->
            assertEquals(1, f(root, 1))
            assertEquals(4, f(root, 4))
            assertEquals(7, f(root, 7))
        }
    }

    private fun buildTree(values: Array<Int?>): TreeNode? {
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