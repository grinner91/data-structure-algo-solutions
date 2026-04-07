package neetcode.tries_prefix_tree

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

//Insert into Trie → O(L)
// search average: O(L
// worst : O(26^L), "....", Each . → branch to up to 26 children

class WordDictionaryTrieRecursive {
    private class TrieNode {
        val children = arrayOfNulls<TrieNode>(26)
        var isWord = false
    }

    private val baseChar = 'a'
    private val root = TrieNode()

    fun addWord(word: String) {
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
        fun dfs(start: Int, node: TrieNode): Boolean {
            var cur = node
            for (i in start until word.length) {
                val ch = word[i]
                if (ch == '.') {
                    for (child in cur.children) {
                        if (child != null) {
                            if (dfs(i + 1, child)) return true
                        }
                    }
                    return false
                } else {
                    val cIdx = ch - baseChar
                    if (cur.children[cIdx] == null) return false
                    cur = cur.children[cIdx]!!
                }
            }
            return cur.isWord
        }
        return dfs(0, root)
    }
}

class WordDictionaryTrieIterative {
    private class TrieNode {
        val children = arrayOfNulls<TrieNode>(26)
        var isWord = false
    }

    private data class State(
        val node: TrieNode,
        val wordIndex: Int
    )

    private val root = TrieNode()

    fun addWord(word: String) {
        var current = root
        for (char in word) {
            val childIndex = char - 'a'
            if (current.children[childIndex] == null) {
                current.children[childIndex] = TrieNode()
            }
            current = current.children[childIndex]!!
        }
        current.isWord = true
    }

    fun search(word: String): Boolean {
        val stack = ArrayDeque<State>()
        stack.addLast(State(root, 0))

        while (stack.isNotEmpty()) {
            val (node, index) = stack.removeLast()

            if (index == word.length) {
                if (node.isWord) return true
                continue
            }

            val char = word[index]

            if (char == '.') {
                for (child in node.children) {
                    if (child == null) continue
                    stack.addLast(State(child, index + 1))
                }
            } else {
                val childIndex = char - 'a'
                val nextNode = node.children[childIndex] ?: continue
                stack.addLast(State(nextNode, index + 1))
            }
        }

        return false
    }
}
class WordDictionaryTest {
    private val impls = listOf(
        ::WordDictionaryTrieRecursive,
    )

    @Test
    fun exampleCase() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            wordDictionary.addWord("bad")
            wordDictionary.addWord("dad")
            wordDictionary.addWord("mad")

            assertEquals(false, wordDictionary.search("pad"))
            assertEquals(true, wordDictionary.search("bad"))
            assertEquals(true, wordDictionary.search(".ad"))
            assertEquals(true, wordDictionary.search("b.."))
        }
    }

    @Test
    fun exactMatchOnly() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            wordDictionary.addWord("apple")

            assertEquals(true, wordDictionary.search("apple"))
            assertEquals(false, wordDictionary.search("app"))
            assertEquals(false, wordDictionary.search("apples"))
        }
    }

    @Test
    fun singleWildcard() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            wordDictionary.addWord("cat")
            wordDictionary.addWord("cap")
            wordDictionary.addWord("can")

            assertEquals(true, wordDictionary.search("ca."))
            assertEquals(true, wordDictionary.search("c.t"))
            assertEquals(false, wordDictionary.search("d.t"))
        }
    }

    @Test
    fun allWildcards() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            wordDictionary.addWord("a")
            wordDictionary.addWord("to")
            wordDictionary.addWord("tea")

            assertEquals(true, wordDictionary.search("."))
            assertEquals(true, wordDictionary.search(".."))
            assertEquals(true, wordDictionary.search("..."))
            assertEquals(false, wordDictionary.search("...."))
        }
    }

    @Test
    fun multipleBranches() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            wordDictionary.addWord("at")
            wordDictionary.addWord("and")
            wordDictionary.addWord("an")
            wordDictionary.addWord("add")

            assertEquals(false, wordDictionary.search("a"))
            assertEquals(
                true,
                wordDictionary.search(".at").not()
            ) // same as true, just sanity that expression is boolean
            assertEquals(true, wordDictionary.search("an"))
            assertEquals(true, wordDictionary.search("a.d"))
            assertEquals(true, wordDictionary.search("..d"))
            assertEquals(false, wordDictionary.search("b.."))
        }
    }

    @Test
    fun duplicateInsertions() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            wordDictionary.addWord("hello")
            wordDictionary.addWord("hello")

            assertEquals(true, wordDictionary.search("hello"))
            assertEquals(true, wordDictionary.search("h.llo"))
            assertEquals(false, wordDictionary.search("hell"))
        }
    }

    @Test
    fun prefixShouldNotBeWordUnlessAdded() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            wordDictionary.addWord("banana")

            assertEquals(false, wordDictionary.search("ban"))
            assertEquals(false, wordDictionary.search("bana"))
            assertEquals(true, wordDictionary.search("banana"))
        }
    }

    @Test
    fun emptyStructure() {
        impls.forEach { factory ->
            val wordDictionary = WordDictionaryAdapter(factory())

            assertEquals(false, wordDictionary.search("a"))
            assertEquals(false, wordDictionary.search("."))
        }
    }

    private class WordDictionaryAdapter(private val impl: Any) {
        fun addWord(word: String) {
            when (impl) {
                is WordDictionaryTrieRecursive -> impl.addWord(word)
                //is WordDictionaryTrieIterative -> impl.addWord(word)
                else -> error("Unknown implementation")
            }
        }

        fun search(word: String): Boolean {
            return when (impl) {
                is WordDictionaryTrieRecursive -> impl.search(word)
                else -> error("Unknown implementation")
            }
        }
    }
}