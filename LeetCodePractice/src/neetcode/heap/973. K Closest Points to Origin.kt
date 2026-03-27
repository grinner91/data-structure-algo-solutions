package neetcode.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class KClosestPointsToOriginMinHeap {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        val minHeap = PriorityQueue(compareBy<IntArray> { it[0] * it[0] + it[1] * it[1] })
        points.forEach { minHeap.offer(it) }
        val res = mutableListOf<IntArray>()
        repeat(k) {
            res.add(minHeap.poll())
        }
        return res.toTypedArray()
    }
}

class KClosestPointsToOriginMaxHeap {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        val maxHeap = PriorityQueue<IntArray> { a, b -> distanceSquared(b) - distanceSquared(a) }
        points.forEach {
            maxHeap.offer(it)
            if (maxHeap.size > k) {
                maxHeap.poll()
            }
        }
        val res = mutableListOf<IntArray>()
        repeat(k) {
            res.add(maxHeap.poll())
        }
        return res.toTypedArray()
    }

    private fun distanceSquared(point: IntArray): Int {
        return point[0] * point[0] + point[1] * point[1]
    }
}

class KClosestPointsToOriginTest {

    private val impls = listOf(
        KClosestPointsToOriginMinHeap()::kClosest,
        KClosestPointsToOriginMaxHeap()::kClosest
    )

    @Test
    fun example1() {
        val points = arrayOf(
            intArrayOf(1, 3),
            intArrayOf(-2, 2)
        )
        val expected = arrayOf(
            intArrayOf(-2, 2)
        )

        impls.forEach { f ->
            assertPointsMatchIgnoringOrder(expected, f(copyPoints(points), 1))
        }
    }

    @Test
    fun example2() {
        val points = arrayOf(
            intArrayOf(3, 3),
            intArrayOf(5, -1),
            intArrayOf(-2, 4)
        )
        val expected = arrayOf(
            intArrayOf(3, 3),
            intArrayOf(-2, 4)
        )

        impls.forEach { f ->
            assertPointsMatchIgnoringOrder(expected, f(copyPoints(points), 2))
        }
    }

    @Test
    fun singlePoint() {
        val points = arrayOf(
            intArrayOf(7, -4)
        )
        val expected = arrayOf(
            intArrayOf(7, -4)
        )

        impls.forEach { f ->
            assertPointsMatchIgnoringOrder(expected, f(copyPoints(points), 1))
        }
    }

    @Test
    fun allPointsRequested() {
        val points = arrayOf(
            intArrayOf(1, 2),
            intArrayOf(2, 1),
            intArrayOf(-1, -2)
        )
        val expected = arrayOf(
            intArrayOf(1, 2),
            intArrayOf(2, 1),
            intArrayOf(-1, -2)
        )

        impls.forEach { f ->
            assertPointsMatchIgnoringOrder(expected, f(copyPoints(points), 3))
        }
    }

    @Test
    fun includesNegativeCoordinates() {
        val points = arrayOf(
            intArrayOf(-5, 4),
            intArrayOf(0, 2),
            intArrayOf(1, -1),
            intArrayOf(-2, -2)
        )
        val expected = arrayOf(
            intArrayOf(1, -1),
            intArrayOf(0, 2)
        )

        impls.forEach { f ->
            assertPointsMatchIgnoringOrder(expected, f(copyPoints(points), 2))
        }
    }

    @Test
    fun duplicatePoints() {
        val points = arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 1),
            intArrayOf(3, 3)
        )
        val expected = arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 1)
        )

        impls.forEach { f ->
            assertPointsMatchIgnoringOrder(expected, f(copyPoints(points), 2))
        }
    }

    @Test
    fun zeroPointIncluded() {
        val points = arrayOf(
            intArrayOf(0, 0),
            intArrayOf(2, 2),
            intArrayOf(1, 1)
        )
        val expected = arrayOf(
            intArrayOf(0, 0)
        )

        impls.forEach { f ->
            assertPointsMatchIgnoringOrder(expected, f(copyPoints(points), 1))
        }
    }

    @Test
    fun tiesInDistance() {
        val points = arrayOf(
            intArrayOf(1, 1),
            intArrayOf(-1, -1),
            intArrayOf(2, 0),
            intArrayOf(0, 2)
        )

        impls.forEach { f ->
            val result = f(copyPoints(points), 2)

            assertEquals(2, result.size)
            result.forEach { point ->
                assertEquals(2, distanceSquared(point))
            }
        }
    }

    private fun assertPointsMatchIgnoringOrder(
        expected: Array<IntArray>,
        actual: Array<IntArray>
    ) {
        assertEquals(
            normalize(expected),
            normalize(actual)
        )
    }

    private fun normalize(points: Array<IntArray>): List<String> {
        return points
            .map { "${it[0]},${it[1]}" }
            .sorted()
    }

    private fun distanceSquared(point: IntArray): Int {
        return point[0] * point[0] + point[1] * point[1]
    }

    private fun copyPoints(points: Array<IntArray>): Array<IntArray> {
        return Array(points.size) { i ->
            intArrayOf(points[i][0], points[i][1])
        }
    }
}