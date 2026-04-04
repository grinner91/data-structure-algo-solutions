package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//TC O(n!)
//SC O(n) + board
class NQueensBooleanArrays {
    fun solveNQueens(n: Int): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val board = Array(n) { CharArray(n) { '.' } }
        val usedCols = BooleanArray(n)
        val usedDiag = BooleanArray(2 * n - 1) //(r - c) + (n - 1)
        val usedAntiDiag = BooleanArray(2 * n - 1) // r + c

        fun backtrack(r: Int) {
            if (r == n) {
                result.add(board.map { String(it) })
                return
            }

            for (c in 0 until n) {
                val dia = r - c + (n - 1)
                val anti = r + c
                if (usedCols[c] || usedDiag[dia] || usedAntiDiag[anti]) continue

                board[r][c] = 'Q'
                usedCols[c] = true
                usedDiag[dia] = true
                usedAntiDiag[anti] = true

                backtrack(r + 1)

                board[r][c] = '.'
                usedCols[c] = false
                usedDiag[dia] = false
                usedAntiDiag[anti] = false
            }
        }
        backtrack(0)
        return result
    }
}

class NQueensTest {

    private val impls = listOf(
        NQueensBooleanArrays()::solveNQueens
    )

    @Test
    fun n1() {
        val expected = setOf(
            board(
                "Q"
            )
        )

        impls.forEach { solve ->
            assertEquals(expected, solve(1).toNormalizedSet())
        }
    }

    @Test
    fun n2() {
        impls.forEach { solve ->
            assertEquals(emptySet<List<String>>(), solve(2).toNormalizedSet())
        }
    }

    @Test
    fun n3() {
        impls.forEach { solve ->
            assertEquals(emptySet<List<String>>(), solve(3).toNormalizedSet())
        }
    }

    @Test
    fun n4() {
        val expected = setOf(
            board(
                ".Q..",
                "...Q",
                "Q...",
                "..Q."
            ),
            board(
                "..Q.",
                "Q...",
                "...Q",
                ".Q.."
            )
        )

        impls.forEach { solve ->
            assertEquals(expected, solve(4).toNormalizedSet())
        }
    }

    @Test
    fun n5Count() {
        impls.forEach { solve ->
            assertEquals(10, solve(5).size)
        }
    }

    @Test
    fun n6Count() {
        impls.forEach { solve ->
            assertEquals(4, solve(6).size)
        }
    }

    @Test
    fun everyReturnedBoardIsValidForN4() {
        impls.forEach { solve ->
            val boards = solve(4)
            boards.forEach { board ->
                assertBoardValid(board)
            }
        }
    }

    private fun board(vararg rows: String): List<String> = rows.toList()

    private fun List<List<String>>.toNormalizedSet(): Set<List<String>> =
        this.map { it.toList() }.toSet()

    private fun assertBoardValid(board: List<String>) {
        val n = board.size
        val queenPositions = mutableListOf<Pair<Int, Int>>()

        for (row in 0 until n) {
            for (col in 0 until n) {
                if (board[row][col] == 'Q') {
                    queenPositions.add(row to col)
                }
            }
        }

        assertEquals(n, queenPositions.size)

        for (i in queenPositions.indices) {
            for (j in i + 1 until queenPositions.size) {
                val (r1, c1) = queenPositions[i]
                val (r2, c2) = queenPositions[j]

                val sameRow = r1 == r2
                val sameCol = c1 == c2
                val sameDiag = kotlin.math.abs(r1 - r2) == kotlin.math.abs(c1 - c2)

                assertEquals(false, sameRow || sameCol || sameDiag)
            }
        }
    }
}