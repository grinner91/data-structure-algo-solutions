package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DiameterOfBinaryDFS {
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        var res = 0
        fun dfs(node: TreeNode?): Int {
            if (node == null) return 0
            val left = dfs(node.left)
            val right = dfs(node.right)
            res = maxOf(res, left + right)
            return 1 + maxOf(left, right)
        }
        dfs(root)
        return res
    }
}

class DiameterOfBinaryDfsIterative {
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        if (root == null) return 0
        val stack = ArrayDeque<Pair<TreeNode?, Boolean>>() //node to visited
        val height = HashMap<TreeNode, Int>()
        var diam = 0
        stack.addLast(root to false)
        while (stack.isNotEmpty()) {
            val (node, visited) = stack.removeLast()
            if (node == null) continue
            if (visited) {
                val lht = height[node.left] ?: 0
                val rht = height[node.right] ?: 0
                diam = maxOf(diam, lht + rht)
                height[node] = 1 + maxOf(lht, rht)
            } else {
                stack.addLast(node to true)
                stack.add(node.left to false)
                stack.add(node.right to false)
            }
        }
        return diam
    }
}

class DiameterOfBinaryTreeTest {

    private val impls = listOf(
        DiameterOfBinaryDFS()::diameterOfBinaryTree,
    )

    @Test
    fun example1() {
        // [1,2,3,4,5]
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        // Diameter = 3 (4-2-1-3 or 5-2-1-3)
        val root = node(
            1,
            node(2, node(4), node(5)),
            node(3)
        )

        impls.forEach { f ->
            assertEquals(3, f(root))
        }
    }

    @Test
    fun example2() {
        // [1,2]
        val root = node(1, node(2), null)

        impls.forEach { f ->
            assertEquals(1, f(root))
        }
    }

    @Test
    fun nullTree() {
        impls.forEach { f ->
            assertEquals(0, f(null))
        }
    }

    @Test
    fun singleNode() {
        val root = node(1)

        impls.forEach { f ->
            assertEquals(0, f(root))
        }
    }

    @Test
    fun leftSkewedTree() {
        // 1 - 2 - 3 - 4
        val root = node(
            1,
            node(
                2,
                node(
                    3,
                    node(4),
                    null
                ),
                null
            ),
            null
        )

        impls.forEach { f ->
            assertEquals(3, f(root))
        }
    }

    @Test
    fun rightSkewedTree() {
        // 1 - 2 - 3 - 4
        val root = node(
            1,
            null,
            node(
                2,
                null,
                node(
                    3,
                    null,
                    node(4)
                )
            )
        )

        impls.forEach { f ->
            assertEquals(3, f(root))
        }
    }

    @Test
    fun diameterNotPassingThroughRoot() {
        //         1
        //        /
        //       2
        //      / \
        //     3   4
        //    /     \
        //   5       6
        //
        // Diameter = 4 (5-3-2-4-6)
        val root = node(
            1,
            node(
                2,
                node(3, node(5), null),
                node(4, null, node(6))
            ),
            null
        )

        impls.forEach { f ->
            assertEquals(4, f(root))
        }
    }

    @Test
    fun balancedTree() {
        //         1
        //       /   \
        //      2     3
        //     / \   / \
        //    4   5 6   7
        //
        // Diameter = 4
        val root = node(
            1,
            node(2, node(4), node(5)),
            node(3, node(6), node(7))
        )

        impls.forEach { f ->
            assertEquals(4, f(root))
        }
    }

    private fun node(
        value: Int,
        left: TreeNode? = null,
        right: TreeNode? = null
    ): TreeNode {
        return TreeNode(value).also {
            it.left = left
            it.right = right
        }
    }
}
