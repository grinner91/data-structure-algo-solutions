package neetcode.trees

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

data class TreeNode(
    var `val`: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

class SameTreeRecursiveDfs {
    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) return true
        if (p == null || q == null) return false
        if (p.`val` != q.`val`) return false
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
    }
}

class SameTreeIterativeDfs {
    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) return true
        if (p == null || q == null) return false

        val stack = ArrayDeque<Pair<TreeNode?, TreeNode?>>()
        stack.addLast(p to q)

        while (stack.isNotEmpty()) {
            val (a, b) = stack.removeLast()
            if (a == null || b == null) {
                if (a != b) return false
                continue
            }
            if (a.`val` != b.`val`) return false

            stack.addLast(a.left to b.left)
            stack.addLast(a.right to b.right)
        }
        return true
    }
}

class SameTreeTest {

    private val impls = listOf(
        SameTreeIterativeDfs()::isSameTree,
    )

    @Test
    fun `both trees null`() {
        impls.forEach { f ->
            assertTrue(f(null, null))
        }
    }

    @Test
    fun `one tree null`() {
        val root = node(1)

        impls.forEach { f ->
            assertFalse(f(root, null))
            assertFalse(f(null, root))
        }
    }

    @Test
    fun `single node same value`() {
        val p = node(1)
        val q = node(1)

        impls.forEach { f ->
            assertTrue(f(p, q))
        }
    }

    @Test
    fun `single node different value`() {
        val p = node(1)
        val q = node(2)

        impls.forEach { f ->
            assertFalse(f(p, q))
        }
    }

    @Test
    fun `same structure same values`() {
        val p = tree(1, 2, 3)
        val q = tree(1, 2, 3)

        impls.forEach { f ->
            assertTrue(f(p, q))
        }
    }

    @Test
    fun `same values different structure`() {
        val p = tree(1, 2, null)
        val q = tree(1, null, 2)

        impls.forEach { f ->
            assertFalse(f(p, q))
        }
    }

    @Test
    fun `different values same structure`() {
        val p = tree(1, 2, 3)
        val q = tree(1, 2, 4)

        impls.forEach { f ->
            assertFalse(f(p, q))
        }
    }

    @Test
    fun `deep identical trees`() {
        val p = tree(1, 2, 3, 4, 5, 6, 7)
        val q = tree(1, 2, 3, 4, 5, 6, 7)

        impls.forEach { f ->
            assertTrue(f(p, q))
        }
    }

    @Test
    fun `deep trees differ at leaf`() {
        val p = tree(1, 2, 3, 4, 5, 6, 7)
        val q = tree(1, 2, 3, 4, 5, 6, 8)

        impls.forEach { f ->
            assertFalse(f(p, q))
        }
    }

    @Test
    fun `asymmetric trees`() {
        val p = tree(1, 2, 3, null, 4)
        val q = tree(1, 2, 3, 4, null)

        impls.forEach { f ->
            assertFalse(f(p, q))
        }
    }

    private fun node(value: Int, left: TreeNode? = null, right: TreeNode? = null) =
        TreeNode(value, left, right)

    /**
     * Build tree from level order values
     */
    private fun tree(vararg values: Int?): TreeNode? {
        if (values.isEmpty() || values[0] == null) return null

        val root = TreeNode(values[0]!!)
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)

        var i = 1

        while (i < values.size && queue.isNotEmpty()) {
            val curr = queue.removeFirst()

            if (i < values.size && values[i] != null) {
                curr.left = TreeNode(values[i]!!)
                queue.addLast(curr.left!!)
            }
            i++

            if (i < values.size && values[i] != null) {
                curr.right = TreeNode(values[i]!!)
                queue.addLast(curr.right!!)
            }
            i++
        }

        return root
    }
}