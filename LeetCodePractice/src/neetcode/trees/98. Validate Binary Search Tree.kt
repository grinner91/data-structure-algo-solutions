package neetcode.trees

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ValidateBinarySearchRecursiveDfs {
    fun isValidBST(root: TreeNode?): Boolean {
        fun isValidDfs(node: TreeNode?, lower: Long, upper: Long): Boolean {
            if (node == null) return true
            val value = node.`val`.toLong()
            if (value <= lower || value >= upper) return false

            return isValidDfs(node.left, lower, value)
                    && isValidDfs(node.right, value, upper)

        }
        return isValidDfs(root, Long.MIN_VALUE, Long.MAX_VALUE)
    }
}

class ValidateBinarySearchIterativeInOrder {
    fun isValidBST(root: TreeNode?): Boolean {
        if (root == null) return true
        val inorder = mutableListOf<Int>()
        fun dfs(node: TreeNode?) {
            if (node == null) return
            dfs(node.left)
            inorder.add(node.`val`)
            dfs(node.right)
        }
        dfs(root)
        for (i in 1 until inorder.size) {
            if (inorder[i] <= inorder[i - 1]) return false
        }
        return true
    }
}

class ValidateBinarySearchBfs {
    data class State(val node: TreeNode, val lower: Int?, val upper: Int?)

    fun isValidBST(root: TreeNode?): Boolean {
        if (root == null) return true

        val que = ArrayDeque<State>()
        que.add(State(root, null, null))
        while (que.isNotEmpty()) {
            val (cur, lower, upper) = que.removeFirst()
            val value = cur.`val`

            if (lower != null && value <= lower) return false
            if (upper != null && value >= upper) return false

            cur.left?.let { que.addLast(State(it, lower, value)) }
            cur.right?.let { que.addLast(State(it, value, upper)) }
        }
        return true
    }
}

class ValidateBinarySearchTreeTest {

    private val impls = listOf(
//        ValidateBinarySearchRecursiveDfs()::isValidBST,
//        ValidateBinarySearchIterativeInOrder()::isValidBST,
        ValidateBinarySearchBfs()::isValidBST
    )

    @Test
    fun validBst_simple() {
        val root = node(
            2,
            node(1),
            node(3),
        )

        impls.forEach { isValidBST ->
            assertTrue(isValidBST(root))
        }
    }

    @Test
    fun invalidBst_immediateChildViolation() {
        val root = node(
            5,
            node(1),
            node(
                4,
                node(3),
                node(6),
            ),
        )

        impls.forEach { isValidBST ->
            assertFalse(isValidBST(root))
        }
    }

    @Test
    fun invalidBst_deepViolationInLeftSubtree() {
        val root = node(
            10,
            node(
                5,
                node(2),
                node(12),
            ),
            node(15),
        )

        impls.forEach { isValidBST ->
            assertFalse(isValidBST(root))
        }
    }

    @Test
    fun invalidBst_deepViolationInRightSubtree() {
        val root = node(
            10,
            node(5),
            node(
                15,
                node(6),
                node(20),
            ),
        )

        impls.forEach { isValidBST ->
            assertFalse(isValidBST(root))
        }
    }

    @Test
    fun invalidBst_duplicateValuesNotAllowed() {
        val root = node(
            2,
            node(2),
            node(3),
        )

        impls.forEach { isValidBST ->
            assertFalse(isValidBST(root))
        }
    }

    @Test
    fun validBst_withIntBoundaries() {
        val root = node(
            0,
            node(Int.MIN_VALUE),
            node(Int.MAX_VALUE),
        )

        impls.forEach { isValidBST ->
            assertTrue(isValidBST(root))
        }
    }

    @Test
    fun singleNode_isValid() {
        val root = node(1)

        impls.forEach { isValidBST ->
            assertTrue(isValidBST(root))
        }
    }

    @Test
    fun nullTree_isValid() {
        impls.forEach { isValidBST ->
            assertTrue(isValidBST(null))
        }
    }

    private fun node(
        value: Int,
        left: TreeNode? = null,
        right: TreeNode? = null,
    ): TreeNode {
        return TreeNode(value, left, right)
    }
}