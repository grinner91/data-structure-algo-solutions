package neetcode.graphs


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SolutionDFSCycleDetection {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val preMap = (0 until numCourses)
            .associateWith { mutableListOf<Int>() }
            .toMutableMap()

        for ((course, prereq) in prerequisites) {
            preMap[course]!!.add(prereq)
        }

        val visited = mutableSetOf<Int>()
        fun dfs(course: Int): Boolean {
            //cycle detected
            if (course in visited) return false
            //no pre-requisite
            if (preMap[course]!!.isEmpty()) return true
            //
            visited.add(course)
            for (pre in preMap[course]!!) {
                if (!dfs(pre)) return false
            }
            visited.remove(course)
            preMap[course] = mutableListOf()
            return true
        }
        for (course in 0 until numCourses) {
            if (!dfs(course)) return false
        }
        return true
    }
}

//BFS
class SolutionTopologicalSort {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val indegree = IntArray(numCourses) { 0 }
        val adjGraph = Array(numCourses) { mutableListOf<Int>() }

        for ((course, prereq) in prerequisites) {
            indegree[course]++
            adjGraph[prereq].add(course)
        }

        val que = ArrayDeque<Int>()
        indegree.forEachIndexed { i, deg ->
            if (deg == 0) que.addLast(i)
        }

        var visited = 0
        while (que.isNotEmpty()) {
            visited++
            val prereq = que.removeFirst()
            for (course in adjGraph[prereq]) {
                indegree[course]--
                if (indegree[course] == 0) que.addLast(course)
            }
        }
        return visited == numCourses
    }
}

class CourseScheduleTest {

    //private val bfs = CourseSchedule()
    //private val dfs = CourseScheduleDFS()

    //private val sut = SolutionDFSCycleDetection()
    private val sut = SolutionTopologicalSort()

    data class Case(
        val numCourses: Int,
        val prereq: Array<IntArray>,
        val expected: Boolean,
        val name: String
    )

    companion object {
        @JvmStatic
        fun cases(): Stream<Case> = Stream.of(
            Case(
                numCourses = 2,
                prereq = arrayOf(intArrayOf(1, 0)),
                expected = true,
                name = "Simple chain"
            ),
            Case(
                numCourses = 2,
                prereq = arrayOf(intArrayOf(1, 0), intArrayOf(0, 1)),
                expected = false,
                name = "Simple cycle length 2"
            ),
            Case(
                numCourses = 4,
                prereq = arrayOf(intArrayOf(1, 0), intArrayOf(2, 1), intArrayOf(3, 2)),
                expected = true,
                name = "Linear chain 0→1→2→3"
            ),
            Case(
                numCourses = 4,
                prereq = arrayOf(intArrayOf(1, 0), intArrayOf(2, 0), intArrayOf(3, 1), intArrayOf(3, 2)),
                expected = true,
                name = "DAG with branching"
            ),
            Case(
                numCourses = 3,
                prereq = arrayOf(intArrayOf(0, 1), intArrayOf(1, 2), intArrayOf(2, 0)),
                expected = false,
                name = "Cycle of length 3"
            ),
            Case(
                numCourses = 1,
                prereq = emptyArray(),
                expected = true,
                name = "Single course no prereqs"
            ),
            Case(
                numCourses = 3,
                prereq = emptyArray(),
                expected = true,
                name = "Multiple courses no prereqs"
            ),
            Case(
                numCourses = 3,
                prereq = arrayOf(intArrayOf(1, 1)), // self-dependency is an immediate cycle
                expected = false,
                name = "Self dependency"
            ),
            Case(
                numCourses = 5,
                prereq = arrayOf( // duplicate edges shouldn't break
                    intArrayOf(1, 0), intArrayOf(1, 0),
                    intArrayOf(2, 0), intArrayOf(3, 2),
                    intArrayOf(4, 3)
                ),
                expected = true,
                name = "Duplicates + chain"
            )
        )
    }

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("cases")
    fun `BFS canFinish cases`(c: Case) {
        assertEquals(c.expected, sut.canFinish(c.numCourses, c.prereq), "BFS failed: ${c.name}")
    }

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("cases")
    fun `DFS canFinish cases`(c: Case) {
        assertEquals(c.expected, sut.canFinish(c.numCourses, c.prereq), "DFS failed: ${c.name}")
    }

    @Test
    fun `stress small dense DAG`() {
        val n = 50
        // DAG: edges i->j for all i < j (no cycles)
        val edges = buildList {
            for (i in 0 until n) for (j in i + 1 until n) add(intArrayOf(j, i))
        }.toTypedArray()

        assertTrue(sut.canFinish(n, edges))
    }
}
