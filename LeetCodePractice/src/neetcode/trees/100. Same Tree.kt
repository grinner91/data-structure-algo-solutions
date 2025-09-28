package neetcode.trees

class SolutionIsSameTree {
//    //DFS
//    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
//        if (p == null && q == null) return true
//        if (p != null && q != null && p.`val` == q.`val`) {
//            val left = isSameTree(p.left, q.left)
//            val right = isSameTree(p.right, q.right)
//            return left && right
//        }
//        return false
//    }

    //iterative DFS
//    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
//        val deq = ArrayDeque<Pair<TreeNode?, TreeNode?>>()
//        deq.addLast(Pair(p, q))
//        while (deq.isNotEmpty()) {
//            val (n1, n2) = deq.removeFirst()
//            if (n1 == null && n2 == null) continue
//            if (n1 == null || n2 == null || n1.`val` != n2.`val`) {
//                return false
//            }
//            deq.addLast(Pair(n1.left, n2.left))
//            deq.addLast(Pair(n1.right, n2.right))
//        }
//        return true
//    }

    //BFS traverse
    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        val q1 = ArrayDeque<TreeNode?>()
        val q2 = ArrayDeque<TreeNode?>()
        q1.addLast(p)
        q2.addLast(q)

        while (q1.isNotEmpty() && q2.isNotEmpty()) {
            for (i in q1.size downTo 1) {
                val n1 = q1.removeFirst()
                val n2 = q2.removeFirst()
                if(n1 == null || n2 == null || n1.`val` != n2.`val`) {
                    return false
                }
                q1.addLast(n1.left)
                q1.addLast(n1.right)
                q2.addLast(n2.left)
                q2.addLast(n2.right)
            }
        }
        return true
    }
}
