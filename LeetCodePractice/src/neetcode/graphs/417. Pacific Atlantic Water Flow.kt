package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PacificAtlanticWaterFlowBFS {
    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
        if (heights.isEmpty() || heights[0].isEmpty()) return emptyList()

        data class Cell(val row: Int, val col: Int)

        val rows = heights.size
        val cols = heights[0].size

        fun bfs(starts: List<Cell>, reachable: Array<BooleanArray>) {
            val que = ArrayDeque<Cell>()
            val dir = intArrayOf(1, 0, -1, 0, 1)

            for (cell in starts) {
                reachable[cell.row][cell.col] = true
                que.addLast(cell)
            }

            while (que.isNotEmpty()) {
                val (r, c) = que.removeFirst()
                for (i in 0 until 4) {
                    val nr = r + dir[i]
                    val nc = c + dir[i + 1]
                    if (nr !in 0 until rows ||
                        nc !in 0 until cols ||
                        reachable[nr][nc] ||
                        heights[nr][nc] < heights[r][c]
                    )
                        continue

                    reachable[nr][nc] = true
                    que.addLast(Cell(nr, nc))
                }
            }
        }

        val pacificStarts = buildList<Cell> {
            for (c in 0 until cols) add(Cell(0, c))
            for (r in 0 until rows) add(Cell(r, 0))
        }

        val atlanticStarts = buildList {
            for (c in 0 until cols) add(Cell(rows - 1, c))
            for (r in 0 until rows) add(Cell(r, cols - 1))
        }

        val pacificReach = Array(rows) { BooleanArray(cols) }
        val atlanticReach = Array(rows) { BooleanArray(cols) }

        bfs(pacificStarts, pacificReach)
        bfs(atlanticStarts, atlanticReach)

        val result = mutableListOf<List<Int>>()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (pacificReach[r][c] && atlanticReach[r][c]) {
                    result.add(listOf(r, c))
                }
            }
        }

        return result
    }
}

class PacificAtlanticWaterFlowTest {

    private val impls = listOf(
        PacificAtlanticWaterFlowBFS()::pacificAtlantic,
    )

    @Test
    fun example1() {
        val heights = arrayOf(
            intArrayOf(1, 2, 2, 3, 5),
            intArrayOf(3, 2, 3, 4, 4),
            intArrayOf(2, 4, 5, 3, 1),
            intArrayOf(6, 7, 1, 4, 5),
            intArrayOf(5, 1, 1, 2, 4)
        )

        val expected = setOf(
            listOf(0, 4),
            listOf(1, 3),
            listOf(1, 4),
            listOf(2, 2),
            listOf(3, 0),
            listOf(3, 1),
            listOf(4, 0)
        )

        impls.forEach { f ->
            assertEquals(expected, f(copyGrid(heights)).toSet())
        }
    }

    @Test
    fun singleCell() {
        val heights = arrayOf(
            intArrayOf(42)
        )

        val expected = setOf(
            listOf(0, 0)
        )

        impls.forEach { f ->
            assertEquals(expected, f(copyGrid(heights)).toSet())
        }
    }

    @Test
    fun singleRow() {
        val heights = arrayOf(
            intArrayOf(1, 2, 3, 4, 5)
        )

        val expected = setOf(
            listOf(0, 0),
            listOf(0, 1),
            listOf(0, 2),
            listOf(0, 3),
            listOf(0, 4)
        )

        impls.forEach { f ->
            assertEquals(expected, f(copyGrid(heights)).toSet())
        }
    }

    @Test
    fun singleColumn() {
        val heights = arrayOf(
            intArrayOf(1),
            intArrayOf(2),
            intArrayOf(3),
            intArrayOf(4)
        )

        val expected = setOf(
            listOf(0, 0),
            listOf(1, 0),
            listOf(2, 0),
            listOf(3, 0)
        )

        impls.forEach { f ->
            assertEquals(expected, f(copyGrid(heights)).toSet())
        }
    }

    @Test
    fun flatGridAllReachable() {
        val heights = arrayOf(
            intArrayOf(7, 7),
            intArrayOf(7, 7)
        )

        val expected = setOf(
            listOf(0, 0),
            listOf(0, 1),
            listOf(1, 0),
            listOf(1, 1)
        )

        impls.forEach { f ->
            assertEquals(expected, f(copyGrid(heights)).toSet())
        }
    }

    @Test
    fun increasingTowardBottomRight() {
        val heights = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(2, 3, 4),
            intArrayOf(3, 4, 5)
        )

        val expected = setOf(
            listOf(0, 2),
            listOf(1, 2),
            listOf(2, 0),
            listOf(2, 1),
            listOf(2, 2)
        )

        impls.forEach { f ->
            assertEquals(expected, f(copyGrid(heights)).toSet())
        }
    }

    @Test
    fun decreasingTowardBottomRight() {
        val heights = arrayOf(
            intArrayOf(5, 4, 3),
            intArrayOf(4, 3, 2),
            intArrayOf(3, 2, 1)
        )

        val expected = setOf(
            listOf(0, 0),
            listOf(0, 1),
            listOf(0, 2),
            listOf(1, 0),
            listOf(2, 0)
        )

        impls.forEach { f ->
            assertEquals(expected, f(copyGrid(heights)).toSet())
        }
    }

    private fun copyGrid(grid: Array<IntArray>): Array<IntArray> =
        Array(grid.size) { r -> grid[r].clone() }
}