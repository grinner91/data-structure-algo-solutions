package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GraphValidTreeDFS {
    fun validTree(n: Int, edges: Array<IntArray>): Boolean {
        if (n == 0) return false
        if (edges.size != n - 1) return false
        if (n == 1) return edges.isEmpty()

        val adjGraph = Array(n) { mutableListOf<Int>() }
        for ((u, v) in edges) {
            adjGraph[u].add(v)
            adjGraph[v].add(u)
        }
        val visited = BooleanArray(n)
        fun dfs(node: Int, parent: Int): Boolean {
            if (visited[node]) return false
            visited[node] = true
            for (nbr in adjGraph[node]) {
                if (nbr == parent) continue
                if (visited[nbr]) return false
                if (!dfs(nbr, node)) return false
            }
            return true
        }
        if (!dfs(0, -1)) return false
        visited.forEach { if (!it) return false }
        return true
    }
}

class GraphValidTreeTest {

    private val solver = GraphValidTreeDFS()

    @Test
    fun example_valid() {
        val n = 5
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(0, 2),
            intArrayOf(0, 3),
            intArrayOf(1, 4)
        )
        assertTrue(solver.validTree(n, edges))
    }

    @Test
    fun example_cycle_invalid() {
        val n = 5
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(2, 3),
            intArrayOf(1, 3),
            intArrayOf(1, 4)
        )
        assertFalse(solver.validTree(n, edges))
    }

    @Test
    fun disconnected_invalid() {
        val n = 4
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(2, 3)
        )
        assertFalse(solver.validTree(n, edges))
    }

    @Test
    fun single_node_tree() {
        val n = 1
        val edges = emptyArray<IntArray>()
        assertTrue(solver.validTree(n, edges))
    }

    @Test
    fun two_nodes_connected_valid() {
        val n = 2
        val edges = arrayOf(intArrayOf(0, 1))
        assertTrue(solver.validTree(n, edges))
    }

    @Test
    fun wrong_edge_count_too_few() {
        val n = 3
        val edges = arrayOf(intArrayOf(0, 1))
        assertFalse(solver.validTree(n, edges))
    }

    @Test
    fun wrong_edge_count_too_many_even_if_looks_connected() {
        val n = 3
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(1, 2),
            intArrayOf(0, 2) // creates cycle & edges.size != n-1
        )
        assertFalse(solver.validTree(n, edges))
    }

    @Test
    fun duplicate_edge_creates_cycle() {
        val n = 3
        val edges = arrayOf(
            intArrayOf(0, 1),
            intArrayOf(0, 1) // duplicate implies cycle in DSU logic
        )
        assertFalse(solver.validTree(n, edges))
    }
}
