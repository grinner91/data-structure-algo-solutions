package neetcode.tries_prefix_tree

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
TC O(m*n*4*3^t-1+s)
SC O(S)
Where
m is the number of rows,
n is the number of columns,
t is the maximum length of any word in the array
s is the sum of the lengths of all the words.
* */

class WordSearchIIDfsTrieHashMap {
    private class TrieNode {
        val children = HashMap<Char, TrieNode>()
        var word: String? = null
    }

    fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {
        val root = buildTrie(words)
        val result = mutableListOf<String>()

        fun dfs(r: Int, c: Int, parent: TrieNode) {
            if (r !in board.indices || c !in board[0].indices) return

            val ch = board[r][c]
            if (ch == '#') return
            val node = parent.children[ch] ?: return

            node.word?.let {
                result.add(it)
                node.word = null
            }

            board[r][c] = '#'
            dfs(r - 1, c, node)
            dfs(r + 1, c, node)
            dfs(r, c - 1, node)
            dfs(r, c + 1, node)

            board[r][c] = ch
        }

        for (r in board.indices) {
            for (c in board[0].indices) {
                dfs(r, c, root)
            }
        }

        return result
    }

    private fun buildTrie(words: Array<String>): TrieNode {
        val root = TrieNode()

        for (wrd in words) {
            var cur = root
            for (ch in wrd) {
              cur =  cur.children.getOrPut(ch) { TrieNode() }
            }
            cur.word = wrd
        }

        return root
    }
}

class WordSearchIITest {
    private val impls = listOf(
        WordSearchIIDfsTrieHashMap()::findWords,
    )

    @Test
    fun example1() {
        val board = arrayOf(
            charArrayOf('o', 'a', 'a', 'n'),
            charArrayOf('e', 't', 'a', 'e'),
            charArrayOf('i', 'h', 'k', 'r'),
            charArrayOf('i', 'f', 'l', 'v'),
        )
        val words = arrayOf("oath", "pea", "eat", "rain")

        impls.forEach { findWords ->
            val actual = findWords(copyBoard(board), words).toSet()
            val expected = setOf("oath", "eat")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun example2() {
        val board = arrayOf(
            charArrayOf('a', 'b'),
            charArrayOf('c', 'd'),
        )
        val words = arrayOf("abcb")

        impls.forEach { findWords ->
            val actual = findWords(copyBoard(board), words).toSet()
            val expected = emptySet<String>()
            assertEquals(expected, actual)
        }
    }

    @Test
    fun singleCellBoard() {
        val board = arrayOf(
            charArrayOf('a'),
        )
        val words = arrayOf("a", "b", "aa")

        impls.forEach { findWords ->
            val actual = findWords(copyBoard(board), words).toSet()
            val expected = setOf("a")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun wordCanBeFoundOnlyOnceEvenIfMultiplePathsExist() {
        val board = arrayOf(
            charArrayOf('a', 'a'),
            charArrayOf('a', 'a'),
        )
        val words = arrayOf("aa", "aaa", "aaaa")

        impls.forEach { findWords ->
            val actual = findWords(copyBoard(board), words).toSet()
            val expected = setOf("aa", "aaa", "aaaa")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun emptyResult() {
        val board = arrayOf(
            charArrayOf('x', 'y'),
            charArrayOf('z', 'w'),
        )
        val words = arrayOf("abc", "def")

        impls.forEach { findWords ->
            val actual = findWords(copyBoard(board), words).toSet()
            val expected = emptySet<String>()
            assertEquals(expected, actual)
        }
    }

    private fun copyBoard(board: Array<CharArray>): Array<CharArray> {
        return Array(board.size) { row -> board[row].copyOf() }
    }
}