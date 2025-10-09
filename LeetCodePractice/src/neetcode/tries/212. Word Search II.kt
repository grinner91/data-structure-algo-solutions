package neetcode.tries

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

class SolutionWordSearchIITrie {
    class TrieNode1 {
        val children = HashMap<Char, TrieNode1>()
        var isWord = false
        fun addWord(word: String) {
            var cur = this
            for (ch in word) {
                cur = cur.children.getOrPut(ch) { TrieNode1() }
            }
            cur.isWord = true
        }
    }

    fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {
        val root = TrieNode1()
        words.forEach { root.addWord(it) }

        val rows = board.size
        val cols = board[0].size
        val result = mutableSetOf<String>()
        val visited = mutableSetOf<Pair<Int, Int>>()

        fun dfs(r: Int, c: Int, node: TrieNode1, word: String) {
            if (r < 0 || c < 0 || r >= rows || c >= cols
                || (r to c) in visited
                || board[r][c] !in node.children
            ) {
                return
            }
            visited.add((r to c))
            val ch = board[r][c]
            val nextNode = node.children[ch]!!
            val newWord = word + ch
            if (nextNode.isWord) {
                result.add(newWord)
            }
            dfs(r + 1, c, nextNode, newWord)
            dfs(r - 1, c, nextNode, newWord)
            dfs(r, c + 1, nextNode, newWord)
            dfs(r, c - 1, nextNode, newWord)
            visited.remove((r to c))
        }

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                dfs(r, c, root, "")
            }
        }

        return result.toList()
    }
}

class SolutionWordSearchIITrieTest {
    private val sut = SolutionWordSearchIITrie()
    private fun boardOf(vararg rows: String): Array<CharArray> =
        rows.map { it.toCharArray() }.toTypedArray()


    @Test
    fun example1_fromLeetCode() {
        val board = boardOf(
            "oaan",
            "etae",
            "ihkr",
            "iflv"
        )
        val words = arrayOf("oath", "pea", "eat", "rain")
        val expected = setOf("oath", "eat")
        val actual = sut.findWords(board, words).toSet()
        assertEquals(expected, actual)
    }

    @Test
    fun example2_singleMatch() {
        val board = boardOf(
            "ab",
            "cd"
        )
        val words = arrayOf("abcb") // cannot reuse the same cell
        val expected = emptySet<String>()
        val actual = sut.findWords(board, words).toSet()
        assertEquals(expected, actual)
    }

    @Test
    fun multipleOverlappingWords() {
        val board = boardOf(
            "aaaa",
            "aaaa",
            "aaaa"
        )
        val words = arrayOf("a", "aa", "aaa", "aaaa", "aaaaa")
        val actual = sut.findWords(board, words).toSet()
        // All except "aaaaa" are possible
        val expected = setOf("a", "aa", "aaa", "aaaa")
        assertEquals(expected, actual)
    }

    @Test
    fun wordsSharingPrefixes_pruningWorks() {
        val board = boardOf(
            "cat",
            "rdo",
            "gox"
        )
        val words = arrayOf("car", "card", "cat", "dog", "cart")
        val actual = sut.findWords(board, words).toSet()
        // "cat" and "car"/"card" depend on available paths; with this board:
        // c(0,0)->a(0,1)->t(0,2) gives "cat"
        // c(0,0)->a(0,1)->r(1,0) gives "car", then ->d(1,1) gives "card"
        // "cart" cannot be formed; "dog" can: d(1,1)->o(1,2)->g(2,1)
        val expected = setOf("cat", "car", "card", "dog")
        assertEquals(expected, actual)
    }


    @Test
    fun noWords() {
        val board = boardOf("abc", "def")
        val words = emptyArray<String>()
        val actual = sut.findWords(board, words)
        assertEquals(emptyList<String>(), actual)
    }

    @Test
    fun unicodeSafeguard_nonLowercaseIgnored() {
        // The solution assumes 'a'..'z'. This test ensures non-lowercase is safely skipped.
        val board = arrayOf(charArrayOf('A')) // outside 'a'..'z'
        val words = arrayOf("a")
        val actual = sut.findWords(board, words)
        assertEquals(emptyList<String>(), actual)
    }
}
