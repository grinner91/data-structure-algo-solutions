package neetcode.trees

class SolutionMaxDepth {
    //DFS recursion
//    fun maxDepth(root: TreeNode?): Int {
//        if (root ==null) return  0
//        val dl = 1 + maxDepth(root.left)
//        val dr = 1 + maxDepth(root.right)
//
//        return maxOf(dl, dr)
//    }

    //DFS Stack / Iterative
    fun maxDepth2(root: TreeNode?): Int {
        if (root == null) return 0
        val stack: ArrayDeque<Pair<TreeNode?, Int>> = ArrayDeque()
        stack.addLast(Pair(root, 1))
        var res = 0
        while (stack.isNotEmpty()) {
            val (node, depth) = stack.removeLast()
            if (node != null) {
                res = maxOf(res, depth)
                stack.addLast(Pair(node.left, depth + 1))
                stack.addLast(Pair(node.right, depth + 1))
            }
        }
        return res
    }

    //BFS
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        val deq = ArrayDeque<TreeNode>()
        deq.addLast(root)
        var level = 0
        while (deq.isNotEmpty()) {
            val size = deq.size
            repeat(size) {
                val node = deq.removeFirst()
                node.left?.let { deq.addLast(it) }
                node.right?.let { deq.addLast(it) }
            }
            level++
        }
        return level
    }
}
