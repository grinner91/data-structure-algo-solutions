package neetcode.arrayandhashing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Codec {
    /**
     * Encodes a list of strings to a single string.
     * Format: <len>#<string><len>#<string>...
     */
    fun encode(strs: List<String>): String {
        val encode = StringBuilder()
        strs.forEach {
            encode.append(it.length)
            encode.append("#")
            encode.append(it)
        }
        return encode.toString()
    }

    /**
     * Decodes a single string to a list of strings.
     */
    //"21#ab...c1#k35#xy..."
    fun decode(s: String): List<String> {
        val res = mutableListOf<String>()
        var i = 0
        val n = s.length
        while (i < n) {
            var j = i
            while (j < n && s[j] != '#') j++
            //parse - str length until #,
            val subLen = s.substring(i, j).toInt() //"21"
            //parse - sub str "ab..."
            i = j + 1
            res.add(s.substring(i, i + subLen))
            i += subLen
        }
        return res
    }
}


class CodecTest {

    private val sut = Codec()

    @Test
    fun `encode-decode empty list`() {
        val input = emptyList<String>()
        val encoded = sut.encode(input)
        val decoded = sut.decode(encoded)

        assertEquals(input, decoded)
        assertEquals("", encoded)
    }

    @Test
    fun `encode-decode single empty string`() {
        val input = listOf("")
        val encoded = sut.encode(input)
        val decoded = sut.decode(encoded)

        assertEquals(input, decoded)
        assertEquals("0#", encoded)
    }

    @Test
    fun `encode-decode typical strings`() {
        val input = listOf("lint", "code", "love", "you")
        val encoded = sut.encode(input)
        val decoded = sut.decode(encoded)

        assertEquals(input, decoded)
    }

    @Test
    fun `encode-decode strings containing delimiter characters`() {
        val input = listOf("a#b", "##", "#", "x#y#z")
        val encoded = sut.encode(input)
        val decoded = sut.decode(encoded)

        assertEquals(input, decoded)
    }

    @Test
    fun `encode-decode strings containing numbers and symbols`() {
        val input = listOf("123", "9#20", "len=5", "0#")
        val encoded = sut.encode(input)
        val decoded = sut.decode(encoded)

        assertEquals(input, decoded)
    }

    @Test
    fun `encode-decode unicode strings`() {
        val input = listOf("বাংলা", "🙂🙃", "こんにちは", "mañana")
        val encoded = sut.encode(input)
        val decoded = sut.decode(encoded)

        assertEquals(input, decoded)
    }

    @Test
    fun `encode-decode preserves empty in middle`() {
        val input = listOf("a", "", "b", "")
        val encoded = sut.encode(input)
        val decoded = sut.decode(encoded)

        assertEquals(input, decoded)
    }
}
