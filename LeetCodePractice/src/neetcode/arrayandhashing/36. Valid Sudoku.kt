package neetcode.arrayandhashing

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SolutionHashSet {
    //TC O(N^2) and SC O(N^2)
    fun isValidSudoku(board: Array<CharArray>): Boolean {
        val n = 9
        val cols = Array(n) { HashSet<Char>() }
        val rows = Array(n) { HashSet<Char>() }
        val boxs = Array(n) { HashSet<Char>() }

        for (r in 0 until n) {
            for (c in 0 until n) {
                val ch = board[r][c]
                if (ch == '.') continue
                val bid = (r / 3) * 3 + c / 3
                if (!rows[r].add(ch)
                    || !cols[c].add(ch)
                    || !boxs[bid].add(ch)
                ) return false
            }
        }
        return true
    }
}

class SolutionBitmask {
    fun isValidSudoku(board: Array<CharArray>): Boolean {
        val R = board.size
        val C = board[0].size
        val rows = IntArray(9)
        val cols = IntArray(9)
        val boxs = IntArray(9)
        for (r in 0 until R) {
            for (c in 0 until C) {
                val ch = board[r][c]
                if (ch == '.') continue

                val mask = 1 shl (ch - '1') // 0 <- 1, 1 <- 2, ... bit positions
                val boxId = (r / 3) * 3 + c / 3
                if ((rows[r] and mask) != 0
                    || (cols[c] and mask) != 0
                    || (boxs[boxId] and mask) != 0
                )
                    return false

                rows[r] = rows[r] or mask
                cols[c] = cols[c] or mask
                boxs[boxId] = boxs[boxId] or mask
            }
        }
        return true
    }
}

class ValidSudokuTest {

    private fun boardOf(vararg rows: String): Array<CharArray> {
        require(rows.size == 9) { "Need 9 rows" }
        return Array(9) { i ->
            val s = rows[i]
            require(s.length == 9) { "Each row must be length 9" }
            s.toCharArray()
        }
    }

    private val validBoard = boardOf(
        "53..7....",
        "6..195...",
        ".98....6.",
        "8...6...3",
        "4..8.3..1",
        "7...2...6",
        ".6....28.",
        "...419..5",
        "....8..79"
    )

    @Test
    fun `valid sudoku returns true`() {
        val solutions = listOf(
            SolutionHashSet()::isValidSudoku,
            SolutionBitmask()::isValidSudoku
        )

        for (fn in solutions) {
            assertTrue(fn(validBoard))
        }
    }

    @Test
    fun `duplicate in row returns false`() {
        val b = boardOf(
            "53..7...5", // duplicate '5' in row 0
            "6..195...",
            ".98....6.",
            "8...6...3",
            "4..8.3..1",
            "7...2...6",
            ".6....28.",
            "...419..5",
            "....8..79"
        )

        val solutions = listOf(
            SolutionHashSet()::isValidSudoku,
            SolutionBitmask()::isValidSudoku
        )
        for (fn in solutions) {
            assertFalse(fn(b))
        }
    }

    @Test
    fun `duplicate in column returns false`() {
        val b = boardOf(
            "53..7....",
            "6..195...",
            ".98....6.",
            "8...6...3",
            "4..8.3..1",
            "7...2...6",
            ".6....28.",
            "...419..5",
            "....8..76" // column 8 now has '6' twice (row2 col8 is '6')
        )

        val solutions = listOf(
            SolutionHashSet()::isValidSudoku,
            SolutionBitmask()::isValidSudoku
        )
        for (fn in solutions) {
            assertFalse(fn(b))
        }
    }

    @Test
    fun `duplicate in 3x3 box returns false`() {
        val b = boardOf(
            "53..7....",
            "6..195...",
            ".98....6.",
            "8...6...3",
            "4..8.3..1",
            "7...2...6",
            ".6....28.",
            "...419..5",
            "....8..75" // put '5' in bottom-right box (box 8) duplicates existing '5' at row7 col8
        )

        val solutions = listOf(
            SolutionHashSet()::isValidSudoku,
            SolutionBitmask()::isValidSudoku
        )
        for (fn in solutions) {
            assertFalse(fn(b))
        }
    }
}

