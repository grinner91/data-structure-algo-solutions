package neetcode.graphs


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//BFS
class SolutionTopologicalSort {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val graph = Array(numCourses) { mutableListOf<Int>() }
        val indegree = IntArray(numCourses)

        for ((course, pre) in prerequisites) {
            graph[pre].add(course)
            indegree[course]++
        }

        val que = ArrayDeque<Int>()
        for (crs in 0 until  numCourses) {
            if(indegree[crs] == 0) {
                que.addLast(crs)
            }
        }
        //bfs
        var completed = 0
        while (que.isNotEmpty()) {
            val course = que.removeFirst()
            for (next in graph[course]) {
                indegree[next]--
                if (indegree[next] == 0) {
                    que.addLast(next)
                }
            }
            completed++
        }

        return completed == numCourses
    }
}

class CourseScheduleTest {

    private val impls = listOf(
        //SolutionDfs()::canFinish,
        SolutionTopologicalSort()::canFinish
    )

    @Test
    fun noPrerequisites() {
        impls.forEach { canFinish ->
            assertEquals(
                true,
                canFinish(3, arrayOf())
            )
        }
    }

    @Test
    fun simplePossible() {
        impls.forEach { canFinish ->
            assertEquals(
                true,
                canFinish(
                    2,
                    arrayOf(
                        intArrayOf(1, 0)
                    )
                )
            )
        }
    }

    @Test
    fun simpleCycle() {
        impls.forEach { canFinish ->
            assertEquals(
                false,
                canFinish(
                    2,
                    arrayOf(
                        intArrayOf(1, 0),
                        intArrayOf(0, 1)
                    )
                )
            )
        }
    }

    @Test
    fun multipleCoursesPossible() {
        impls.forEach { canFinish ->
            assertEquals(
                true,
                canFinish(
                    4,
                    arrayOf(
                        intArrayOf(1, 0),
                        intArrayOf(2, 0),
                        intArrayOf(3, 1),
                        intArrayOf(3, 2)
                    )
                )
            )
        }
    }

    @Test
    fun cycleInsideGraph() {
        impls.forEach { canFinish ->
            assertEquals(
                false,
                canFinish(
                    4,
                    arrayOf(
                        intArrayOf(1, 0),
                        intArrayOf(2, 1),
                        intArrayOf(1, 2),
                        intArrayOf(3, 0)
                    )
                )
            )
        }
    }

    @Test
    fun disconnectedGraphWithNoCycle() {
        impls.forEach { canFinish ->
            assertEquals(
                true,
                canFinish(
                    5,
                    arrayOf(
                        intArrayOf(1, 0),
                        intArrayOf(3, 2)
                    )
                )
            )
        }
    }

    @Test
    fun disconnectedGraphWithCycle() {
        impls.forEach { canFinish ->
            assertEquals(
                false,
                canFinish(
                    5,
                    arrayOf(
                        intArrayOf(1, 0),
                        intArrayOf(3, 2),
                        intArrayOf(2, 3)
                    )
                )
            )
        }
    }

    @Test
    fun selfCycle() {
        impls.forEach { canFinish ->
            assertEquals(
                false,
                canFinish(
                    1,
                    arrayOf(
                        intArrayOf(0, 0)
                    )
                )
            )
        }
    }
}