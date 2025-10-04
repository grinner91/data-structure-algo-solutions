package neetcode.backtracking

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

//https://leetcode.com/problems/word-search/
//https://neetcode.io/problems/search-for-word

class SolutionExistWord {
    fun exist(board: Array<CharArray>, word: String): Boolean {
        val rows = board.size
        val cols = board[0].size
        val path = mutableSetOf<Pair<Int, Int>>()
        fun dfs(r: Int, c: Int, i: Int): Boolean {
            if (i == word.length) return true
            val pair = Pair(r, c)
            if (r < 0 || c < 0 || r >= rows || c >= cols
                || board[r][c] != word[i]
                || pair in path
            ) return false
            path.add(pair)
            val res = dfs(r + 1, c, i + 1)
                    || dfs(r - 1, c, i + 1)
                    || dfs(r, c + 1, i + 1)
                    || dfs(r, c - 1, i + 1)
            path.remove(pair)
            return res
        }
        for (r in board.indices) {
            for (c in board[0].indices) {
                if (dfs(r, c, 0)) return true
            }
        }
        return false
    }
}

//chatgpt unit tests
class WordSearchTest {

    private val sut = SolutionExistWord()

    @Test
    fun example1() {
        val board = arrayOf(
            charArrayOf('A', 'B', 'C', 'E'),
            charArrayOf('S', 'F', 'C', 'S'),
            charArrayOf('A', 'D', 'E', 'E')
        )
        assertTrue(sut.exist(board.map { it.clone() }.toTypedArray(), "ABCCED"))
        assertTrue(sut.exist(board.map { it.clone() }.toTypedArray(), "SEE"))
        assertFalse(sut.exist(board.map { it.clone() }.toTypedArray(), "ABCB"))
    }

    @Test
    fun singleLetterBoard() {
        val board = arrayOf(charArrayOf('a'))
        assertTrue(sut.exist(board.map { it.clone() }.toTypedArray(), "a"))
        assertFalse(sut.exist(board.map { it.clone() }.toTypedArray(), "aa"))
    }

    @Test
    fun requiresNonRevisit() {
        val board = arrayOf(
            charArrayOf('a', 'b'),
            charArrayOf('c', 'd')
        )
        // "aba" would require revisiting 'a'; should be false
        assertFalse(sut.exist(board.map { it.clone() }.toTypedArray(), "aba"))
    }

    @Test
    fun frequencyPruneFastFail() {
        val board = arrayOf(
            charArrayOf('X', 'Y'),
            charArrayOf('Z', 'W')
        )
        // Board has no 'Q' at all, should fail quickly
        assertFalse(sut.exist(board.map { it.clone() }.toTypedArray(), "QQQ"))
    }
}
