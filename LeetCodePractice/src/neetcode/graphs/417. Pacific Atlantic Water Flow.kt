package neetcode.graphs
// src/test/kotlin/PacificAtlanticWaterFlowTest.kt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class SolutionPacificAtlanticDFS {
    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
        if (heights.isEmpty() || heights[0].isEmpty()) return emptyList()

        val rows = heights.size
        val cols = heights[0].size

        fun dfs(r: Int, c: Int, visited: Array<BooleanArray>, prevHeight: Int) {
            if (r !in 0 until rows
                || c !in 0 until cols
                || visited[r][c]
                || heights[r][c] < prevHeight
            ) {
                return
            }

            visited[r][c] = true

            dfs(r + 1, c, visited, heights[r][c])
            dfs(r - 1, c, visited, heights[r][c])
            dfs(r, c + 1, visited, heights[r][c])
            dfs(r, c - 1, visited, heights[r][c])
        }

        val pacVisited = Array(rows) { BooleanArray(cols) }
        val atlVisited = Array(rows) { BooleanArray(cols) }


        for (c in 0 until cols) {
            dfs(0, c, pacVisited, heights[0][c])
            dfs(rows - 1, c, atlVisited, heights[rows - 1][c])
        }
        for (r in 0 until rows) {
            dfs(r, 0, pacVisited, heights[r][0])
            dfs(r, cols - 1, atlVisited, heights[r][cols - 1])
        }
        val result = mutableListOf<List<Int>>()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (pacVisited[r][c] && atlVisited[r][c]) {
                    result.add(listOf(r, c))
                }
            }
        }
        return result
    }
//    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
//        if (heights.isEmpty() || heights[0].isEmpty()) return emptyList()
//
//        val rows = heights.size
//        val cols = heights[0].size
//        val pac = mutableSetOf<Pair<Int, Int>>()
//        val atl = mutableSetOf<Pair<Int, Int>>()
//        val dir = intArrayOf(1, 0, -1, 0, 1)
//
//        fun dfs(r: Int, c: Int, visited: MutableSet<Pair<Int, Int>>) {
//            visited.add(r to c)
//            for (i in 0..3) {
//                val nr = r + dir[i]
//                val nc = c + dir[i + 1]
//                if (nr in 0 until rows
//                    && nc in 0 until cols
//                    && (nr to nc) !in visited
//                    && heights[nr][nc] >= heights[r][c]
//                ) {
//                    dfs(nr, nc, visited)
//                }
//            }
//        }
//        for (c in 0 until cols) {
//            dfs(0, c, pac)
//            dfs(rows - 1, c, atl)
//        }
//        for (r in 0 until rows) {
//            dfs(r, 0, pac)
//            dfs(r, cols - 1, atl)
//        }
//        val result = mutableListOf<List<Int>>()
//        for (r in 0 until rows) {
//            for (c in 0 until cols) {
//                val rc = r to c
//                if (rc in pac && rc in atl) {
//                    result.add(listOf(r, c))
//                }
//            }
//        }
//        return result
//    }
}

class SolutionPacificAtlanticBFS {
    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
        if (heights.isEmpty() || heights[0].isEmpty()) return emptyList()

        val rows = heights.size
        val cols = heights[0].size
        val dir = intArrayOf(1, 0, -1, 0, 1)
        fun bfs(sr: Int, sc: Int, visited: Array<BooleanArray>) {
            if (visited[sr][sc]) return
            val deq = ArrayDeque<Pair<Int, Int>>()
            visited[sr][sc] = true
            deq.addLast(sr to sc)
            while (deq.isNotEmpty()) {
                val (r, c) = deq.removeFirst()
                for (i in 0..3) {
                    val nr = r + dir[i]
                    val nc = c + dir[i + 1]
                    if (nr in 0 until rows
                        && nc in 0 until cols
                        && !visited[nr][nc]
                        && heights[nr][nc] >= heights[r][c]
                    ) {
                        visited[nr][nc] = true
                        deq.addLast(nr to nc)
                    }
                }
            }
        }

        val pacVisited = Array(rows) { BooleanArray(cols) }
        val atlVisited = Array(rows) { BooleanArray(cols) }
        for (c in 0 until cols) {
            bfs(0, c, pacVisited)
            bfs(rows - 1, c, atlVisited)
        }
        for (r in 0 until rows) {
            bfs(r, 0, pacVisited)
            bfs(r, cols - 1, atlVisited)
        }
        val result = mutableListOf<List<Int>>()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (pacVisited[r][c] && atlVisited[r][c]) {
                    result.add(listOf(r, c))
                }
            }
        }
        return result
    }
}


/***************************/
class PacificAtlanticWaterFlowTest {

    // --------- Helpers ---------

    private fun toSet(points: List<List<Int>>): Set<Pair<Int, Int>> =
        points.map { (r, c) -> r to c }.toSet()

    private fun sorted(points: List<List<Int>>): List<List<Int>> =
        points.sortedWith(compareBy({ it[0] }, { it[1] }))

    // --------- Tests ---------

    //private val sut = SolutionPacificAtlanticDFS()
    private val sut = SolutionPacificAtlanticBFS()

    @Test
    fun `example from LeetCode`() {
        val heights = arrayOf(
            intArrayOf(1, 2, 2, 3, 5),
            intArrayOf(3, 2, 3, 4, 4),
            intArrayOf(2, 4, 5, 3, 1),
            intArrayOf(6, 7, 1, 4, 5),
            intArrayOf(5, 1, 1, 2, 4),
        )

        val res = sut.pacificAtlantic(heights)
        val got = toSet(res)

        val expected = setOf(
            0 to 4, 1 to 3, 1 to 4, 2 to 2, 3 to 0, 3 to 1, 4 to 0
        )
        assertEquals(expected, got)
    }

    @Test
    fun `single cell grid`() {
        val heights = arrayOf(intArrayOf(10))
        val res = sut.pacificAtlantic(heights)
        assertEquals(listOf(listOf(0, 0)), res)
    }

    @Test
    fun `strictly increasing rows`() {
        val heights = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(1, 2, 3),
            intArrayOf(1, 2, 3)
        )
        val got = toSet(sut.pacificAtlantic(heights))
        // Every cell in first column touches Pacific; last column touches Atlantic; due to monotonicity,
        // all cells can reach both.
        val expected = buildSet {
            for (r in 0..2) for (c in 0..2) add(r to c)
        }
        assertEquals(expected, got)
    }


    @Test
    fun `plateau with walls`() {
        val heights = arrayOf(
            intArrayOf(5, 5, 5, 5),
            intArrayOf(5, 1, 1, 5),
            intArrayOf(5, 1, 1, 5),
            intArrayOf(5, 5, 5, 5)
        )
        val got = toSet(sut.pacificAtlantic(heights))
        // The 1's are lower, so water inside cannot climb to borders; but borders are all 5's, touching both oceans.
        val expected = buildSet {
            val border = listOf(
                0 to 0, 0 to 1, 0 to 2, 0 to 3,
                1 to 0, 1 to 3,
                2 to 0, 2 to 3,
                3 to 0, 3 to 1, 3 to 2, 3 to 3
            )
            addAll(border)
        }
        assertEquals(expected, got)
    }


    @Test
    fun `empty grid returns empty list`() {
        val heights = emptyArray<IntArray>()
        val res = sut.pacificAtlantic(heights)
        assertTrue(res.isEmpty())
    }

    @Test
    fun `order does not matter but is stable when sorted`() {
        val heights = arrayOf(
            intArrayOf(1, 2),
            intArrayOf(4, 3)
        )
        val res = sut.pacificAtlantic(heights)
        // Sorting to show stable form (not required by problem, useful for code reviews)
        val s = sorted(res)
        // Just ensure it has the expected coordinates (set equality)
        val expectedSet = setOf(0 to 1, 1 to 0, 1 to 1)
        assertEquals(expectedSet, toSet(s))
    }
}

