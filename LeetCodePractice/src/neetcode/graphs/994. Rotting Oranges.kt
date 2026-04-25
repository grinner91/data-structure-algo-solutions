package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RottingOrangesSolutions {
    // ------------------------------------------------------------
    // 1) Multi-source BFS
    // ------------------------------------------------------------
    fun orangesRottingBfs(grid: Array<IntArray>): Int {
        if (grid.isEmpty() || grid[0].isEmpty()) return 0

        data class Cell(val row: Int, val col: Int)

        val que = ArrayDeque<Cell>()
        var fresh = 0

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                when (grid[r][c]) {
                    1 -> fresh++
                    2 -> que.addLast(Cell(r, c))
                }
            }
        }

        if (fresh == 0) return 0

        val direction = intArrayOf(1, 0, -1, 0, 1)
        var minutes = 0

        while (que.isNotEmpty() && fresh > 0) {
            minutes++
            repeat(que.size) {
                val (r, c) = que.removeFirst()
                for (i in 0..3) {
                    val nr = r + direction[i]
                    val nc = c + direction[i + 1]

                    if (nr !in grid.indices ||
                        nc !in grid[0].indices ||
                        grid[nr][nc] != 1
                    ) continue

                    grid[nr][nc] = 2
                    que.addLast(Cell(nr, nc))
                    fresh--
                }
            }
        }

        return if (fresh == 0) minutes else -1
    }
}

class RottingOrangesTest {

    private val solutions = RottingOrangesSolutions()

    private val impls = listOf(
        solutions::orangesRottingBfs,
    )

    @Test
    fun example1() {
        val grid = arrayOf(
            intArrayOf(2, 1, 1),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 1),
        )

        impls.forEach { f ->
            assertEquals(4, f(copyGrid(grid)))
        }
    }

    @Test
    fun example2() {
        val grid = arrayOf(
            intArrayOf(2, 1, 1),
            intArrayOf(0, 1, 1),
            intArrayOf(1, 0, 1),
        )

        impls.forEach { f ->
            assertEquals(-1, f(copyGrid(grid)))
        }
    }

    @Test
    fun example3() {
        val grid = arrayOf(
            intArrayOf(0, 2),
        )

        impls.forEach { f ->
            assertEquals(0, f(copyGrid(grid)))
        }
    }

    @Test
    fun noRottenButFreshExists() {
        val grid = arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 1),
        )

        impls.forEach { f ->
            assertEquals(-1, f(copyGrid(grid)))
        }
    }

    @Test
    fun allAlreadyRotten() {
        val grid = arrayOf(
            intArrayOf(2, 2),
            intArrayOf(2, 2),
        )

        impls.forEach { f ->
            assertEquals(0, f(copyGrid(grid)))
        }
    }

    @Test
    fun allEmpty() {
        val grid = arrayOf(
            intArrayOf(0, 0),
            intArrayOf(0, 0),
        )

        impls.forEach { f ->
            assertEquals(0, f(copyGrid(grid)))
        }
    }

    @Test
    fun singleFreshOrange() {
        val grid = arrayOf(
            intArrayOf(1),
        )

        impls.forEach { f ->
            assertEquals(-1, f(copyGrid(grid)))
        }
    }

    @Test
    fun singleRottenOrange() {
        val grid = arrayOf(
            intArrayOf(2),
        )

        impls.forEach { f ->
            assertEquals(0, f(copyGrid(grid)))
        }
    }

    @Test
    fun isolatedFreshOrange() {
        val grid = arrayOf(
            intArrayOf(2, 0, 1),
        )

        impls.forEach { f ->
            assertEquals(-1, f(copyGrid(grid)))
        }
    }

    @Test
    fun multipleInitialRottenSources() {
        val grid = arrayOf(
            intArrayOf(2, 1, 1),
            intArrayOf(1, 1, 1),
            intArrayOf(1, 1, 2),
        )

        impls.forEach { f ->
            assertEquals(2, f(copyGrid(grid)))
        }
    }

    private fun copyGrid(grid: Array<IntArray>): Array<IntArray> {
        return Array(grid.size) { r -> grid[r].clone() }
    }
}