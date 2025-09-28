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
 *
 * Input: preorder = [1,2,3,4], inorder = [2,1,3,4]
 * Output: [1,2,3,null,null,null,4]
 */

class SolutionBuildTree {
    //DFS Optimal, TC O(n), SC O(n)
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        if (preorder.isEmpty()) return null
        val inorderMap = inorder.withIndex().associate { (i, v) -> v to i }
        var pre = preorder.indices.first
        fun buildDfs(left: Int, right: Int): TreeNode? {
            if (left > right) return null
            val root = TreeNode(preorder[pre++])
            val mid = inorderMap[root.`val`]!!
            root.left = buildDfs(left, mid - 1)
            root.right = buildDfs(mid + 1, right)
            return root
        }
        return buildDfs(inorder.indices.first, inorder.indices.last)
    }

    //DFS, TC: O(n^2), SC: O(n^2)
    fun buildTree1(preorder: IntArray, inorder: IntArray): TreeNode? {
        if (preorder.isEmpty() || inorder.isEmpty()) return null
        val root = TreeNode(preorder[0])
        val midIdx = inorder.indexOf(root.`val`)

        root.left = buildTree1(
            preorder.slice(1..midIdx).toIntArray(),
            inorder.slice(0 until midIdx).toIntArray()
        )

        root.right = buildTree1(
            preorder.slice(midIdx + 1 until preorder.size).toIntArray(),
            inorder.slice(midIdx + 1 until inorder.size).toIntArray()
        )

        return root
    }
}


// ---- Remove this when running on LeetCode (they provide TreeNode) ----
//class TreeNode(var `val`: Int) { var left: TreeNode? = null; var right: TreeNode? = null }
// ---------------------------------------------------------------------

class ConstructTreeFromPreInorderTest {

    private val sut = SolutionBuildTree()

    // --- Helpers ---
    private fun preorderList(root: TreeNode?): List<Int> {
        val out = mutableListOf<Int>()
        fun dfs(n: TreeNode?) {
            if (n == null) return
            out += n.`val`
            dfs(n.left)
            dfs(n.right)
        }
        dfs(root)
        return out
    }

    private fun inorderList(root: TreeNode?): List<Int> {
        val out = mutableListOf<Int>()
        fun dfs(n: TreeNode?) {
            if (n == null) return
            dfs(n.left)
            out += n.`val`
            dfs(n.right)
        }
        dfs(root)
        return out
    }

    private fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()
        val res = mutableListOf<List<Int>>()
        val q: ArrayDeque<TreeNode> = ArrayDeque()
        q.add(root)
        while (q.isNotEmpty()) {
            val size = q.size
            val level = ArrayList<Int>(size)
            repeat(size) {
                val n = q.removeFirst()
                level += n.`val`
                n.left?.let(q::add)
                n.right?.let(q::add)
            }
            res += level
        }
        return res
    }

    // --- Tests ---

    @Test
    fun empty_arrays_return_null() {
        val root = sut.buildTree(intArrayOf(), intArrayOf())
        assertEquals(emptyList<Int>(), preorderList(root))
        assertEquals(emptyList<Int>(), inorderList(root))
    }

    @Test
    fun single_node() {
        val pre = intArrayOf(1)
        val ino = intArrayOf(1)
        val root = sut.buildTree(pre, ino)
        assertEquals(listOf(1), preorderList(root))
        assertEquals(listOf(1), inorderList(root))
        assertEquals(listOf(listOf(1)), levelOrder(root))
    }

    @Test
    fun leetcode_example() {
        // preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
        val pre = intArrayOf(3, 9, 20, 15, 7)
        val ino = intArrayOf(9, 3, 15, 20, 7)
        val root = sut.buildTree(pre, ino)

        // Traversals must match inputs
        assertEquals(pre.toList(), preorderList(root))
        assertEquals(ino.toList(), inorderList(root))

        // Also check expected level order shape
        val expectedLevels = listOf(listOf(3), listOf(9, 20), listOf(15, 7))
        assertEquals(expectedLevels, levelOrder(root))
    }

    @Test
    fun left_skewed_tree() {
        // Tree:    3
        //         /
        //        2
        //       /
        //      1
        val pre = intArrayOf(3, 2, 1)
        val ino = intArrayOf(1, 2, 3)
        val root = sut.buildTree(pre, ino)
        assertEquals(pre.toList(), preorderList(root))
        assertEquals(ino.toList(), inorderList(root))
        assertEquals(listOf(listOf(3), listOf(2), listOf(1)), levelOrder(root))
    }

    @Test
    fun right_skewed_tree() {
        // Tree: 1
        //        \
        //         2
        //          \
        //           3
        val pre = intArrayOf(1, 2, 3)
        val ino = intArrayOf(1, 2, 3)
        val root = sut.buildTree(pre, ino)
        assertEquals(pre.toList(), preorderList(root))
        assertEquals(ino.toList(), inorderList(root))
        assertEquals(listOf(listOf(1), listOf(2), listOf(3)), levelOrder(root))
    }

    @Test
    fun balanced_three_nodes() {
        // Tree:   2
        //        / \
        //       1   3
        val pre = intArrayOf(2, 1, 3)
        val ino = intArrayOf(1, 2, 3)
        val root = sut.buildTree(pre, ino)
        assertEquals(pre.toList(), preorderList(root))
        assertEquals(ino.toList(), inorderList(root))
        assertEquals(listOf(listOf(2), listOf(1, 3)), levelOrder(root))
    }

    @Test
    fun larger_tree_seven_nodes() {
        // Tree:
        //         4
        //       /   \
        //      2     6
        //     / \   / \
        //    1   3 5   7
        val pre = intArrayOf(4, 2, 1, 3, 6, 5, 7)
        val ino = intArrayOf(1, 2, 3, 4, 5, 6, 7)
        val root = sut.buildTree(pre, ino)
        assertEquals(pre.toList(), preorderList(root))
        assertEquals(ino.toList(), inorderList(root))
        assertEquals(listOf(listOf(4), listOf(2, 6), listOf(1, 3, 5, 7)), levelOrder(root))
    }
}
