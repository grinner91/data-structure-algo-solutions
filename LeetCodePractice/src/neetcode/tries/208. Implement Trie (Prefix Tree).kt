package neetcode.tries

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    var endOfWord = false
}

class Trie() {
    private val root = TrieNode()
    fun insert(word: String) {
        var cur = root
        for (c in word) {
            cur.children.putIfAbsent(c, TrieNode())
            cur = cur.children[c]!!
        }
        cur.endOfWord = true
    }

    fun search(word: String): Boolean {
        var cur = root
        for (c in word) {
            if (c !in cur.children) {
                return false
            }
            cur = cur.children[c]!!
        }
        return cur.endOfWord
    }

    fun startsWith(prefix: String): Boolean {
        var cur = root
        for (c in prefix) {
            if (c !in cur.children) {
                return false
            }
            cur = cur.children[c]!!
        }
        return true
    }
}

class TrieTest {

    @Test
    fun `basic insert and search`() {
        val trie = Trie()
        assertFalse(trie.search("apple"))

        trie.insert("apple")
        assertTrue(trie.search("apple"))
        assertFalse(trie.search("app"))
        assertTrue(trie.startsWith("app"))

        trie.insert("app")
        assertTrue(trie.search("app"))
        assertTrue(trie.search("apple"))
    }

    @Test
    fun `shared prefixes and non existent words`() {
        val trie = Trie()
        listOf("to", "tea", "ted", "ten", "inn").forEach(trie::insert)

        assertTrue(trie.startsWith("t"))
        assertTrue(trie.startsWith("te"))
        assertTrue(trie.search("tea"))
        assertTrue(trie.search("ted"))
        assertFalse(trie.search("te"))         // prefix only, not a word
        assertFalse(trie.search("tent"))       // not inserted
        assertFalse(trie.startsWith("zen"))    // unrelated prefix
    }

    @Test
    fun `multiple inserts of same word are idempotent`() {
        val trie = Trie()
        repeat(5) { trie.insert("repeat") }
        assertTrue(trie.search("repeat"))
        assertTrue(trie.startsWith("rep"))
    }

    @Test
    fun `empty string behavior`() {
        val trie = Trie()
        trie.insert("")
        // After inserting empty string, search("") should be true,
        // and every prefix query still works as usual.
        assertTrue(trie.search(""))
        assertTrue(trie.startsWith(""))
        trie.insert("a")
        assertTrue(trie.search("a"))
        assertTrue(trie.startsWith(""))
    }
}
