package neetcode.trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

object KthSmallestInBST {

    //optimal iterative DFS
    fun kthSmallest(root: TreeNode?, k: Int): Int {
        val stack = ArrayDeque<TreeNode>()
        var cur = root
        var count = k
        while (cur != null || stack.isNotEmpty()) {
            //left subtree traversal
            while (cur != null) {
                stack.addLast(cur)
                cur = cur.left
            }
            val node = stack.removeLast()
            count--
            if (count == 0) return node.`val`
            //right subtree traversal
            cur = node.right
        }
        return -1
    }

    /************************************************/
    //DFS brute force
    private val elements = mutableListOf<Int>()
    fun kthSmallest1(root: TreeNode?, k: Int): Int {
        inorderTraversal(root)
        return elements[k - 1]
    }

    //DFS
    private fun inorderTraversal(root: TreeNode?) {
        root ?: return
        inorderTraversal(root.left)
        elements.add(root.`val`)
        inorderTraversal(root.right)
    }
}

class KthSmallestInBSTTest {
// ===== Test helpers =====
    /** Build a tree from level-order values where null means “no node”. */
    fun buildLevelOrder(vararg vals: Int?): TreeNode? {
        if (vals.isEmpty() || vals[0] == null) return null
        val root = TreeNode(vals[0]!!)
        val q = ArrayDeque<TreeNode>()
        q.addLast(root)
        var i = 1
        while (i < vals.size && q.isNotEmpty()) {
            val n = q.removeFirst()

            if (i < vals.size && vals[i] != null) {
                n.left = TreeNode(vals[i]!!)
                q.addLast(n.left!!)
            }
            i++

            if (i < vals.size && vals[i] != null) {
                n.right = TreeNode(vals[i]!!)
                q.addLast(n.right!!)
            }
            i++
        }
        return root
    }

    @Test
    fun `example small tree`() {
        //    3
        //   / \
        //  1   4
        //   \
        //    2
        val root = buildLevelOrder(3, 1, 4, null, 2)
        assertEquals(1, KthSmallestInBST.kthSmallest(root, 1))
        assertEquals(2, KthSmallestInBST.kthSmallest(root, 2))
        assertEquals(3, KthSmallestInBST.kthSmallest(root, 3))
        assertEquals(4, KthSmallestInBST.kthSmallest(root, 4))
    }

    @Test
    fun `right-skewed increasing`() {
        // 1 -> 2 -> 3 -> 4 -> 5
        val root = buildLevelOrder(1, null, 2, null, 3, null, 4, null, 5)
        assertEquals(1, KthSmallestInBST.kthSmallest(root, 1))
        assertEquals(3, KthSmallestInBST.kthSmallest(root, 3))
        assertEquals(5, KthSmallestInBST.kthSmallest(root, 5))

    }

    @Test
    fun `balanced BST typical`() {
        //        5
        //      /   \
        //     3     7
        //    / \   / \
        //   2  4  6   8
        val root = buildLevelOrder(5, 3, 7, 2, 4, 6, 8)
        val inorder = listOf(2, 3, 4, 5, 6, 7, 8)
        for (k in 1..inorder.size) {
            assertEquals(inorder[k - 1], KthSmallestInBST.kthSmallest(root, k))
        }
    }

    @Test
    fun `single node`() {
        val root = TreeNode(42)
        assertEquals(42, KthSmallestInBST.kthSmallest(root, 1))

    }

    @Test
    fun `invalid k throws`() {
        val root = buildLevelOrder(2, 1, 3)
        assertThrows(IllegalArgumentException::class.java) {
            KthSmallestInBST.kthSmallest(root, 0)
        }

        assertThrows(IllegalArgumentException::class.java) {
            KthSmallestInBST.kthSmallest(root, 4) // n = 3
        }

    }

    @Test
    fun `handles extreme Int values`() {
        // (-2^31) < ... < (2^31 - 1)
        val root = TreeNode(Int.MIN_VALUE).apply {
            right = TreeNode(0).apply { right = TreeNode(Int.MAX_VALUE) }
        }
        assertEquals(Int.MIN_VALUE, KthSmallestInBST.kthSmallest(root, 1))
        assertEquals(0, KthSmallestInBST.kthSmallest(root, 2))
        assertEquals(Int.MAX_VALUE, KthSmallestInBST.kthSmallest(root, 3))

    }
}