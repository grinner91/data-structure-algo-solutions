package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class SolutionMultiSourceBfs {

    fun islandsAndTreasure(grid: Array<IntArray>) {
        if (grid.isEmpty() || grid[0].isEmpty()) return

        data class Cell(val r: Int, val c: Int)

        val INF = 2147483647
        val que = ArrayDeque<Cell>()

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == 0) {
                    que.addLast(Cell(r, c))
                }
            }
        }

        val directions = intArrayOf(1, 0, -1, 0, 1)
        while (que.isNotEmpty()) {
            val (r, c) = que.removeFirst()

            for (i in 0..3) {
                val nr = r + directions[i]
                val nc = c + directions[i + 1]

                if (nr in grid.indices &&
                    nc in grid[0].indices &&
                    grid[nr][nc] == INF
                ) {

                    grid[nr][nc] = grid[r][c] + 1
                    que.addLast(Cell(nr, nc))
                }
            }
        }
    }
}

class IslandsAndTreasureTest {

    private val INF = 2147483647

    private val impls = listOf(
        SolutionMultiSourceBfs()::islandsAndTreasure,
    )

    @Test
    fun exampleCase() {
        val input = arrayOf(
            intArrayOf(INF, -1, 0, INF),
            intArrayOf(INF, INF, INF, -1),
            intArrayOf(INF, -1, INF, -1),
            intArrayOf(0, -1, INF, INF),
        )
        val expected = arrayOf(
            intArrayOf(3, -1, 0, 1),
            intArrayOf(2, 2, 1, -1),
            intArrayOf(1, -1, 2, -1),
            intArrayOf(0, -1, 3, 4),
        )

        impls.forEach { solve ->
            val actual = copyGrid(input)
            solve(actual)
            assertGridEquals(expected, actual)
        }
    }

    @Test
    fun allBlockedAndTreasures() {
        val input = arrayOf(
            intArrayOf(0, -1),
            intArrayOf(-1, INF),
        )
        val expected = arrayOf(
            intArrayOf(0, -1),
            intArrayOf(-1, INF),
        )

        impls.forEach { solve ->
            val actual = copyGrid(input)
            solve(actual)
            assertGridEquals(expected, actual)
        }
    }

    @Test
    fun singleTreasure() {
        val input = arrayOf(
            intArrayOf(INF, INF, INF),
            intArrayOf(INF, 0, INF),
            intArrayOf(INF, INF, INF),
        )
        val expected = arrayOf(
            intArrayOf(2, 1, 2),
            intArrayOf(1, 0, 1),
            intArrayOf(2, 1, 2),
        )

        impls.forEach { solve ->
            val actual = copyGrid(input)
            solve(actual)
            assertGridEquals(expected, actual)
        }
    }

    @Test
    fun noTreasure() {
        val input = arrayOf(
            intArrayOf(INF, INF),
            intArrayOf(INF, -1),
        )
        val expected = arrayOf(
            intArrayOf(INF, INF),
            intArrayOf(INF, -1),
        )

        impls.forEach { solve ->
            val actual = copyGrid(input)
            solve(actual)
            assertGridEquals(expected, actual)
        }
    }

    @Test
    fun emptyGrid() {
        impls.forEach { solve ->
            val actual = emptyArray<IntArray>()
            solve(actual)
            assertArrayEquals(emptyArray<IntArray>(), actual)
        }
    }

    // Helpers (as per your preference: inside test class)

    private fun copyGrid(grid: Array<IntArray>): Array<IntArray> =
        Array(grid.size) { r -> grid[r].clone() }

    private fun assertGridEquals(expected: Array<IntArray>, actual: Array<IntArray>) {
        assertArrayEquals(expected, actual)
    }
}