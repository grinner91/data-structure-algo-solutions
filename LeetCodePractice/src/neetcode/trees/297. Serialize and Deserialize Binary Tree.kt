package neetcode.trees

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.random.Random

/**
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */

//BFS
class Codec() {
    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        root ?: return ""
        val res = mutableListOf<String>()
        val que: ArrayDeque<TreeNode?> = ArrayDeque()

        que.addLast(root)
        while (que.isNotEmpty()) {
            val cur = que.removeFirst()
            if (cur == null) {
                res.add("N")
            } else {
                res.add(cur.`val`.toString())
                que.addLast(cur.left)
                que.addLast(cur.right)
            }
        }
        return res.joinToString(",")
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        val tokens = data.split(",").filter { it.isNotEmpty() }.iterator()
        if (tokens.hasNext().not()) return null

        val que: ArrayDeque<TreeNode?> = ArrayDeque()
        val root = TreeNode(tokens.next().toInt())
        que.addLast(root)
        while (que.isNotEmpty() && tokens.hasNext()) {
            val cur = que.removeFirst()
            //left child
            if (tokens.hasNext()) {
                val v = tokens.next()
                if (v != "N") {
                    cur?.left = TreeNode(v.toInt())
                    que.addLast(cur?.left)
                }
            }
            //right child
            if (tokens.hasNext()) {
                val v = tokens.next()
                if (v != "N") {
                    cur?.right = TreeNode(v.toInt())
                    que.addLast(cur?.right)
                }
            }
        }
        return root
    }
}

//DFS
class CodecDFS() {
    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        val res = mutableListOf<String>()
        fun dfs(node: TreeNode?) {
            if (node == null) {
                res.add("N")
                return
            }
            res.add(node.`val`.toString())
            dfs(node.left)
            dfs(node.right)
        }
        dfs(root)
        return res.joinToString(",")
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        val tokens = data.split(",")
            .asSequence()
            .filter { it.isNotEmpty() }
            .iterator()

        fun buildDfs(): TreeNode? {
            if (tokens.hasNext().not()) return null

            val value = tokens.next()
            if (value == "N") return null

            val node = TreeNode(value.toInt())
            node.left = buildDfs()
            node.right = buildDfs()

            return node
        }
        return buildDfs()
    }
}

/**
 * Your Codec object will be instantiated and called as such:
 * var ser = Codec()
 * var deser = Codec()
 * var data = ser.serialize(longUrl)
 * var ans = deser.deserialize(data)
 */


//chat-gpt unit tests
class CodecTest {

    /** Build a tree from a level-order list where nulls are represented by null. */
    fun buildTree(level: List<Int?>): TreeNode? {
        if (level.isEmpty() || level[0] == null) return null
        val root = TreeNode(level[0]!!)
        val q: ArrayDeque<TreeNode> = ArrayDeque()
        q.add(root)
        var i = 1
        while (i < level.size && q.isNotEmpty()) {
            val cur = q.removeFirst()

            if (i < level.size) {
                val lv = level[i++]
                if (lv != null) {
                    cur.left = TreeNode(lv)
                    q.add(cur.left!!)
                }
            }
            if (i < level.size) {
                val rv = level[i++]
                if (rv != null) {
                    cur.right = TreeNode(rv)
                    q.add(cur.right!!)
                }
            }
        }
        return root
    }

    /** Deep structural equality (values + shape). */
    fun equalsTree(a: TreeNode?, b: TreeNode?): Boolean {
        if (a === b) return true
        if (a == null || b == null) return false
        if (a.`val` != b.`val`) return false
        return equalsTree(a.left, b.left) && equalsTree(a.right, b.right)
    }

    /** Level-order (with nulls) snapshot to help debug failing tests. */
    fun toLevelOrderWithNulls(root: TreeNode?): List<Int?> {
        if (root == null) return emptyList()
        val res = mutableListOf<Int?>()
        val q: ArrayDeque<TreeNode?> = ArrayDeque()
        q.add(root)
        while (q.isNotEmpty()) {
            val node = q.removeFirst()
            if (node == null) {
                res.add(null)
            } else {
                res.add(node.`val`)
                q.add(node.left)
                q.add(node.right)
            }
        }
        // trim trailing nulls for readability
        var end = res.size
        while (end > 0 && res[end - 1] == null) end--
        return res.subList(0, end)
    }


    private val codec = Codec()

    @Test
    @DisplayName("Empty tree round-trip")
    fun emptyTree() {
        val root: TreeNode? = null
        val s = codec.serialize(root)
        val back = codec.deserialize(s)
        assertTrue(equalsTree(root, back), "Expected null tree after round-trip")
    }

    @Test
    @DisplayName("Single node round-trip")
    fun singleNode() {
        val root = TreeNode(42)
        val s = codec.serialize(root)
        val back = codec.deserialize(s)
        assertTrue(equalsTree(root, back))
    }

    @Test
    @DisplayName("Balanced tree round-trip")
    fun balancedTree() {
        //        1
        //      /   \
        //     2     3
        //    / \   / \
        //   4  5  6   7
        val root = buildTree(listOf(1, 2, 3, 4, 5, 6, 7))
        val s = codec.serialize(root)
        val back = codec.deserialize(s)
        assertTrue(equalsTree(root, back)) {
            "Expected same tree, got: ${toLevelOrderWithNulls(back)}"
        }
    }

    @Test
    @DisplayName("Skewed tree (right) round-trip")
    fun skewedRight() {
        // 1 -> 2 -> 3 -> 4 -> 5
        val root = TreeNode(1).apply {
            right = TreeNode(2).apply {
                right = TreeNode(3).apply {
                    right = TreeNode(4).apply {
                        right = TreeNode(5)
                    }
                }
            }
        }
        val s = codec.serialize(root)
        val back = codec.deserialize(s)
        assertTrue(equalsTree(root, back))
    }

    @Test
    @DisplayName("Tree with negatives and duplicates")
    fun negativesAndDuplicates() {
        // Level: [1, 1, 1, -3, null, -3, -3]
        val root = buildTree(listOf(1, 1, 1, -3, null, -3, -3))
        val s = codec.serialize(root)
        val back = codec.deserialize(s)
        assertTrue(equalsTree(root, back))
    }

    @Test
    @DisplayName("Deterministic random trees: multiple round-trips")
    fun randomRoundTrips() {
        val seed = 20250930
        val rng = Random(seed)
        repeat(10) {
            val root = genRandomTree(size = 20, nullProb = 0.25, rng = rng)
            val s = codec.serialize(root)
            val back = codec.deserialize(s)
            assertTrue(equalsTree(root, back)) {
                "Mismatch for tree: ${toLevelOrderWithNulls(root)}"
            }
        }
    }

    // --- Helpers for random tree generation ---

    private fun genRandomTree(size: Int, nullProb: Double, rng: Random): TreeNode? {
        // Generate a level-order array with nulls then build
        val arr = MutableList<Int?>(size) {
            if (rng.nextDouble() < nullProb) null else rng.nextInt(-10, 11)
        }
        // ensure root is non-null so we actually have a tree
        if (arr.isNotEmpty()) arr[0] = rng.nextInt(-10, 11)
        return buildTree(arr)
    }
}

