package neetcode.trees

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
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
class SolutionIsSubtree {
    fun isSubtree(root: TreeNode?, subRoot: TreeNode?): Boolean {
        if (subRoot == null) return true
        if (root == null) return false
        if (isSameTree(root, subRoot)) return true
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot)
    }

    private fun isSameTree(root: TreeNode?, subRoot: TreeNode?): Boolean {
        if (root == null && subRoot == null) return true
        if (root != null && subRoot != null && root.`val` == subRoot.`val`) {
            return isSameTree(root.left, subRoot.left) && isSameTree(root.right, subRoot.right)
        }
        return false
    }

}


// ---- Remove this when running on LeetCode (they provide TreeNode) ----
//class TreeNode(var `val`: Int) { var left: TreeNode? = null; var right: TreeNode? = null }
// ---------------------------------------------------------------------

class SubtreeOfAnotherTreeTests {

    private val dfs = SolutionIsSubtree()

    // Build a binary tree from level-order values; nulls allowed (e.g., 3,4,5,1,2,null,null)
    private fun buildLevelOrder(vararg vals: Int?): TreeNode? {
        if (vals.isEmpty() || vals[0] == null) return null
        val root = TreeNode(vals[0]!!)
        val q: ArrayDeque<TreeNode> = ArrayDeque()
        q.add(root)
        var i = 1
        while (q.isNotEmpty() && i < vals.size) {
            val node = q.removeFirst()
            if (i < vals.size) {
                vals[i]?.let {
                    node.left = TreeNode(it);
                    q.add(node.left!!)
                }
                i++
            }
            if (i < vals.size) {
                vals[i]?.let {
                    node.right = TreeNode(it);
                    q.add(node.right!!)
                }
                i++
            }
        }
        return root
    }

    // ----- LeetCode examples -----
    @Test
    fun example_true() {
        val root = buildLevelOrder(3, 4, 5, 1, 2)
        val sub = buildLevelOrder(4, 1, 2)
        assertTrue(dfs.isSubtree(root, sub))
    }

    @Test
    fun example_false_due_to_extra_node() {
        val root = buildLevelOrder(3, 4, 5, 1, 2, null, null, null, null, 0)
        val sub = buildLevelOrder(4, 1, 2)
        assertFalse(dfs.isSubtree(root, sub))
    }

    // ----- Additional coverage -----
    @Test
    fun identical_trees_true() {
        val root = buildLevelOrder(1, 2, 3, null, 4)
        val sub = buildLevelOrder(1, 2, 3, null, 4)
        assertTrue(dfs.isSubtree(root, sub))
    }

    @Test
    fun sub_is_single_node_present_true() {
        val root = buildLevelOrder(2, 1, 3)
        val sub = buildLevelOrder(3)
        assertTrue(dfs.isSubtree(root, sub))
    }

    @Test
    fun sub_is_single_node_absent_false() {
        val root = buildLevelOrder(2, 1, 3)
        val sub = buildLevelOrder(4)
        assertFalse(dfs.isSubtree(root, sub))
    }

    @Test
    fun root_null_sub_null_true_by_definition() {
        val root: TreeNode? = null
        val sub: TreeNode? = null
        assertTrue(dfs.isSubtree(root, sub))
    }

    @Test
    fun root_null_sub_nonnull_false() {
        val root: TreeNode? = null
        val sub = buildLevelOrder(1)
        assertFalse(dfs.isSubtree(root, sub))
    }

    @Test
    fun duplicates_require_structure_match() {
        // root:     1
        //         /   \
        //        1     1
        //       /
        //      1
        val root = buildLevelOrder(1, 1, 1, 1, null, null, null)
        // sub must match structure: 1 with left child 1
        val subGood = buildLevelOrder(1, 1, null)
        val subBad = buildLevelOrder(1, null, 1) // different shape
        assertTrue(dfs.isSubtree(root, subGood))
        assertFalse(dfs.isSubtree(root, subBad))
    }
}


