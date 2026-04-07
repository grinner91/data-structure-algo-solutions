package neetcode.tries_prefix_tree

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
/*
Time and Space
insert(word): O(n)
search(word): O(n)
startsWith(prefix): O(n)
Space: O(total characters inserted)
* */

class PrefixTreeWithArray {
    private class TrieNode {
        val children = arrayOfNulls<TrieNode>(26)
        var isWord = false
    }

    private val baseChar = 'a'
    private val root = TrieNode()

    fun insert(word: String) {
        var cur = root
        word.forEach { ch ->
            val i = ch - baseChar
            if (cur.children[i] == null) {
                cur.children[i] = TrieNode()
            }
            cur = cur.children[i]!!
        }
        cur.isWord = true
    }

    fun search(word: String): Boolean {
        val node = findWord(word)
        return node?.isWord ?: false
    }

    fun startsWith(prefix: String): Boolean {
        return findWord(prefix) != null
    }

    private fun findWord(word: String): TrieNode? {
        var cur = root
        word.forEach { ch ->
            val i = ch - baseChar
            cur.children[i] ?: return null
            cur = cur.children[i]!!
        }
        return cur
    }
}


class ImplementTrieTest {

    private data class TrieAdapter(
        val name: String,
        val create: () -> Any,
        val insert: (Any, String) -> Unit,
        val search: (Any, String) -> Boolean,
        val startsWith: (Any, String) -> Boolean
    )

    private val impls = listOf(
        TrieAdapter(
            name = "TrieArray",
            create = { PrefixTreeWithArray() },
            insert = { trie, word -> (trie as PrefixTreeWithArray).insert(word) },
            search = { trie, word -> (trie as PrefixTreeWithArray).search(word) },
            startsWith = { trie, prefix -> (trie as PrefixTreeWithArray).startsWith(prefix) }
        )
    )

    @Test
    fun exampleCase() {
        impls.forEach { impl ->
            val trie = impl.create()

            impl.insert(trie, "apple")
            assertTrue(impl.search(trie, "apple"), impl.name)
            assertFalse(impl.search(trie, "app"), impl.name)
            assertTrue(impl.startsWith(trie, "app"), impl.name)

            impl.insert(trie, "app")
            assertTrue(impl.search(trie, "app"), impl.name)
        }
    }

    @Test
    fun multipleWordsWithSharedPrefix() {
        impls.forEach { impl ->
            val trie = impl.create()

            impl.insert(trie, "cat")
            impl.insert(trie, "car")
            impl.insert(trie, "care")
            impl.insert(trie, "dog")

            assertTrue(impl.search(trie, "cat"), impl.name)
            assertTrue(impl.search(trie, "car"), impl.name)
            assertTrue(impl.search(trie, "care"), impl.name)
            assertTrue(impl.search(trie, "dog"), impl.name)

            assertTrue(impl.startsWith(trie, "ca"), impl.name)
            assertTrue(impl.startsWith(trie, "car"), impl.name)
            assertTrue(impl.startsWith(trie, "do"), impl.name)

            assertFalse(impl.search(trie, "ca"), impl.name)
            assertFalse(impl.search(trie, "c"), impl.name)
            assertFalse(impl.startsWith(trie, "z"), impl.name)
        }
    }

    @Test
    fun insertDuplicateWord() {
        impls.forEach { impl ->
            val trie = impl.create()

            impl.insert(trie, "hello")
            impl.insert(trie, "hello")

            assertTrue(impl.search(trie, "hello"), impl.name)
            assertTrue(impl.startsWith(trie, "hell"), impl.name)
            assertFalse(impl.search(trie, "hell"), impl.name)
        }
    }

    @Test
    fun prefixIsWholeWordAndAlsoPrefixOfAnotherWord() {
        impls.forEach { impl ->
            val trie = impl.create()

            impl.insert(trie, "a")
            impl.insert(trie, "ab")
            impl.insert(trie, "abc")

            assertTrue(impl.search(trie, "a"), impl.name)
            assertTrue(impl.search(trie, "ab"), impl.name)
            assertTrue(impl.search(trie, "abc"), impl.name)

            assertTrue(impl.startsWith(trie, "a"), impl.name)
            assertTrue(impl.startsWith(trie, "ab"), impl.name)
            assertFalse(impl.search(trie, "abcd"), impl.name)
        }
    }

    @Test
    fun emptyStructure() {
        impls.forEach { impl ->
            val trie = impl.create()

            assertFalse(impl.search(trie, "apple"), impl.name)
            assertFalse(impl.startsWith(trie, "app"), impl.name)
        }
    }

    @Test
    fun divergingBranches() {
        impls.forEach { impl ->
            val trie = impl.create()

            impl.insert(trie, "bat")
            impl.insert(trie, "ball")
            impl.insert(trie, "map")

            assertTrue(impl.search(trie, "bat"), impl.name)
            assertTrue(impl.search(trie, "ball"), impl.name)
            assertTrue(impl.search(trie, "map"), impl.name)

            assertTrue(impl.startsWith(trie, "ba"), impl.name)
            assertTrue(impl.startsWith(trie, "bal"), impl.name)
            assertTrue(impl.startsWith(trie, "ma"), impl.name)

            assertFalse(impl.search(trie, "bad"), impl.name)
            assertFalse(impl.startsWith(trie, "mo"), impl.name)
        }
    }
}