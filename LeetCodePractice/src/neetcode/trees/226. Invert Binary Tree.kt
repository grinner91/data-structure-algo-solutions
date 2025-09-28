package neetcode.trees

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

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class SolutionInvertTree {
    //DFS
    fun invertTree1(root: TreeNode?): TreeNode? {
        if (root == null) return null
        val left = root.left
        root.left = root.right
        root.right = left
        invertTree(root.left)
        invertTree(root.right)
        return root
    }

    //BFS
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return root
        val deq: ArrayDeque<TreeNode?> = ArrayDeque()
        deq.addLast(root)
        while (deq.isNotEmpty()) {
            val node = deq.removeFirst()
            node?.let {
                val temp = it.left
                it.left = it.right
                it.left = temp
                deq.addLast(it.left)
                deq.addLast(it.right)
            }
        }
        return root
    }
}