package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NumberOfIslandsDFS {
    fun numIslands(grid: Array<CharArray>): Int {
        val rows = grid.size
        val cols = grid[0].size

        var islands = 0
        fun dfs(r: Int, c: Int) {
            if (r < 0 || c < 0 || r >= rows || c >= cols
                || grid[r][c] == '0'
            ) {
                return
            }
            grid[r][c] = '0'
            dfs(r + 1, c)
            dfs(r - 1, c)
            dfs(r, c + 1)
            dfs(r, c - 1)
        }

        for (i in grid.indices) {
            for (j in grid[0].indices) {
                if (grid[i][j] == '1') {
                    islands++
                    dfs(i, j)
                }
            }
        }
        return islands
    }
}


class NumberOfIslandsBFS {
    fun numIslands(grid: Array<CharArray>): Int {
        val rows = grid.size
        val cols = grid[0].size
        val directions = arrayOf(
            intArrayOf(1, 0),
            intArrayOf(-1, 0),
            intArrayOf(0, 1),
            intArrayOf(0, -1),
        )

        fun bfs(sr: Int, sc: Int) {
            val que = ArrayDeque<Pair<Int, Int>>()
            que.addLast(sr to sc)
            grid[sr][sc] = '0'
            while (que.isNotEmpty()) {
                val (r, c) = que.removeFirst()
                for (dir in directions) {
                    val nr = r + dir[0]
                    val nc = c + dir[1]
                    if (nr in 0 until rows
                        && nc in 0 until cols
                        && grid[nr][nc] == '1'
                    ) {
                        que.addLast(nr to nc)
                        grid[nr][nc] = '0'
                    }
                }
            }
        }

        var islands = 0
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == '1') {
                    islands++
                    bfs(r, c)
                }
            }
        }
        return islands
    }
}

class NumberOfIslandsUnionFind {
    class DisjointUnionSet(n: Int) {
        private val parent = IntArray(n + 1) { it }
        private val rank = IntArray(n + 1) { 1 }
        private fun find(node: Int): Int {
            if (parent[node] != node) {
                parent[node] = find(parent[node])
            }
            return parent[node]
        }

        fun union(u: Int, v: Int): Boolean {
            val pu = find(u)
            val pv = find(v)
            if (pu == pv) return false
            if (rank[pu] >= rank[pv]) {
                rank[pu] += rank[pv]
                parent[pv] = pu
            } else {
                rank[pv] += rank[pu]
                parent[pu] = pv
            }
            return true
        }
    }

    fun numIslands(grid: Array<CharArray>): Int {
        val rows = grid.size
        val cols = grid[0].size
        val dsu = DisjointUnionSet(rows * cols)
        val dirs = arrayOf(
            intArrayOf(1, 0),
            intArrayOf(-1, 0),
            intArrayOf(0, 1),
            intArrayOf(0, -1),
        )
        var islands = 0
        fun index(r: Int, c: Int) = r * cols + c
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                if (grid[r][c] == '1') {
                    islands++
                    for (dir in dirs) {
                        val nr = r + dir[0]
                        val nc = c + dir[1]
                        if (nr < 0 || nr >= rows
                            || nc < 0 || nc >= cols
                            || grid[nr][nc] == '0'
                        ) continue
                        if (dsu.union(index(r, c), index(nr, nc))) {
                            islands--
                        }
                    }
                }
            }
        }
        return islands
    }
}


class NumberOfIslandsTest {
    private val sut = NumberOfIslandsUnionFind() //NumberOfIslandsBFS()
    private fun Array<CharArray>.deepCopy(): Array<CharArray> =
        Array(size) { i -> this[i].clone() }

    @Test
    fun `example case`() {
        val grid = arrayOf(
            charArrayOf('1', '1', '1', '1', '0'),
            charArrayOf('1', '1', '0', '1', '0'),
            charArrayOf('1', '1', '0', '0', '0'),
            charArrayOf('0', '0', '0', '0', '0')
        )
        assertEquals(1, sut.numIslands(grid.deepCopy()))

    }

    @Test
    fun `multiple islands`() {
        val grid = arrayOf(
            charArrayOf('1', '1', '0', '0', '0'),
            charArrayOf('1', '1', '0', '0', '0'),
            charArrayOf('0', '0', '1', '0', '0'),
            charArrayOf('0', '0', '0', '1', '1')
        )
        assertEquals(3, sut.numIslands(grid.deepCopy()))
    }

    @Test
    fun `single cell land and water`() {
        val land = arrayOf(charArrayOf('1'))
        val water = arrayOf(charArrayOf('0'))
        assertEquals(1, sut.numIslands(land.deepCopy()))
    }

    @Test
    fun `all water`() {
        val grid = arrayOf(
            charArrayOf('0', '0'),
            charArrayOf('0', '0')
        )
        assertEquals(0, sut.numIslands(grid.deepCopy()))

    }

    @Test
    fun `snake island`() {
        val grid = arrayOf(
            charArrayOf('1', '0', '1', '0', '1', '0'),
            charArrayOf('0', '1', '0', '1', '0', '1'),
            charArrayOf('1', '0', '1', '0', '1', '0'),
        )
        // No diagonal connections; each '1' stands alone.
        assertEquals(9, sut.numIslands(grid.deepCopy()))
    }

    @Test
    fun `rectangular grid`() {
        val grid = arrayOf(
            charArrayOf('1', '1', '0'),
            charArrayOf('0', '1', '0')
        )
        assertEquals(1, sut.numIslands(grid.deepCopy()))
    }
}


