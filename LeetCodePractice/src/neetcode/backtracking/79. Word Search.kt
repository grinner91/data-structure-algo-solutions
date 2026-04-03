package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
/*
Total work = (m * n) * (DFS cost per start)
           = (m * n) * O(3^L)

           3 - directions after first cell
           L - is length of word
* */
class WordSearchDfsInPlaceMarking {
    fun exist(board: Array<CharArray>, word: String): Boolean {
        val rows = board.size - 1
        val cols = board[0].size - 1

        fun dfs(r: Int, c: Int, i: Int): Boolean {
            if (i == word.length) return true
            if (r !in 0..rows || c !in 0..cols) return false
            if (board[r][c] != word[i]) return false

            val ch = board[r][c]
            board[r][c] = '#'
            val found = dfs(r + 1, c, i + 1) ||
                    dfs(r - 1, c, i + 1) ||
                    dfs(r, c + 1, i + 1) ||
                    dfs(r, c - 1, i + 1)

            board[r][c] = ch
            return found
        }

        for (r in 0..rows) {
            for (c in 0..cols) {
                if (dfs(r, c, 0)) return true
            }
        }

        return false
    }
}

class WordSearchTest {

    private val impls = listOf<(Array<CharArray>, String) -> Boolean>(
        WordSearchDfsInPlaceMarking()::exist,
    )

    @Test
    fun example1() {
        impls.forEach { exist ->
            val board = boardOf(
                "ABCE",
                "SFCS",
                "ADEE"
            )
            assertTrue(exist(board, "ABCCED"))
        }
    }

    @Test
    fun example2() {
        impls.forEach { exist ->
            val board = boardOf(
                "ABCE",
                "SFCS",
                "ADEE"
            )
            assertTrue(exist(board, "SEE"))
        }
    }

    @Test
    fun example3() {
        impls.forEach { exist ->
            val board = boardOf(
                "ABCE",
                "SFCS",
                "ADEE"
            )
            assertFalse(exist(board, "ABCB"))
        }
    }

    @Test
    fun singleCellMatch() {
        impls.forEach { exist ->
            val board = boardOf("A")
            assertTrue(exist(board, "A"))
        }
    }

    @Test
    fun singleCellNoMatch() {
        impls.forEach { exist ->
            val board = boardOf("A")
            assertFalse(exist(board, "B"))
        }
    }

    @Test
    fun cannotReuseSameCell() {
        impls.forEach { exist ->
            val board = boardOf(
                "AA"
            )
            assertFalse(exist(board, "AAA"))
        }
    }

    @Test
    fun pathTurnsMultipleTimes() {
        impls.forEach { exist ->
            val board = boardOf(
                "ABC",
                "DEF",
                "GHI"
            )
            assertTrue(exist(board, "ABCFI"))
        }
    }

    @Test
    fun wordLongerThanCellCount() {
        impls.forEach { exist ->
            val board = boardOf(
                "AB",
                "CD"
            )
            assertFalse(exist(board, "ABCDE"))
        }
    }

    @Test
    fun repeatedLettersValidPath() {
        impls.forEach { exist ->
            val board = boardOf(
                "CAA",
                "AAA",
                "BCD"
            )
            assertTrue(exist(board, "AAB"))
        }
    }

    private fun boardOf(vararg rows: String): Array<CharArray> {
        return Array(rows.size) { r -> rows[r].toCharArray() }
    }
}