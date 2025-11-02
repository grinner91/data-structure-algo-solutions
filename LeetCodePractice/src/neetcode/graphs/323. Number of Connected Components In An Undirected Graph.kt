package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ConnectedComponentsDFS {
    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        if (n <= 1) return n
        val adjGraph = Array(n) { mutableListOf<Int>() }
        edges.forEach { (u, v) ->
            adjGraph[u].add(v)
            adjGraph[v].add(u)
        }

        val visited = BooleanArray(n)
        fun dfs(node: Int) {
            adjGraph[node].forEach { nbr ->
                if (!visited[nbr]) {
                    visited[node] = true
                    dfs(nbr)
                }
            }
        }

        var count = 0
        for (node in 0 until n) {
            if (!visited[node]) {
                count++
                visited[node] = true
                dfs(node)
            }
        }
        return count
    }
}

class ConnectedComponentsBFS {
    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        if (n <= 1) return n
        val adjGraph = Array(n) { mutableListOf<Int>() }
        edges.forEach { (u, v) ->
            adjGraph[u].add(v)
            adjGraph[v].add(u)
        }
        val visited = BooleanArray(n)

        fun bfs(node: Int) {
            val que = ArrayDeque<Int>()
            visited[node] = true
            que.addLast(node)
            while (que.isNotEmpty()) {
                val cur = que.removeFirst()
                adjGraph[cur].forEach { nbr ->
                    if (!visited[nbr]) {
                        visited[nbr] = true
                        que.addLast(nbr)
                    }
                }
            }
        }

        var res = 0
        for (node in 0 until n) {
            if (!visited[node]) {
                res++
                bfs(node)
            }
        }
        return res
    }
}

class ConnectedComponentsUnionFind {
    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        if (n <= 1) return n
        val parent = IntArray(n) { it }
        val rank = IntArray(n)
        fun find(node: Int): Int {
            var cur = node
            while (cur != parent[cur]) {
                parent[cur] = parent[parent[cur]]
                cur = parent[cur]
            }
            return cur
        }

        fun union(x: Int, y: Int): Boolean {
            val px = find(x)
            val py = find(y)
            if (px == py) return false
            if (rank[px] < rank[py]) {
                parent[px] = py
            } else {
                parent[py] = px
                rank[px] += rank[py]
            }
            return true
        }

        var res = n
        for ((x, y) in edges) {
            if (union(x, y)) res--
        }
        return res
    }
}

//chatgpt
class ConnectedComponentsTest {
    private val sut = ConnectedComponentsDFS()

    //private val sut =  ConnectedComponentsBFS()
    //private val sut =  ConnectedComponentsUnionFind()
    @Test
    fun `example 1 simple connected graph`() {
        val n = 5
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(3, 4)
        )
        assertEquals(2, sut.countComponents(n, edges))
    }

    @Test
    fun `example 2 no edges all isolated`() {
        val n = 5
        val edges = emptyArray<IntArray>()
        assertEquals(5, sut.countComponents(n, edges))
    }

    @Test
    fun `single node`() {
        val n = 1
        val edges = emptyArray<IntArray>()
        assertEquals(1, sut.countComponents(n, edges))
    }

    @Test
    fun `fully connected chain`() {
        val n = 6
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(2, 3),
            intArrayOf(3, 4),
            intArrayOf(4, 5)
        )
        assertEquals(1, sut.countComponents(n, edges))

    }

    @Test
    fun `contains duplicate edges and self loops`() {
        val n = 4
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(0, 1), // duplicate
            intArrayOf(1, 1), // self loop
            intArrayOf(2, 3)
        )
        assertEquals(2, sut.countComponents(n, edges))

    }

    @Test
    fun `edges with out of range nodes are ignored`() {
        val n = 4
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 5), // invalid, ignored
            intArrayOf(-1, 2), // invalid, ignored
            intArrayOf(2, 3)
        )
        assertEquals(2, sut.countComponents(n, edges))
    }

    @Test
    fun `disconnected clusters`() {
        val n = 10
        val edges = arrayOf(
            // cluster A: 0-1-2
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            // cluster B: 3-4
            intArrayOf(3, 4),
            // cluster C: 5-6-7
            intArrayOf(5, 6),
            intArrayOf(6, 7)
            // nodes 8 and 9 isolated
        )
        // Components: {0,1,2}, {3,4}, {5,6,7}, {8}, {9} => 5
        assertEquals(5, sut.countComponents(n, edges))
    }
}
