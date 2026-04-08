package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MaxAreaOfIslandDfsRecursive {
    fun maxAreaOfIsland(grid: Array<IntArray>): Int {
        fun dfs(sr: Int, sc: Int): Int {
            if (sr !in grid.indices
                || sc !in grid[0].indices
                || grid[sr][sc] == 0
            ) {
                return 0
            }
            grid[sr][sc] = 0
            return 1 +
                    dfs(sr + 1, sc) +
                    dfs(sr - 1, sc) +
                    dfs(sr, sc + 1) +
                    dfs(sr, sc - 1)
        }

        var maxArea = 0
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == 1) {
                    val curArea = dfs(r, c)
                    maxArea = maxOf(maxArea, curArea)
                }
            }
        }
        return maxArea
    }
}

class MaxAreaOfIslandDfsIterative {
    fun maxAreaOfIsland(grid: Array<IntArray>): Int {
        data class Cell(val r: Int, val c: Int)

        fun dfs(sr: Int, sc: Int): Int {
            val directions = intArrayOf(1, 0, -1, 0, 1)
            val stack = ArrayDeque<Cell>() //pair - (r,c)
            stack.addLast(Cell(sr, sc))
            grid[sr][sc] = 0

            var area = 0
            while (stack.isNotEmpty()) {
                area++
                val (r, c) = stack.removeLast()

                for (i in 0 until directions.size - 1) {
                    val nr = r + directions[i]
                    val nc = c + directions[i + 1]

                    if (nr in grid.indices &&
                        nc in grid[0].indices &&
                        grid[nr][nc] == 1
                    ) {
                        stack.addLast(Cell(nr, nc))
                        grid[nr][nc] = 0
                    }
                }
            }
            return area
        }

        var maxArea = 0
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == 1) {
                    maxArea = maxOf(maxArea, dfs(r, c))
                }
            }
        }
        return maxArea
    }
}

class MaxAreaOfIslandBfs {
    fun maxAreaOfIsland(grid: Array<IntArray>): Int {
        val rows = grid.size
        val cols = grid[0].size
        val directions = intArrayOf(-1, 0, 1, 0, -1)

        fun bfs(sr: Int, sc: Int): Int {
            var area = 0
            val queue = ArrayDeque<Pair<Int, Int>>()
            queue.addLast(sr to sc)
            grid[sr][sc] = 0

            while (queue.isNotEmpty()) {
                val (r, c) = queue.removeFirst()
                area++

                for (i in 0 until 4) {
                    val nr = r + directions[i]
                    val nc = c + directions[i + 1]

                    if (nr in 0 until rows && nc in 0 until cols && grid[nr][nc] == 1) {
                        grid[nr][nc] = 0
                        queue.addLast(nr to nc)
                    }
                }
            }

            return area
        }

        var best = 0

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == 1) {
                    best = maxOf(best, bfs(r, c))
                }
            }
        }

        return best
    }
}

class MaxAreaOfIslandTest {

    private val impls = listOf<(Array<IntArray>) -> Int>(
//        MaxAreaOfIslandDfsRecursive()::maxAreaOfIsland,
        MaxAreaOfIslandDfsIterative()::maxAreaOfIsland,
    )

    @Test
    fun exampleCase() {
        val grid = arrayOf(
            intArrayOf(0, 0, 1, 0, 0, 0, 1, 1),
            intArrayOf(0, 1, 1, 0, 1, 0, 1, 1),
            intArrayOf(0, 0, 0, 0, 1, 0, 0, 0),
            intArrayOf(1, 1, 0, 0, 1, 1, 0, 0),
        )

        impls.forEach { f ->
            assertEquals(4, f(copyGrid(grid)))
        }
    }

    @Test
    fun allWater() {
        val grid = arrayOf(
            intArrayOf(0, 0),
            intArrayOf(0, 0),
        )

        impls.forEach { f ->
            assertEquals(0, f(copyGrid(grid)))
        }
    }

    @Test
    fun singleLandCell() {
        val grid = arrayOf(
            intArrayOf(1),
        )

        impls.forEach { f ->
            assertEquals(1, f(copyGrid(grid)))
        }
    }

    @Test
    fun allLand() {
        val grid = arrayOf(
            intArrayOf(1, 1, 1),
            intArrayOf(1, 1, 1),
        )

        impls.forEach { f ->
            assertEquals(6, f(copyGrid(grid)))
        }
    }

    @Test
    fun multipleSmallIslands() {
        val grid = arrayOf(
            intArrayOf(1, 0, 1, 0),
            intArrayOf(0, 1, 0, 1),
            intArrayOf(1, 0, 1, 0),
        )

        impls.forEach { f ->
            assertEquals(1, f(copyGrid(grid)))
        }
    }

    @Test
    fun longConnectedIsland() {
        val grid = arrayOf(
            intArrayOf(1, 1, 1, 1, 1),
        )

        impls.forEach { f ->
            assertEquals(5, f(copyGrid(grid)))
        }
    }

    private fun copyGrid(grid: Array<IntArray>): Array<IntArray> {
        return Array(grid.size) { r -> grid[r].clone() }
    }
}