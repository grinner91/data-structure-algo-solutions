package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun isIsomorphic(s: String, t: String): Boolean {
    return s.toSet().size == t.toSet().size
            && t.zip(s).toSet().size == t.toSet().size
}

class IsomorphicTests {
    @Test
    fun `should return when egg and add`() {
        assertEquals(
            true,
            isIsomorphic("egg", "add")
        )
    }
    @Test
    fun `should return when foo and bar`() {
        assertEquals(
            false,
            isIsomorphic("foo", "bar")
        )
    }
}