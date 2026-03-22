package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CountGoodNodesInBinaryTreeBfs {
    fun goodNodes(root: TreeNode?): Int {
        if (root == null) return 0
        val que = ArrayDeque<Pair<TreeNode, Int>>() // node : maxSoFar
        que.addLast(root to root.`val`)
        var count = 0
        while (que.isNotEmpty()) {
            val (cur, maxSoFar) = que.removeFirst()
            if (cur.`val` >= maxSoFar) {
                count++
            }
            val newMax = maxOf(cur.`val`, maxSoFar)
            cur.left?.let { que.addLast(it to newMax) }
            cur.right?.let { que.addLast(it to newMax) }
        }
        return count
    }
}

class CountGoodNodesInBinaryTreeTest {

    private val impls = listOf(
        CountGoodNodesInBinaryTreeBfs()::goodNodes,
    )

    @Test
    fun `example 1`() {
        val root = buildTree(arrayOf(3, 1, 4, 3, null, 1, 5))

        impls.forEach { goodNodes ->
            assertEquals(4, goodNodes(root))
        }
    }

    @Test
    fun `example 2`() {
        val root = buildTree(arrayOf(3, 3, null, 4, 2))

        impls.forEach { goodNodes ->
            assertEquals(3, goodNodes(root))
        }
    }

    @Test
    fun `single node`() {
        val root = buildTree(arrayOf(1))

        impls.forEach { goodNodes ->
            assertEquals(1, goodNodes(root))
        }
    }

    @Test
    fun `empty tree`() {
        impls.forEach { goodNodes ->
            assertEquals(0, goodNodes(null))
        }
    }

    @Test
    fun `all nodes good increasing along paths`() {
        val root = buildTree(arrayOf(1, 2, 3, 4, 5, 6, 7))

        impls.forEach { goodNodes ->
            assertEquals(7, goodNodes(root))
        }
    }

    @Test
    fun `only root good`() {
        val root = buildTree(arrayOf(5, 4, 3, 2, 1, 0, -1))

        impls.forEach { goodNodes ->
            assertEquals(1, goodNodes(root))
        }
    }

    @Test
    fun `mixed negative values`() {
        val root = buildTree(arrayOf(-1, -2, 0, -3, null, -1, 2))

        impls.forEach { goodNodes ->
            assertEquals(3, goodNodes(root))
        }
    }

    @Test
    fun `right skewed tree`() {
        val root = buildTree(arrayOf(2, null, 4, null, 1, null, 5))

        impls.forEach { goodNodes ->
            assertEquals(3, goodNodes(root))
        }
    }

    @Test
    fun `left skewed tree`() {
        val root = buildTree(arrayOf(3, 1, null, 3, null, 4))

        impls.forEach { goodNodes ->
            assertEquals(3, goodNodes(root))
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