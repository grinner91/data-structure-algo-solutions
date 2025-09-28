package neetcode.trees

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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

object ValidateBSTBounds {

    fun isValidBST1(root: TreeNode?): Boolean {
        root ?: return true
        return isValidBSTDfs(root, Long.MIN_VALUE, Long.MAX_VALUE)
    }

    //DFS
    private fun isValidBSTDfs(root: TreeNode?, low: Long, high: Long): Boolean {
        root ?: return true

        if (root.`val` <= low || root.`val` >= high)
            return false

        return isValidBSTDfs(root.left, low, root.`val`.toLong())
                && isValidBSTDfs(root.right, root.`val`.toLong(), high)
    }

    //BFS
    fun isValidBST(root: TreeNode?): Boolean {
        root ?: return true
        data class QueItem(val node: TreeNode, val low: Long, val high: Long)

        val que = ArrayDeque<QueItem>()
        que.addLast(QueItem(node = root, low = Long.MIN_VALUE, high = Long.MAX_VALUE))
        while (que.isNotEmpty()) {
            val (node, low, high) = que.removeFirst()
            val value = node.`val`.toLong()
            if (value <= low || value >= high)
                return false
            node.left?.let {
                que.addLast(QueItem(it, low, value))
            }
            node.right?.let {
                que.addLast(QueItem(it, value, high))
            }
        }
        return true
    }


}

// ---------------------------------------------------------------------------

/**
 * Build a binary tree from level-order values where null means “no node”.
 * Example: buildLevelOrder(5, 1, 4, null, null, 3, 6)
 */
fun buildLevelOrder(vararg vals: Int?): TreeNode? {
    if (vals.isEmpty() || vals[0] == null) return null
    val root = TreeNode(vals[0]!!)
    val q: ArrayDeque<TreeNode> = ArrayDeque()
    q.add(root)
    var i = 1
    while (i < vals.size && q.isNotEmpty()) {
        val node = q.removeFirst()

        if (i < vals.size && vals[i] != null) {
            node.left = TreeNode(vals[i]!!)
            q.add(node.left!!)
        }
        i++

        if (i < vals.size && vals[i] != null) {
            node.right = TreeNode(vals[i]!!)
            q.add(node.right!!)
        }
        i++
    }
    return root
}

class ValidateBSTBoundsTest {

    @Test
    fun `empty tree is valid`() {
        val root: TreeNode? = null
        assertTrue(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `single node is valid`() {
        val root = TreeNode(42)
        assertTrue(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `simple valid tree`() {
        val root = buildLevelOrder(2, 1, 3)
        assertTrue(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `invalid due to right subtree violation`() {
        // [5,1,4,null,null,3,6] -> invalid because 3 is in right subtree of 5
        val root = buildLevelOrder(5, 1, 4, null, null, 3, 6)
        assertFalse(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `invalid due to left subtree violation deep`() {
        //     10
        //    /  \
        //   5    15
        //       /  \
        //      6    20   (6 violates > 10)
        val root = buildLevelOrder(10, 5, 15, null, null, 6, 20)
        assertFalse(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `duplicates on left not allowed`() {
        // BST requires strict ordering: left < node < right
        val root = buildLevelOrder(2, 2, null)
        assertFalse(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `duplicates on right not allowed`() {
        val root = buildLevelOrder(2, null, 2)
        assertFalse(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `handles Int MIN and MAX correctly`() {
        val root = TreeNode(Int.MIN_VALUE).apply {
            right = TreeNode(Int.MAX_VALUE)
        }
        assertTrue(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `valid skewed increasing chain`() {
        // 1 -> 2 -> 3 -> 4 (right-skewed)
        val root = buildLevelOrder(1, null, 2, null, 3, null, 4)
        assertTrue(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `invalid when a deep node breaks upper bound`() {
        //        8
        //      /   \
        //     3     10
        //    / \      \
        //   1   6      14
        //      / \     /
        //     4  13   9   <- 9 is in right subtree of 8 but < 10 branch; still should be > 10? No—placed under 14 left, but 9 < 10 violates bound from root's right side
        val root = TreeNode(8).apply {
            left = TreeNode(3).apply {
                left = TreeNode(1)
                right = TreeNode(6).apply {
                    left = TreeNode(4)
                    right = TreeNode(13)
                }
            }
            right = TreeNode(10).apply {
                right = TreeNode(14).apply {
                    left = TreeNode(9) // violates (>10 && <14) → 9 <= 10
                }
            }
        }
        assertFalse(ValidateBSTBounds.isValidBST(root))
    }

    @Test
    fun `valid complex tree`() {
        //        8
        //      /   \
        //     3     10
        //    / \      \
        //   1   6      14
        //      / \     /
        //     4   7   11
        val root = TreeNode(8).apply {
            left = TreeNode(3).apply {
                left = TreeNode(1)
                right = TreeNode(6).apply {
                    left = TreeNode(4)
                    right = TreeNode(7)
                }
            }
            right = TreeNode(10).apply {
                right = TreeNode(14).apply { left = TreeNode(11) }
            }
        }
        assertTrue(ValidateBSTBounds.isValidBST(root))
    }
}