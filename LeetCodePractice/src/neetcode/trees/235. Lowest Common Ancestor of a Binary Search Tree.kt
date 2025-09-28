package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

/**
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int = 0) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */

class SolutionLowestCommonAncestor {
    //iterative solution
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || p == null || q == null) return null
        val maxPq = maxOf(p.`val`, q.`val`)
        val minPq = minOf(p.`val`, q.`val`)

        var cur = root
        while (cur != null) {
            cur = when {
                maxPq < cur.`val` -> cur.left
                minPq > cur.`val` -> cur.right
                else -> return cur
            }
        }
        return null
    }

//    //DFS recursion
//    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
//        if (root == null || p == null || q == null) return null
//        return when {
//            maxOf(p.`val`, q.`val`) < root.`val` -> {
//                lowestCommonAncestor(root.left, p, q)
//            }
//
//            minOf(p.`val`, q.`val`) > root.`val` -> {
//                lowestCommonAncestor(root.right, p, q)
//            }
//
//            else -> root
//        }
//    }


}


// ---- Remove this when running on LeetCode (they provide TreeNode) ----
//class TreeNode(var `val`: Int) { var left: TreeNode? = null; var right: TreeNode? = null }
// ---------------------------------------------------------------------

class LowestCommonAncestorBSTTest {

    private val sut = SolutionLowestCommonAncestor()

    // Build BST from level-order with nulls (use LeetCode-style arrays)
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

    // Find node by value using BST property (values are unique in these tests)
    private fun find(root: TreeNode?, target: Int): TreeNode? {
        var cur = root
        while (cur != null) {
            cur = when {
                target == cur.`val` -> return cur
                target < cur.`val` -> cur.left
                else -> cur.right
            }
        }
        return null
    }

    private fun assertLcaVal(root: TreeNode?, pVal: Int, qVal: Int, expectedVal: Int) {
        val p = find(root, pVal)!!
        val q = find(root, qVal)!!
        val lca = sut.lowestCommonAncestor(root, p, q)
        assertEquals(expectedVal, lca?.`val`)
    }

    @Test
    fun example_1_root_is_lca() {
        // root = [6,2,8,0,4,7,9,null,null,3,5], p=2, q=8 -> LCA=6
        val root = buildLevelOrder(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5)
        assertLcaVal(root, 2, 8, 6)
    }

    @Test
    fun example_2_one_is_ancestor_of_other() {
        // same tree, p=2, q=4 -> LCA=2
        val root = buildLevelOrder(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5)
        assertLcaVal(root, 2, 4, 2)
    }

    @Test
    fun example_3_two_nodes_in_small_bst() {
        // root = [2,1], p=2, q=1 -> LCA=2
        val root = buildLevelOrder(2, 1)
        assertLcaVal(root, 2, 1, 2)
    }

    @Test
    fun both_on_left_subtree_lca_not_root() {
        val root = buildLevelOrder(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5)
        // p=0, q=5 -> LCA=2
        assertLcaVal(root, 0, 5, 2)
    }

    @Test
    fun same_node_as_both_inputs() {
        val root = buildLevelOrder(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5)
        assertLcaVal(root, 4, 4, 4)
    }

    @Test
    fun root_is_one_of_nodes() {
        val root = buildLevelOrder(6, 2, 8)
        assertLcaVal(root, 6, 8, 6)
    }
}


