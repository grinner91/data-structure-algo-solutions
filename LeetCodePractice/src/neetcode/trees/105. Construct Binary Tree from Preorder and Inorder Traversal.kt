package neetcode.trees

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConstructBinaryTreeFromPreorderAndInorderTraversalDfs {
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        if (preorder.isEmpty() || inorder.isEmpty()) return null
        val root = TreeNode(preorder[0])
        val mid = inorder.indexOf(preorder[0])
        root.left = buildTree(
            preorder.slice(1..mid).toIntArray(),
            inorder.slice(0 until mid).toIntArray()
        )

        root.right = buildTree(
            preorder.slice(mid + 1 until preorder.size).toIntArray(),
            inorder.slice(mid + 1 until inorder.size).toIntArray()
        )
        return root
    }
}

class ConstructBinaryTreeFromPreorderAndInorderTraversalRecursiveOptimal {
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        val indices = inorder.withIndex().associate { (i, v) -> v to i }
        var preIdx = 0
        fun dfs(l: Int, r: Int): TreeNode? {
            if (l > r) return null
            val value = preorder[preIdx++]
            val root = TreeNode(value)
            val mid = indices[value]!!
            root.left = dfs(l, mid - 1)
            root.right = dfs(mid + 1, r)
            return root
        }
        return dfs(0, inorder.lastIndex)
    }
}

class ConstructBinaryTreeFromPreorderAndInorderTraversalTest {

    private val impls = listOf(
//        ConstructBinaryTreeFromPreorderAndInorderTraversalDfs()::buildTree,
        ConstructBinaryTreeFromPreorderAndInorderTraversalRecursiveOptimal()::buildTree
    )

    @Test
    fun example1() {
        val preorder = intArrayOf(3, 9, 20, 15, 7)
        val inorder = intArrayOf(9, 3, 15, 20, 7)

        impls.forEach { buildTree ->
            val root = buildTree(preorder, inorder)

            assertArrayEquals(preorder, preorderTraversal(root).toIntArray())
            assertArrayEquals(inorder, inorderTraversal(root).toIntArray())
            assertEquals(
                listOf(3, 9, 20, null, null, 15, 7),
                levelOrderWithNulls(root)
            )
        }
    }

    @Test
    fun singleNode() {
        val preorder = intArrayOf(1)
        val inorder = intArrayOf(1)

        impls.forEach { buildTree ->
            val root = buildTree(preorder, inorder)

            assertArrayEquals(preorder, preorderTraversal(root).toIntArray())
            assertArrayEquals(inorder, inorderTraversal(root).toIntArray())
            assertEquals(listOf(1), levelOrderWithNulls(root))
        }
    }

    @Test
    fun leftSkewedTree() {
        val preorder = intArrayOf(4, 3, 2, 1)
        val inorder = intArrayOf(1, 2, 3, 4)

        impls.forEach { buildTree ->
            val root = buildTree(preorder, inorder)

            assertArrayEquals(preorder, preorderTraversal(root).toIntArray())
            assertArrayEquals(inorder, inorderTraversal(root).toIntArray())
            assertEquals(
                listOf(4, 3, null, 2, null, 1),
                levelOrderWithNulls(root)
            )
        }
    }

    @Test
    fun rightSkewedTree() {
        val preorder = intArrayOf(1, 2, 3, 4)
        val inorder = intArrayOf(1, 2, 3, 4)

        impls.forEach { buildTree ->
            val root = buildTree(preorder, inorder)

            assertArrayEquals(preorder, preorderTraversal(root).toIntArray())
            assertArrayEquals(inorder, inorderTraversal(root).toIntArray())
            assertEquals(
                listOf(1, null, 2, null, 3, null, 4),
                levelOrderWithNulls(root)
            )
        }
    }

    @Test
    fun balancedTree() {
        val preorder = intArrayOf(8, 4, 2, 6, 12, 10, 14)
        val inorder = intArrayOf(2, 4, 6, 8, 10, 12, 14)

        impls.forEach { buildTree ->
            val root = buildTree(preorder, inorder)

            assertArrayEquals(preorder, preorderTraversal(root).toIntArray())
            assertArrayEquals(inorder, inorderTraversal(root).toIntArray())
            assertEquals(
                listOf(8, 4, 12, 2, 6, 10, 14),
                levelOrderWithNulls(root)
            )
        }
    }

    @Test
    fun emptyTree() {
        val preorder = intArrayOf()
        val inorder = intArrayOf()

        impls.forEach { buildTree ->
            val root = buildTree(preorder, inorder)
            assertNull(root)
        }
    }

    private fun preorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()

        fun dfs(node: TreeNode?) {
            if (node == null) return
            result.add(node.`val`)
            dfs(node.left)
            dfs(node.right)
        }

        dfs(root)
        return result
    }

    private fun inorderTraversal(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()

        fun dfs(node: TreeNode?) {
            if (node == null) return
            dfs(node.left)
            result.add(node.`val`)
            dfs(node.right)
        }

        dfs(root)
        return result
    }

    private fun levelOrderWithNulls(root: TreeNode?): List<Int?> {
        if (root == null) return emptyList()

        val result = mutableListOf<Int?>()
        val queue = ArrayDeque<TreeNode?>()
        queue.addLast(root)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            if (node == null) {
                result.add(null)
            } else {
                result.add(node.`val`)
                queue.addLast(node.left)
                queue.addLast(node.right)
            }
        }

        while (result.isNotEmpty() && result.last() == null) {
            result.removeAt(result.lastIndex)
        }

        return result
    }
}