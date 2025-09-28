

/*
class Solution {

    private companion object {
        const val ALPHABET = 26
    }

    private inline fun Char.idx(): Int = this - 'a'

    fun checkInclusion(s1: String, s2: String): Boolean {
        val n1 = s1.length
        val n2 = s2.length
        if (n1 > n2) return false

        // Build counts for s1 and the first window of s2
        val need = IntArray(ALPHABET)
        val have = IntArray(ALPHABET)
        repeat(n1) { i ->
            need[s1[i].idx()]++
            have[s2[i].idx()]++
        }

        var matches = (0 until ALPHABET).count { need[it] == have[it] }

        var left = 0
        var right = n1
        while (right < n2) {
            if (matches == ALPHABET) return true

            // Add right char
            val add = s2[right++].idx()
            have[add]++
            matches += when {
                have[add] == need[add] -> 1
                have[add] == need[add] + 1 -> -1
                else -> 0
            }

            // Remove left char
            val rem = s2[left++].idx()
            have[rem]--
            matches += when {
                have[rem] == need[rem] -> 1
                have[rem] == need[rem] - 1 -> -1
                else -> 0
            }
        }

        return matches == ALPHABET
    }
}
*/