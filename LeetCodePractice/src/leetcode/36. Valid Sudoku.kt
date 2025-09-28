package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun isValidSudoku(board: Array<CharArray>): Boolean {
    val rows = Array(9) { mutableSetOf<Char>() }
    val cols = Array(9) { mutableSetOf<Char>() }
    val boxes = Array(9) { mutableSetOf<Char>() }
    board.forEachIndexed { row, chars ->
        chars.forEachIndexed { col, ch ->
            if (ch in '1'..'9') {
                if (!rows[row].add(ch)) return false
                if (!cols[col].add(ch)) return false
                val boxIdx = ((row / 3) * 3) + (col / 3)
                if (!boxes[boxIdx].add(ch)) return false
            }
        }
    }
    return true
}

class ValidSudokuTests {
    val str = """
    Input: board = 
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
    """

    @Test
    fun `should return true when given valid input`() {
        assertEquals(
            false,
            isValidSudoku(
                arrayOf(
                    //          ["5","3",".",".","7",".",".",".","."]
                    charArrayOf('5', '3', '.', '.', '7', '.', '.', '.', '.'),
                    //          ["6",".",".","1","9","5",".",".","."]
                    charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
                    //          [".","9","8",".",".",".",".","6","."]
                    charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
                    //          ["8",".",".",".","6",".",".",".","3"]
                    charArrayOf('8', '.', '.', '.', '6', '.', '.', '.', '3'),
                    //           ["4",".",".","8",".","3",".",".","1"]
                    charArrayOf('4', '.', '.', '8', '.', '3', '.', '.', '1'),
                    //           ["7",".",".",".","2",".",".",".","6"]
                    charArrayOf('7', '.', '.', '.', '2', '.', '.', '.', '6'),
                    //         [".","6", ".",".",".",".","2","8","."]
                    charArrayOf('.', '6', '.', '.', '.', '.', '2', '8', '.'),
                    //          [".",".",".","4","1","9",".",".","5"]
                    charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
                    //          [".",".",".","4","1","9",".",".","5"]
                    charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
                )
            )
        )
    }

}