package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

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
class SolutionMaxPathSum {
//    //    //DFS optimal, TC O(n), SC O(n)
//    var result: Int = Int.MIN_VALUE
//    fun maxPathSum(root: TreeNode?): Int {
//        result = Int.MIN_VALUE
//        dfs(root)
//        return result
//    }
//
//    private fun dfs(root: TreeNode?): Int {
//        if (root == null) return 0
//        val leftMax = maxOf(0, dfs(root.left)) // if node value is negative, drop it
//        val rightMax = maxOf(0, dfs(root.right)) // if node value is negative, drop it
//
//        result = maxOf(result, root.`val` + leftMax + rightMax)
//
//        return root.`val` + maxOf(leftMax, rightMax)
//    }

    ////chatgpt - idiomatic Kotlin version of the optimal O(n) solution
    fun maxPathSum(root: TreeNode?): Int {
        var best = Int.MIN_VALUE

        fun dfs(node: TreeNode?): Int = node?.let {
            val left = dfs(it.left).coerceAtLeast(0)
            val right = dfs(it.right).coerceAtLeast(0)
            best = maxOf(best, it.`val` + left + right)   // path through this node
            it.`val` + maxOf(left, right)                 // best single-branch gain upward
        } ?: 0

        dfs(root)
        return best
    }
}


class SolutionMaxPathSumTest {

    // Helper to build a binary tree from level-order values (null for missing nodes)
    private fun buildTreeLevelOrder(values: List<Int?>): TreeNode? {
        if (values.isEmpty() || values[0] == null) return null
        val root = TreeNode(values[0]!!)
        val q: ArrayDeque<TreeNode> = ArrayDeque()
        q.add(root)
        var i = 1
        while (i < values.size && q.isNotEmpty()) {
            val cur = q.removeFirst()

            if (i < values.size) {
                val v = values[i++]
                if (v != null) {
                    cur.left = TreeNode(v)
                    q.add(cur.left!!)
                }
            }
            if (i < values.size) {
                val v = values[i++]
                if (v != null) {
                    cur.right = TreeNode(v)
                    q.add(cur.right!!)
                }
            }
        }
        return root
    }

    private val sut = SolutionMaxPathSum()

    @Test
    fun example1() {
        // [-10,9,20,null,null,15,7] -> 42
        val root = buildTreeLevelOrder(listOf(-10, 9, 20, null, null, 15, 7))
        val ans = sut.maxPathSum(root)
        assertEquals(42, ans)
    }

    @Test
    fun example2() {
        // [1,2,3] -> 6
        val root = buildTreeLevelOrder(listOf(1, 2, 3))
        val ans = sut.maxPathSum(root)
        assertEquals(6, ans)
    }

    @Test
    fun singleNegativeNode() {
        // [-3] -> -3
        val root = buildTreeLevelOrder(listOf(-3))
        val ans = sut.maxPathSum(root)
        assertEquals(-3, ans)
    }

    @Test
    fun mixedTwoNodes() {
        // [2,-1] -> 2 (best path is just the node 2)
        val root = buildTreeLevelOrder(listOf(2, -1))
        val ans = sut.maxPathSum(root)
        assertEquals(2, ans)
    }

    @Test
    fun allNegative() {
        // [-2,-1] -> -1 (choose the single largest node)
        val root = buildTreeLevelOrder(listOf(-2, -1))
        val ans = sut.maxPathSum(root)
        assertEquals(-1, ans)
    }

    @Test
    fun skewed() {
        // [1,2,null,3,null,4] -> 10 (1+2+3+4)
        val root = TreeNode(1).apply {
            left = TreeNode(2).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(4)
                }
            }
        }
        val ans = sut.maxPathSum(root)
        assertEquals(10, ans)
    }
}


