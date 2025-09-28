package leetcode

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun isValid(r: Int, c: Int, board: Array<IntArray>): Boolean {
    return (r in board.indices && c in board[0].indices)
}

fun countLives(r: Int, c: Int, board: Array<IntArray>): Int {
    // Pair(row, col)
    val neighbors = listOf(
        Pair(-1, 0), Pair(-1, 1), Pair(0, 1), Pair(1, 1),
        Pair(1, 0), Pair(1, -1), Pair(0, -1), Pair(-1, -1)
    )

    var lives = 0
    neighbors.forEach { (dr, dc) ->
        val newRow = r + dr
        val newCol = c + dc
        if (isValid(newRow, newCol, board)
            && board[newRow][newCol] == 1
        ) {
            lives++
        }
    }
    return lives
}

fun gameOfLife(board: Array<IntArray>): Unit {
    val gameBoard = Array(board.size) { r ->
        board[r].copyOf()
    }

    for (r in gameBoard.indices) {
        for (c in gameBoard[0].indices) {
            val lives = countLives(r, c, gameBoard)
            //Rule 1
            //Rule 3
            if (lives < 2 || lives > 3) {
                board[r][c] = 0
            }
            //Rule 2
            else if (gameBoard[r][c] == 1) {
                board[r][c] = 1
            }
            //Rule 4
            else if (gameBoard[r][c] == 0 && lives == 3) {
                board[r][c] = 1
            }
        }
    }
}

class GameOfLife {
    @Test
    fun `should return correct when given 4x3 board`() {
        val expected = arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(1, 0, 1),
            intArrayOf(0, 1, 1),
            intArrayOf(0, 1, 0),
        )
        val actual =
            arrayOf(
                intArrayOf(0, 1, 0),
                intArrayOf(0, 0, 1),
                intArrayOf(1, 1, 1),
                intArrayOf(0, 0, 0),
            )

        gameOfLife(actual)

        assertTrue(
            expected.contentDeepEquals(actual)
        )
    }

    @Test
    fun `should return correct when given 2x2 board`() {
        val expected = arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 1),
        )
        val actual =
            arrayOf(
                intArrayOf(1, 1),
                intArrayOf(1, 0),
            )

        gameOfLife(actual)

        assertTrue(
            expected.contentDeepEquals(actual)
        )
    }
}