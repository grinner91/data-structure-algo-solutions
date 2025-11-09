package neetcode.graphs

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AlienDictionaryBFS {
    fun foreignDictionary(words: Array<String>): String {
        val graph = mutableMapOf<Char, MutableSet<Char>>()
        val indegree = mutableMapOf<Char, Int>()
        //init graph and indegree
        words.forEach { wrd ->
            wrd.forEach { ch ->
                indegree.putIfAbsent(ch, 0)
                graph.putIfAbsent(ch, mutableSetOf())
            }
        }
        // build edges
        for (i in 0 until words.size - 1) {
            val w1 = words[i]
            val w2 = words[i + 1]
            //invalid order
            if (w1.length > w2.length && w1.startsWith(w2)) {
                return ""
            }
            val minLen = minOf(w1.length, w2.length)
            for (j in 0 until minLen) {
                val c1 = w1[j]
                val c2 = w2[j]
                if (c1 != c2) {
                    if (c2 !in graph[c1]!!) {
                        graph[c1]!!.add(c2)
                        indegree[c2] = indegree.getOrDefault(c2, 0) + 1
                    }
                    //consider only first diff as order
                    break
                }
            }
        }
        //topological short
        val order = StringBuilder()
        val que = ArrayDeque<Char>()
        indegree.forEach { (ch, indeg) ->
            if (indeg == 0) que.addLast(ch)
        }
        while (que.isNotEmpty()) {
            val cur = que.removeFirst()
            order.append(cur)
            graph[cur]!!.forEach { nbr ->
                indegree[nbr] = indegree[nbr]!! - 1
                if (indegree[nbr]!! == 0) {
                    que.addLast(nbr)
                }
            }
        }
        //if cycle or not chars in order
        return if (order.length != indegree.size) "" else order.toString()
    }
}

//chatgpt
class AlienDictionaryTest {

    private val sut = AlienDictionaryBFS()

    private fun isValidOrder(order: String, words: Array<String>): Boolean {
        if (order.isEmpty()) {
            // if empty, must actually be impossible
            // quick check: if it's possible -> fail
            // BUT for tests that expect "", we'll handle directly.
            return false
        }

        // rank[c] = position of c in order
        val rank = HashMap<Char, Int>()
        for ((i, c) in order.withIndex()) {
            rank[c] = i
        }

        // for each adjacent pair of words, ensure ordering constraint is satisfied
        for (i in 0 until words.size - 1) {
            val w1 = words[i]
            val w2 = words[i + 1]

            // invalid prefix means order should be ""
            if (w1.length > w2.length && w1.startsWith(w2)) {
                return false
            }

            val minLen = minOf(w1.length, w2.length)
            var foundDiff = false
            for (j in 0 until minLen) {
                val c1 = w1[j]
                val c2 = w2[j]
                if (c1 != c2) {
                    // c1 must come before c2
                    val r1 = rank[c1] ?: return false
                    val r2 = rank[c2] ?: return false
                    if (r1 >= r2) return false
                    foundDiff = true
                    break
                }
            }
            // if no diff found, w1 must NOT be longer than w2 (already checked)
        }

        return true
    }

    @Test
    fun testExample_leetcode() {
        val words = arrayOf("wrt", "wrf", "er", "ett", "rftt")
        val solver = AlienDictionaryBFS()
        val result = sut.foreignDictionary(words)

        assertTrue(
            "Result $result should be a valid topo order for words list",
            isValidOrder(result, words)
        )
    }

    @Test
    fun testSingleWord() {
        val words = arrayOf("abc")
        val solver = AlienDictionaryBFS()
        val result = sut.foreignDictionary(words)

        // any permutation of {a,b,c} that respects no extra rules is valid.
        // here, graph will give no edges -> queue order == insertion order of indegree map,
        // which will be a,b,c. We'll just check chars match.
        assertEquals(3, result.length)
        assertTrue(result.contains('a'))
        assertTrue(result.contains('b'))
        assertTrue(result.contains('c'))
    }

    @Test
    fun testInvalidPrefix() {
        val words = arrayOf("abc", "ab")
        val solver = AlienDictionaryBFS()
        val result = sut.foreignDictionary(words)
        assertEquals("", result)
    }

    @Test
    fun cycleGraph_returnsEmptyString() {
        // This input creates edges:
        // "ab" vs "bb"  => a -> b
        // "bb" vs "cb"  => b -> c
        // "cb" vs "ab"  => c -> a
        // Together: a -> b -> c -> a (cycle)
        val words = arrayOf("ab", "bb", "cb", "ab")

        val result = sut.foreignDictionary(words)

        // Because there's a cycle, no valid alien ordering exists.
        assertEquals("", result)
    }

    @Test
    fun testDFSImplementationMatchesConstraints() {
        val words = arrayOf("wrt", "wrf", "er", "ett", "rftt")
        val result = sut.foreignDictionary(words)
        assertTrue(
            "DFS Result $result should be a valid topo order",
            isValidOrder(result, words)
        )
    }

    @Test
    fun testAllSameWordRepeated() {
        val words = arrayOf("aaa", "aaa", "aaa")
        val solver = AlienDictionaryBFS()
        val result = sut.foreignDictionary(words)
        // Only char 'a'
        assertEquals("a", result)
    }
}
