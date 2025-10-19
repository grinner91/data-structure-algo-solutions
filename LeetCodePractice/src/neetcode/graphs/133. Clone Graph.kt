package neetcode.graphs
// src/test/kotlin/CloneGraphTest.kt
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class Node(var `val`: Int) {
    var neighbors: ArrayList<Node?> = ArrayList<Node?>()
}

object SolutionCloneGraphDFS {
    fun cloneGraph(node: Node?): Node? {
        if (node == null) return null

        val oldToClone = mutableMapOf<Node, Node>()

        fun dfs(cur: Node): Node {
            if (cur in oldToClone) {
                return oldToClone[cur]!!
            }
            val clone = Node(cur.`val`)
            oldToClone[cur] = clone
            cur.neighbors.forEach {
                it?.let {
                    val neiCopy = dfs(it)
                    clone.neighbors.add(neiCopy)
                }
            }
            return clone
        }

        return dfs(node)
    }
}


object SolutionCloneGraphBFS {
    fun cloneGraph(node: Node?): Node? {
        if (node == null) return null

        val deq = ArrayDeque<Node>()
        val oldToClone = mutableMapOf<Node, Node>()
        deq.addLast(node)
        oldToClone[node] = Node(node.`val`)

        while (deq.isNotEmpty()) {
            val cur = deq.removeFirst()
            val curClone = oldToClone[cur]!!
            cur.neighbors.filterNotNull().forEach { nei ->
                val neiClone = oldToClone.getOrPut(nei) {
                    deq.addLast(nei)
                    Node(nei.`val`)
                }
                curClone.neighbors.add(neiClone)
            }
        }
        return oldToClone[node]
    }
}

/************************/

//chatgpt
class CloneGraphTest {

    // ---------- Helpers (null-safe & idiomatic) -------------------------------

    private fun link(a: Node, b: Node) {
        if (b !in a.neighbors) a.neighbors.add(b)
        if (a !in b.neighbors) b.neighbors.add(a)
    }

    /**
     * Build an undirected graph from an adjacency list.
     * Example: 1 -> [2,4], 2 -> [1,3], ...
     * Returns the node with the smallest id as the entry.
     */
    private fun buildGraph(adjacency: Map<Int, List<Int>>): Node {
        require(adjacency.isNotEmpty()) { "Adjacency must not be empty" }

        val nodes = mutableMapOf<Int, Node>()
        fun nodeOf(v: Int) = nodes.getOrPut(v) { Node(v) }

        // Ensure all keys and neighbors are instantiated
        adjacency.keys.forEach { nodeOf(it) }
        adjacency.values.flatten().forEach { nodeOf(it) }

        // Undirected edges
        adjacency.forEach { (u, nbrs) ->
            val nu = nodeOf(u)
            nbrs.forEach { v ->
                val nv = nodeOf(v)
                link(nu, nv)
            }
        }

        val startVal = adjacency.keys.minOrNull()!!
        return nodes[startVal]!!
    }

    /**
     * Serialize reachable subgraph into a stable adjacency map.
     * Node is non-null by design here.
     */
    private fun serializeAdj(node: Node): Map<Int, Set<Int>> {
        val visited = LinkedHashSet<Node>()
        val q: ArrayDeque<Node> = ArrayDeque()
        val out = mutableMapOf<Int, MutableSet<Int>>()

        visited.add(node)
        q.add(node)

        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            out.putIfAbsent(cur.`val`, mutableSetOf())
            for (nbr in cur.neighbors) {
                out[cur.`val`]!!.add(nbr!!.`val`)
                if (visited.add(nbr)) q.add(nbr)
            }
        }

        return out.mapValues { it.value.toSortedSet() }
    }

    /**
     * Collect all reachable nodes from a non-null start node.
     */
    private fun collectAll(node: Node): Set<Node> {
        val out = LinkedHashSet<Node>()
        val q: ArrayDeque<Node> = ArrayDeque()
        val seen = HashSet<Node>()
        seen.add(node)
        q.add(node)
        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            out.add(cur)
            for (n in cur.neighbors) if (seen.add(n!!)) q.add(n)
        }
        return out
    }

    /**
     * Verify deep copy invariants when both graphs are non-null.
     */
    private fun assertDeepCopy(original: Node, clone: Node) {
        assertEquals(serializeAdj(original), serializeAdj(clone), "Adjacency/shape must be equal")

        val origSet = collectAll(original)
        val cloneSet = collectAll(clone)

        // Ensure no node instance from original set appears in clone set
        for (o in origSet) {
            assertTrue(cloneSet.none { it === o }, "Clone contains reference(s) from original graph")
        }
    }

    // ---------- Tests ---------------------------------------------------------

    @Test
    fun `null graph returns null`() {
        val clonedBFS = SolutionCloneGraphBFS.cloneGraph(null)
        val clonedDFS = SolutionCloneGraphDFS.cloneGraph(null)
        assertNull(clonedBFS)
        assertNull(clonedDFS)
    }

    @Test
    fun `single node no neighbors`() {
        val one = Node(1)
        val cloned = SolutionCloneGraphBFS.cloneGraph(one)
        assertNotNull(cloned)
        val nonNullClone = cloned!!
        assertDeepCopy(one, nonNullClone)
        assertTrue(nonNullClone.neighbors.isEmpty())
        assertNotSame(one, nonNullClone)
    }

    @Test
    fun `simple edge 1--2`() {
        val one = Node(1)
        val two = Node(2)
        link(one, two)

        val cloned = SolutionCloneGraphBFS.cloneGraph(one)
        assertNotNull(cloned)
        assertDeepCopy(one, cloned!!)
    }

    @Test
    fun `square cycle 1-2-3-4-1 and independence after original mutation`() {
        val n1 = Node(1);
        val n2 = Node(2);
        val n3 = Node(3);
        val n4 = Node(4)
        link(n1, n2); link(n2, n3); link(n3, n4); link(n4, n1)

        val cloned = SolutionCloneGraphBFS.cloneGraph(n1)
        assertNotNull(cloned)
        val nonNullClone = cloned!!
        assertDeepCopy(n1, nonNullClone)

        // Mutate original graph after cloning; clone should remain unchanged
        val n5 = Node(5)
        link(n1, n5)

        val clonedAdj = serializeAdj(nonNullClone)
        assertFalse(clonedAdj.containsKey(5), "Clone should not reflect mutations to original")
    }

    @Test
    fun `build from adjacency and compare using DFS cloner`() {
        // 1: [2,4], 2: [1,3], 3: [2,4], 4: [1,3]
        val adj = mapOf(
            1 to listOf(2, 4),
            2 to listOf(1, 3),
            3 to listOf(2, 4),
            4 to listOf(1, 3),
        )

        val g = buildGraph(adj)
        val cloned = SolutionCloneGraphDFS.cloneGraph(g)
        assertNotNull(cloned)
        assertDeepCopy(g, cloned!!)
    }
}
