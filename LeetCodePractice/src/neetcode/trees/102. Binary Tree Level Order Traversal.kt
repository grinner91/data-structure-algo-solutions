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
class SolutionLevelOrder {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        if (root == null) return result
        val que = ArrayDeque<TreeNode>()
        que.addLast(root)
        while (que.isNotEmpty()) {
            val size = que.size
            val nodes = mutableListOf<Int>()
            repeat(size) {
                val cur = que.removeFirst()
                nodes.add(cur.`val`)
                cur.left?.let { que.addLast(it) }
                cur.right?.let { que.addLast(it) }
            }
            result.add(nodes)
        }
        return result
    }
}


// ---- Remove this when running on LeetCode (they provide TreeNode) ----
//class TreeNode(var `val`: Int) { var left: TreeNode? = null; var right: TreeNode? = null }
// ---------------------------------------------------------------------

class BinaryTreeLevelOrderTraversalTest {

    private val sut = SolutionLevelOrder()

    // Build a tree from level-order values (nulls allowed)
    private fun buildLevelOrder(vararg vals: Int?): TreeNode? {
        if (vals.isEmpty() || vals[0] == null) return null
        val root = TreeNode(vals[0]!!)
        val q: ArrayDeque<TreeNode> = ArrayDeque()
        q.add(root)
        var i = 1
        while (q.isNotEmpty() && i < vals.size) {
            val node = q.removeFirst()
            if (i < vals.size) {
                vals[i]?.let { node.left = TreeNode(it); q.add(node.left!!) }
                i++
            }
            if (i < vals.size) {
                vals[i]?.let { node.right = TreeNode(it); q.add(node.right!!) }
                i++
            }
        }
        return root
    }

    @Test
    fun empty_tree_returns_empty_list() {
        val root: TreeNode? = null
        assertEquals(emptyList<List<Int>>(), sut.levelOrder(root))
    }

    @Test
    fun single_node() {
        val root = buildLevelOrder(1)
        assertEquals(listOf(listOf(1)), sut.levelOrder(root))
    }

    @Test
    fun leetcode_example() {
        // [3,9,20,null,null,15,7] -> [[3],[9,20],[15,7]]
        val root = buildLevelOrder(3, 9, 20, null, null, 15, 7)
        val expected = listOf(listOf(3), listOf(9, 20), listOf(15, 7))
        assertEquals(expected, sut.levelOrder(root))
    }

    @Test
    fun left_skewed() {
        // 3 -> 2 -> 1
        val root = buildLevelOrder(3, 2, null, 1)
        val expected = listOf(listOf(3), listOf(2), listOf(1))
        assertEquals(expected, sut.levelOrder(root))
    }
//
//    @Test
//    fun right_skewed() {
//        // 1 -> 2 -> 3 (right chain)
//        val root = buildLevelOrder(1, null, 2, null, null, null, 3)
//        val expected = listOf(listOf(1), listOf(2), listOf(3))
//        assertEquals(expected, sut.levelOrder(root))
//    }

    @Test
    fun with_duplicates() {
        //         1
        //       /   \
        //      1     1
        //     /
        //    1
        val root = buildLevelOrder(1, 1, 1, 1, null, null, null)
        val expected = listOf(listOf(1), listOf(1, 1), listOf(1))
        assertEquals(expected, sut.levelOrder(root))
    }

    @Test
    fun complete_tree() {
        // [1,2,3,4,5,6,7] -> [[1],[2,3],[4,5,6,7]]
        val root = buildLevelOrder(1, 2, 3, 4, 5, 6, 7)
        val expected = listOf(listOf(1), listOf(2, 3), listOf(4, 5, 6, 7))
        assertEquals(expected, sut.levelOrder(root))
    }
}
