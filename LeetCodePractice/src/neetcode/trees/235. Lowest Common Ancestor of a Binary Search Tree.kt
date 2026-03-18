package neetcode.trees

/**
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int = 0) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class LowestCommonAncestorRecursive {
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || p == null || q == null) return null

        return when {
            maxOf(p.`val`, q.`val`) < root.`val` -> lowestCommonAncestor(root.left, p, q)
            minOf(p.`val`, q.`val`) > root.`val` -> lowestCommonAncestor(root.right, p, q)
            else -> root
        }
    }
}

class LowestCommonAncestorIterative {
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || p == null || q == null) return null
        var cur = root
        while (cur != null) {
            cur = when {
                p.`val` < cur.`val` && q.`val` < cur.`val` -> cur.left
                p.`val` > cur.`val` && q.`val` > cur.`val` -> cur.right
                else -> return cur
            }
        }
        return null
    }
}


class LowestCommonAncestorBstTest {

    private val impls = listOf(
        LowestCommonAncestorRecursive()::lowestCommonAncestor,
    )

    @Test
    fun example1() {
        val root = bst(
            6,
            2, 8,
            0, 4, 7, 9,
            null, null, 3, 5
        )

        val p = find(root, 2)
        val q = find(root, 8)

        impls.forEach { f ->
            assertEquals(6, f(root, p, q)?.`val`)
        }
    }

    @Test
    fun example2() {
        val root = bst(
            6,
            2, 8,
            0, 4, 7, 9,
            null, null, 3, 5
        )

        val p = find(root, 2)
        val q = find(root, 4)

        impls.forEach { f ->
            assertEquals(2, f(root, p, q)?.`val`)
        }
    }

    @Test
    fun singleNodeTree() {
        val root = bst(2)
        val p = find(root, 2)
        val q = find(root, 2)

        impls.forEach { f ->
            assertEquals(2, f(root, p, q)?.`val`)
        }
    }

    @Test
    fun rootIsLcaWhenNodesSplitAtRoot() {
        val root = bst(10, 5, 15, 3, 7, 12, 18)
        val p = find(root, 3)
        val q = find(root, 12)

        impls.forEach { f ->
            assertEquals(10, f(root, p, q)?.`val`)
        }
    }

    @Test
    fun oneNodeIsAncestorOfOther() {
        val root = bst(10, 5, 15, 3, 7, 12, 18, 1, 4)
        val p = find(root, 5)
        val q = find(root, 4)

        impls.forEach { f ->
            assertEquals(5, f(root, p, q)?.`val`)
        }
    }

    @Test
    fun bothNodesInLeftSubtree() {
        val root = bst(20, 10, 30, 5, 15, 25, 35, 3, 7, 13, 17)
        val p = find(root, 13)
        val q = find(root, 17)

        impls.forEach { f ->
            assertEquals(15, f(root, p, q)?.`val`)
        }
    }

    @Test
    fun bothNodesInRightSubtree() {
        val root = bst(20, 10, 30, 5, 15, 25, 35, null, null, null, null, 22, 27)
        val p = find(root, 22)
        val q = find(root, 27)

        impls.forEach { f ->
            assertEquals(25, f(root, p, q)?.`val`)
        }
    }

    @Test
    fun nullRootReturnsNull() {
        impls.forEach { f ->
            assertNull(f(null, TreeNode(1), TreeNode(2)))
        }
    }

    @Test
    fun nullPReturnsNull() {
        val root = bst(2, 1, 3)

        impls.forEach { f ->
            assertNull(f(root, null, find(root, 3)))
        }
    }

    @Test
    fun nullQReturnsNull() {
        val root = bst(2, 1, 3)

        impls.forEach { f ->
            assertNull(f(root, find(root, 1), null))
        }
    }

    private fun bst(vararg values: Int?): TreeNode? {
        if (values.isEmpty() || values[0] == null) return null

        val nodes = values.map { it?.let(::TreeNode) }

        for (i in nodes.indices) {
            val node = nodes[i] ?: continue
            val leftIndex = 2 * i + 1
            val rightIndex = 2 * i + 2

            if (leftIndex < nodes.size) node.left = nodes[leftIndex]
            if (rightIndex < nodes.size) node.right = nodes[rightIndex]
        }

        return nodes[0]
    }

    private fun find(root: TreeNode?, target: Int): TreeNode? {
        var curr = root

        while (curr != null) {
            curr = when {
                target < curr.`val` -> curr.left
                target > curr.`val` -> curr.right
                else -> return curr
            }
        }

        return null
    }
}