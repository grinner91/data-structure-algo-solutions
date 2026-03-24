package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BinaryTreeMaximumPathSumDfsOptimal {
    fun maxPathSum(root: TreeNode?): Int {
        root ?: return 0
        var res = Int.MIN_VALUE
        fun dfs(node: TreeNode?): Int {
            node ?: return 0

            val leftMax = maxOf(dfs(node.left), 0)
            val rightMax = maxOf(dfs(node.right), 0)
            val sumThroughNode = node.`val` + leftMax + rightMax

            res = maxOf(res, sumThroughNode)

            return node.`val` + maxOf(leftMax, rightMax)
        }
        dfs(root)
        return res
    }
}

class BinaryTreeMaximumPathSumTest {

    private val implementations = listOf(
        BinaryTreeMaximumPathSumDfsOptimal()::maxPathSum,
    )

    @Test
    fun `single positive node`() {
        val root = node(5)

        implementations.forEach { maxPathSum ->
            assertEquals(5, maxPathSum(root))
        }
    }

    @Test
    fun `single negative node`() {
        val root = node(-3)

        implementations.forEach { maxPathSum ->
            assertEquals(-3, maxPathSum(root))
        }
    }

    @Test
    fun `example 1`() {
        //      1
        //     / \
        //    2   3
        //
        // Best path: 2 -> 1 -> 3 = 6
        val root = node(
            1,
            node(2),
            node(3)
        )

        implementations.forEach { maxPathSum ->
            assertEquals(6, maxPathSum(root))
        }
    }

    @Test
    fun `example 2`() {
        //        -10
        //        /  \
        //       9   20
        //          /  \
        //         15   7
        //
        // Best path: 15 -> 20 -> 7 = 42
        val root = node(
            -10,
            node(9),
            node(
                20,
                node(15),
                node(7)
            )
        )

        implementations.forEach { maxPathSum ->
            assertEquals(42, maxPathSum(root))
        }
    }

    @Test
    fun `all negative values`() {
        //      -3
        //      / \
        //    -2  -5
        //
        // Best path is just -2
        val root = node(
            -3,
            node(-2),
            node(-5)
        )

        implementations.forEach { maxPathSum ->
            assertEquals(-2, maxPathSum(root))
        }
    }

    @Test
    fun `best path stays inside one subtree`() {
        //         1
        //        / \
        //       2  -100
        //      / \
        //     3   4
        //
        // Best path: 3 -> 2 -> 4 = 9
        val root = node(
            1,
            node(
                2,
                node(3),
                node(4)
            ),
            node(-100)
        )

        implementations.forEach { maxPathSum ->
            assertEquals(9, maxPathSum(root))
        }
    }

    @Test
    fun `best path uses only one side because other side is negative`() {
        //      2
        //     / \
        //   -1   3
        //
        // Best path: 2 -> 3 = 5
        val root = node(
            2,
            node(-1),
            node(3)
        )

        implementations.forEach { maxPathSum ->
            assertEquals(5, maxPathSum(root))
        }
    }

    @Test
    fun `skips negative child contributions`() {
        //        10
        //       /  \
        //      2   10
        //          / \
        //        -20  1
        //
        // Best path: 2 -> 10 -> 10 -> 1 = 23
        val root = node(
            10,
            node(2),
            node(
                10,
                node(-20),
                node(1)
            )
        )

        implementations.forEach { maxPathSum ->
            assertEquals(23, maxPathSum(root))
        }
    }

    @Test
    fun `left skewed tree`() {
        //    1
        //   /
        //  2
        // /
        //3
        //
        // Best path: 3 -> 2 -> 1 = 6
        val root = node(
            1,
            node(
                2,
                node(3),
                null
            ),
            null
        )

        implementations.forEach { maxPathSum ->
            assertEquals(6, maxPathSum(root))
        }
    }

    @Test
    fun `right skewed with negatives`() {
        //  -2
        //    \
        //    -1
        //
        // Best path is -1
        val root = node(
            -2,
            null,
            node(-1)
        )

        implementations.forEach { maxPathSum ->
            assertEquals(-1, maxPathSum(root))
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