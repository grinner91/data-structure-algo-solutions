package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CourseScheduleIIBfsTopologicalSort {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        val graph = Array(numCourses) { mutableListOf<Int>() }
        val indegree = IntArray(numCourses)

        for ((course, pre) in prerequisites) {
            graph[pre].add(course)
            indegree[course]++
        }

        val que = ArrayDeque<Int>()
        for (course in 0 until numCourses) {
            if (indegree[course] == 0) {
                que.addLast(course)
            }
        }

        val order = mutableListOf<Int>()
        while (que.isNotEmpty()) {
            val course = que.removeFirst()
            order.add(course)

            for (next in graph[course]) {
                indegree[next]--
                if (indegree[next] == 0) {
                    que.addLast(next)
                }
            }
        }
        return if (order.size == numCourses) order.toIntArray()
        else intArrayOf()
    }
}

class CourseScheduleIITest {

    private val impls = listOf(
        CourseScheduleIIBfsTopologicalSort()::findOrder,
        //SolutionDfsTopological()::findOrder
    )

    @Test
    fun noPrerequisites() {
        impls.forEach { findOrder ->
            val result = findOrder(3, emptyArray())

            assertEquals(3, result.size)
            assertValidOrder(3, emptyArray(), result)
        }
    }

    @Test
    fun simpleChain() {
        impls.forEach { findOrder ->
            val prerequisites = arrayOf(
                intArrayOf(1, 0),
                intArrayOf(2, 1),
                intArrayOf(3, 2)
            )

            val result = findOrder(4, prerequisites)

            assertEquals(4, result.size)
            assertValidOrder(4, prerequisites, result)
        }
    }

    @Test
    fun multiplePrerequisites() {
        impls.forEach { findOrder ->
            val prerequisites = arrayOf(
                intArrayOf(1, 0),
                intArrayOf(2, 0),
                intArrayOf(3, 1),
                intArrayOf(3, 2)
            )

            val result = findOrder(4, prerequisites)

            assertEquals(4, result.size)
            assertValidOrder(4, prerequisites, result)
        }
    }

    @Test
    fun disconnectedGraph() {
        impls.forEach { findOrder ->
            val prerequisites = arrayOf(
                intArrayOf(1, 0),
                intArrayOf(3, 2)
            )

            val result = findOrder(4, prerequisites)

            assertEquals(4, result.size)
            assertValidOrder(4, prerequisites, result)
        }
    }

    @Test
    fun hasCycle() {
        impls.forEach { findOrder ->
            val prerequisites = arrayOf(
                intArrayOf(1, 0),
                intArrayOf(0, 1)
            )

            val result = findOrder(2, prerequisites)

            assertTrue(result.isEmpty())
        }
    }

    @Test
    fun largerCycle() {
        impls.forEach { findOrder ->
            val prerequisites = arrayOf(
                intArrayOf(1, 0),
                intArrayOf(2, 1),
                intArrayOf(3, 2),
                intArrayOf(1, 3)
            )

            val result = findOrder(4, prerequisites)

            assertTrue(result.isEmpty())
        }
    }

    private fun assertValidOrder(
        numCourses: Int,
        prerequisites: Array<IntArray>,
        order: IntArray
    ) {
        assertEquals(numCourses, order.size)

        val position = IntArray(numCourses)

        for (i in order.indices) {
            position[order[i]] = i
        }

        for ((course, prereq) in prerequisites) {
            assertTrue(
                position[prereq] < position[course],
                "Prerequisite $prereq must come before course $course"
            )
        }
    }
}