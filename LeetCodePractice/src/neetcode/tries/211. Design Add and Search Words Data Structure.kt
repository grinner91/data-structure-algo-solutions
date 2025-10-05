package neetcode.tries

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    var endOfWord = false
}

class WordDictionary() {
    val root = TrieNode()

    fun addWord(word: String) {
        var cur = root
        for (ch in word) {
            cur = cur.children.getOrPut(ch) { TrieNode() }
        }
        cur.endOfWord = true
    }

    fun search(word: String): Boolean {
        fun dfs(node: TrieNode?, i: Int): Boolean {
            if (node == null) return false
            if (i == word.length) return node.endOfWord
            val ch = word[i]
            return if (ch == '.') {
                for (next in node.children) {
                    if (dfs(next.value, i + 1))
                        return true
                }
                false
            } else {
                dfs(node.children[ch], i + 1)
            }
        }
        return dfs(root, 0)
    }
}


class WordDictionaryTest {

    @Test
    fun example_from_leetcode() {
        val wd = WordDictionary()
        wd.addWord("bad")
        wd.addWord("dad")
        wd.addWord("mad")
        assertFalse(wd.search("pad"))
        assertTrue(wd.search("bad"))
        assertTrue(wd.search(".ad"))
        assertTrue(wd.search("b.."))
    }

    @Test
    fun duplicates_and_prefixes() {
        val wd = WordDictionary()
        wd.addWord("a")
        wd.addWord("a")      // duplicate insert is fine
        wd.addWord("ab")
        assertTrue(wd.search("a"))
        assertTrue(wd.search("ab"))
        assertFalse(wd.search("b"))
        assertTrue(wd.search("."))   // matches "a"
        assertTrue(wd.search(".."))  // matches "ab"
        assertFalse(wd.search("..."))
    }

    @Test
    fun wildcards_cover() {
        val wd = WordDictionary()
        listOf("cat", "car", "cart", "dog", "dot").forEach(wd::addWord)
        assertTrue(wd.search("c.t"))
        assertTrue(wd.search("ca."))
        assertTrue(wd.search("...."))    // "cart"
        assertTrue(wd.search("d.g"))
        assertFalse(wd.search("..g."))   // none with 4 letters ending g
    }
}
