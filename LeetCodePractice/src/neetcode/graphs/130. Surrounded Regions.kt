package neetcode.graphs

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test

class SurroundedRegionsBfs {
    fun solve(board: Array<CharArray>) {
        if (board.isEmpty() || board[0].isEmpty()) return
        data class Cell(val row: Int, val col: Int)

        val rows = board.size
        val cols = board[0].size
        val que = ArrayDeque<Cell>()

        fun addBorderCell(r: Int, c: Int) {
            if (board[r][c] == '0') {
                board[r][c] = '#'
                que.addLast(Cell(r, c))
            }
        }
        //scan 1st and last columns cells
        for (r in 0 until rows) {
            addBorderCell(r, 0)
            addBorderCell(r, cols - 1)
        }
        //scan 1st and last rows cells
        for (c in 0 until cols) {
            addBorderCell(0, c)
            addBorderCell(rows - 1, c)
        }
        //bfs
        val dir = intArrayOf(1, 0, -1, 0, 1)
        while (que.isNotEmpty()) {
            val (r, c) = que.removeFirst()
            for (i in 0 until 4) {
                val nr = r + dir[i]
                val nc = c + dir[i + 1]

                if (nr in 0 until rows &&
                    nc in 0 until cols &&
                    board[nr][nc] == '0'
                ) {
                    board[nr][nc] = '#'
                    que.addLast(Cell(nr, nc))
                }
            }
        }

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                board[r][c] = when (board[r][c]) {
                    '0' -> 'X'
                    '#' -> '0'
                    else -> board[r][c]
                }
            }
        }
    }
}

class SurroundedRegionsTest {

    private val impls = listOf(
        SurroundedRegionsBfs()::solve
    )

    @Test
    fun example1() {
        impls.forEach { solve ->
            val board = arrayOf(
                charArrayOf('X', 'X', 'X', 'X'),
                charArrayOf('X', 'O', 'O', 'X'),
                charArrayOf('X', 'X', 'O', 'X'),
                charArrayOf('X', 'O', 'X', 'X')
            )

            solve(board)

            val expected = arrayOf(
                charArrayOf('X', 'X', 'X', 'X'),
                charArrayOf('X', 'X', 'X', 'X'),
                charArrayOf('X', 'X', 'X', 'X'),
                charArrayOf('X', 'O', 'X', 'X')
            )

            assertBoardEquals(expected, board)
        }
    }

    @Test
    fun singleCellO() {
        impls.forEach { solve ->
            val board = arrayOf(
                charArrayOf('O')
            )

            solve(board)

            val expected = arrayOf(
                charArrayOf('O')
            )

            assertBoardEquals(expected, board)
        }
    }

    @Test
    fun singleCellX() {
        impls.forEach { solve ->
            val board = arrayOf(
                charArrayOf('X')
            )

            solve(board)

            val expected = arrayOf(
                charArrayOf('X')
            )

            assertBoardEquals(expected, board)
        }
    }

    @Test
    fun allOsShouldRemainBecauseConnectedToBorder() {
        impls.forEach { solve ->
            val board = arrayOf(
                charArrayOf('O', 'O', 'O'),
                charArrayOf('O', 'O', 'O'),
                charArrayOf('O', 'O', 'O')
            )

            solve(board)

            val expected = arrayOf(
                charArrayOf('O', 'O', 'O'),
                charArrayOf('O', 'O', 'O'),
                charArrayOf('O', 'O', 'O')
            )

            assertBoardEquals(expected, board)
        }
    }

    @Test
    fun surroundedRegionCaptured() {
        impls.forEach { solve ->
            val board = arrayOf(
                charArrayOf('X', 'X', 'X'),
                charArrayOf('X', 'O', 'X'),
                charArrayOf('X', 'X', 'X')
            )

            solve(board)

            val expected = arrayOf(
                charArrayOf('X', 'X', 'X'),
                charArrayOf('X', 'X', 'X'),
                charArrayOf('X', 'X', 'X')
            )

            assertBoardEquals(expected, board)
        }
    }

    @Test
    fun borderConnectedRegionShouldNotBeCaptured() {
        impls.forEach { solve ->
            val board = arrayOf(
                charArrayOf('X', 'O', 'X', 'X'),
                charArrayOf('X', 'O', 'O', 'X'),
                charArrayOf('X', 'X', 'O', 'X'),
                charArrayOf('X', 'X', 'X', 'X')
            )

            solve(board)

            val expected = arrayOf(
                charArrayOf('X', 'O', 'X', 'X'),
                charArrayOf('X', 'O', 'O', 'X'),
                charArrayOf('X', 'X', 'O', 'X'),
                charArrayOf('X', 'X', 'X', 'X')
            )

            assertBoardEquals(expected, board)
        }
    }

    @Test
    fun mixedCapturedAndSafeRegions() {
        impls.forEach { solve ->
            val board = arrayOf(
                charArrayOf('O', 'X', 'X', 'X', 'O'),
                charArrayOf('X', 'O', 'O', 'X', 'X'),
                charArrayOf('X', 'X', 'O', 'X', 'X'),
                charArrayOf('X', 'O', 'X', 'O', 'X'),
                charArrayOf('O', 'X', 'X', 'X', 'O')
            )

            solve(board)

            val expected = arrayOf(
                charArrayOf('O', 'X', 'X', 'X', 'O'),
                charArrayOf('X', 'X', 'X', 'X', 'X'),
                charArrayOf('X', 'X', 'X', 'X', 'X'),
                charArrayOf('X', 'X', 'X', 'X', 'X'),
                charArrayOf('O', 'X', 'X', 'X', 'O')
            )

            assertBoardEquals(expected, board)
        }
    }

    private fun assertBoardEquals(
        expected: Array<CharArray>,
        actual: Array<CharArray>
    ) {
        assertArrayEquals(expected, actual)
    }
}