package neetcode.binarysearch

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KokoEatingBananasBruteForce {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        val maxPile = piles.max()
        for (k in 1..maxPile) {
            var hours = 0L
            for (p in piles) {
                hours += ((p.toLong() + k - 1) / k)
            }
            if (hours <= h) return k
        }
        return maxPile
    }
}

class KokoEatingBananasBinarySearch {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var l = 1
        var r = piles.max()
        var res = r
        while (l <= r) {
            val k = l + (r - l) / 2
            var hours = 0L
            for (p in piles) {
                hours += ((p.toLong() + k - 1) / k)
            }
            if (hours <= h) {
                res = k
                r = k - 1
            } else {
                l = k + 1
            }
        }
        return res
    }
}

class KokoEatingBananasTest {

    private val impls = listOf(
//        KokoEatingBananasBruteForce()::minEatingSpeed,
        KokoEatingBananasBinarySearch()::minEatingSpeed,
//        SolutionBinarySearchTightLowerBound()::minEatingSpeed,
    )

    @Test
    fun example1() {
        val piles = intArrayOf(3, 6, 7, 11)
        val h = 8
        val expected = 4
        impls.forEach { f -> assertEquals(expected, f(piles, h)) }
    }

    @Test
    fun example2() {
        val piles = intArrayOf(30, 11, 23, 4, 20)
        val h = 5
        val expected = 30
        impls.forEach { f -> assertEquals(expected, f(piles, h)) }
    }

    @Test
    fun example3() {
        val piles = intArrayOf(30, 11, 23, 4, 20)
        val h = 6
        val expected = 23
        impls.forEach { f -> assertEquals(expected, f(piles, h)) }
    }

    @Test
    fun singlePile() {
        val piles = intArrayOf(100)
        val h = 10
        val expected = 10 // 100/10 = 10 hours
        impls.forEach { f -> assertEquals(expected, f(piles, h)) }
    }

    @Test
    fun hEqualsNumberOfPiles_answerIsMaxPile() {
        val piles = intArrayOf(1, 2, 3, 100)
        val h = 4
        val expected = 100
        impls.forEach { f -> assertEquals(expected, f(piles, h)) }
    }

    @Test
    fun veryLargeValues_overflowSafety() {
        val piles = intArrayOf(1_000_000_000, 1_000_000_000, 1_000_000_000)
        val h = 3
        val expected = 1_000_000_000
        impls.forEach { f -> assertEquals(expected, f(piles, h)) }
    }

    @Test
    fun tighterBoundStillCorrect() {
        val piles = intArrayOf(9, 9, 9)
        val h = 5
        // Try k=5 => hours = ceil(9/5)*3 = 2*3 = 6 (too big)
        // k=6 => ceil(9/6)*3 = 2*3 = 6 (too big)
        // k=7 => ceil(9/7)*3 = 2*3 = 6 (too big)
        // k=8 => ceil(9/8)*3 = 2*3 = 6 (too big)
        // k=9 => 1*3 = 3 (ok) => answer 9
        val expected = 9
        impls.forEach { f -> assertEquals(expected, f(piles, h)) }
    }
}
