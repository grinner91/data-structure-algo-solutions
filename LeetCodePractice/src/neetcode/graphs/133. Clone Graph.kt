package neetcode.graphs

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class Node(var `val`: Int) {
    var neighbors: ArrayList<Node?> = ArrayList<Node?>()
}

//TC O(V+E)
//MC O(V) for map + recursion stack up to O(V)
class CloneGraphDfsRecursive {
    fun cloneGraph(node: Node?): Node? {
        if (node == null) return null

        val clones = HashMap<Node, Node>()

        fun dfs(node: Node): Node {
            clones[node]?.let { return it }

            val copy = Node(node.`val`)
            clones[node] = copy

            node.neighbors.forEach { nei ->
                nei?.let {
                    val neiCopy = dfs(it)
                    copy.neighbors.add(neiCopy)
                }
            }

            return copy
        }

        return dfs(node)
    }
}

//TC O(V+E)
//MC O(V) for map
class CloneGraphDfsIterative {
    fun cloneGraph(node: Node?): Node? {
        if (node == null) return null

        val clones = HashMap<Node, Node>()
        val stack = ArrayDeque<Node>()
        stack.add(node)

        while (stack.isNotEmpty()) {
            val cur = stack.removeLast()
            val copy = clones.getOrPut(cur) { Node(cur.`val`) }

            for (nei in cur.neighbors) {
                if (nei == null) continue

                if (nei !in clones) {
                    clones[nei] = Node(nei.`val`)
                    stack.addLast(nei)
                }

                copy.neighbors.add(clones[nei])
            }
        }

        return clones[node]
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

class CloneGraphTest {

    private val impls = listOf(
        //CloneGraphDfsRecursive()::cloneGraph,
        CloneGraphDfsIterative()::cloneGraph
    )

    @Test
    fun nullGraph() {
        impls.forEach { cloneGraph ->
            assertNull(cloneGraph(null))
        }
    }

    @Test
    fun singleNodeNoNeighbors() {
        impls.forEach { cloneGraph ->
            val node1 = Node(1)

            val cloned = cloneGraph(node1)

            assertNotNull(cloned)
            assertNotSame(node1, cloned)
            assertEquals(1, cloned!!.`val`)
            assertTrue(cloned.neighbors.isEmpty())
        }
    }

    @Test
    fun singleNodeSelfLoop() {
        impls.forEach { cloneGraph ->
            val node1 = Node(1)
            node1.neighbors.add(node1)

            val cloned = cloneGraph(node1)

            assertNotNull(cloned)
            assertNotSame(node1, cloned)
            assertEquals(1, cloned!!.`val`)
            assertEquals(1, cloned.neighbors.size)
            assertSame(cloned, cloned.neighbors[0])
        }
    }

    @Test
    fun twoConnectedNodes() {
        impls.forEach { cloneGraph ->
            val node1 = Node(1)
            val node2 = Node(2)

            node1.neighbors.add(node2)
            node2.neighbors.add(node1)

            val cloned = cloneGraph(node1)

            assertGraphDeepClone(node1, cloned)
        }
    }

    @Test
    fun fourNodeSquareGraph() {
        impls.forEach { cloneGraph ->
            val node1 = Node(1)
            val node2 = Node(2)
            val node3 = Node(3)
            val node4 = Node(4)

            node1.neighbors.add(node2)
            node1.neighbors.add(node4)

            node2.neighbors.add(node1)
            node2.neighbors.add(node3)

            node3.neighbors.add(node2)
            node3.neighbors.add(node4)

            node4.neighbors.add(node1)
            node4.neighbors.add(node3)

            val cloned = cloneGraph(node1)

            assertGraphDeepClone(node1, cloned)
        }
    }

    @Test
    fun graphWithCycle() {
        impls.forEach { cloneGraph ->
            val node1 = Node(1)
            val node2 = Node(2)
            val node3 = Node(3)

            node1.neighbors.add(node2)
            node2.neighbors.add(node3)
            node3.neighbors.add(node1)

            val cloned = cloneGraph(node1)

            assertGraphDeepClone(node1, cloned)
        }
    }

    @Test
    fun modifyingCloneDoesNotAffectOriginal() {
        impls.forEach { cloneGraph ->
            val node1 = Node(1)
            val node2 = Node(2)

            node1.neighbors.add(node2)
            node2.neighbors.add(node1)

            val cloned = cloneGraph(node1)!!
            cloned.`val` = 100
            cloned.neighbors.clear()

            assertEquals(1, node1.`val`)
            assertEquals(1, node1.neighbors.size)
            assertSame(node2, node1.neighbors[0])
        }
    }

    private fun assertGraphDeepClone(original: Node?, cloned: Node?) {
        if (original == null) {
            assertNull(cloned)
            return
        }

        assertNotNull(cloned)

        val visited = HashMap<Node, Node>()
        val queue = ArrayDeque<Pair<Node, Node>>()
        queue.addLast(original to cloned!!)

        while (queue.isNotEmpty()) {
            val (origNode, cloneNode) = queue.removeFirst()

            if (origNode in visited) {
                assertSame(visited[origNode], cloneNode)
                continue
            }

            visited[origNode] = cloneNode

            assertNotSame(origNode, cloneNode)
            assertEquals(origNode.`val`, cloneNode.`val`)
            assertEquals(origNode.neighbors.size, cloneNode.neighbors.size)

            val origNeighborValues = origNode.neighbors.map { it!!.`val` }
            val cloneNeighborValues = cloneNode.neighbors.map { it!!.`val` }
            assertEquals(origNeighborValues, cloneNeighborValues)

            for (i in origNode.neighbors.indices) {
                val origNeighbor = origNode.neighbors[i]!!
                val cloneNeighbor = cloneNode.neighbors[i]!!

                assertNotSame(origNeighbor, cloneNeighbor)
                queue.addLast(origNeighbor to cloneNeighbor)
            }
        }
    }
}