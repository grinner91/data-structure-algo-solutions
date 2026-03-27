package neetcode.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*


class LastStoneWeightMaxHeap {
    fun lastStoneWeight(stones: IntArray): Int {
        val maxHeap = PriorityQueue<Int>(compareByDescending { it })
        stones.forEach { maxHeap.offer(it) }
        while (maxHeap.size > 1) {
            val x = maxHeap.poll()
            val y = maxHeap.poll()
            if (x != y) {
                maxHeap.offer(x - y)
            }
        }
        return maxHeap.peek() ?: 0
    }
}

class LastStoneWeightTest {

    private val impls = listOf(
        //SolutionBruteForceSortEachRound()::lastStoneWeight,
        LastStoneWeightMaxHeap()::lastStoneWeight,
    )

    @Test
    fun example1() {
        impls.forEach { lastStoneWeight ->
            assertEquals(1, lastStoneWeight(intArrayOf(2, 7, 4, 1, 8, 1)))
        }
    }

    @Test
    fun example2() {
        impls.forEach { lastStoneWeight ->
            assertEquals(1, lastStoneWeight(intArrayOf(1)))
        }
    }

    @Test
    fun allSameWeights() {
        impls.forEach { lastStoneWeight ->
            assertEquals(0, lastStoneWeight(intArrayOf(5, 5, 5, 5)))
        }
    }

    @Test
    fun twoDifferentStones() {
        impls.forEach { lastStoneWeight ->
            assertEquals(2, lastStoneWeight(intArrayOf(4, 2)))
        }
    }

    @Test
    fun twoSameStones() {
        impls.forEach { lastStoneWeight ->
            assertEquals(0, lastStoneWeight(intArrayOf(3, 3)))
        }
    }

    @Test
    fun singleStone() {
        impls.forEach { lastStoneWeight ->
            assertEquals(9, lastStoneWeight(intArrayOf(9)))
        }
    }

    @Test
    fun emptyInput() {
        impls.forEach { lastStoneWeight ->
            assertEquals(0, lastStoneWeight(intArrayOf()))
        }
    }

    @Test
    fun largerMixedCase() {
        impls.forEach { lastStoneWeight ->
            assertEquals(1, lastStoneWeight(intArrayOf(10, 4, 2, 10, 3)))
        }
    }
}