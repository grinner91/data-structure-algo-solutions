package neetcode.backtracking


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//try every unused number, mark it used, explore deeper, then undo the choice.

class PermutationsBacktrackUsedArray {
    fun permute(nums: IntArray): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        val perms = ArrayList<Int>(nums.size)
        val used = BooleanArray(nums.size)

        fun backtrack() {
            if (perms.size == nums.size) {
                res.add(perms.toList())
                return
            }
            for (i in nums.indices) {
                if (used[i]) continue

                used[i] = true
                perms.add(nums[i])

                backtrack()

                perms.removeLast() //backtrack
                used[i] = false //backtrack
            }
        }
        backtrack()
        return res
    }
}

class PermutationsTest {

    private val impls = listOf(
        PermutationsBacktrackUsedArray()::permute
    )

    @Test
    fun example1() {
        val nums = intArrayOf(1, 2, 3)
        val expected = setOf(
            listOf(1, 2, 3),
            listOf(1, 3, 2),
            listOf(2, 1, 3),
            listOf(2, 3, 1),
            listOf(3, 1, 2),
            listOf(3, 2, 1),
        )

        impls.forEach { permute ->
            assertEquals(expected, permute(nums).toSet())
        }
    }

    @Test
    fun example2() {
        val nums = intArrayOf(0, 1)
        val expected = setOf(
            listOf(0, 1),
            listOf(1, 0),
        )

        impls.forEach { permute ->
            assertEquals(expected, permute(nums).toSet())
        }
    }

    @Test
    fun singleElement() {
        val nums = intArrayOf(1)
        val expected = setOf(
            listOf(1),
        )

        impls.forEach { permute ->
            assertEquals(expected, permute(nums).toSet())
        }
    }

    @Test
    fun emptyArray() {
        val nums = intArrayOf()
        val expected = setOf(
            emptyList<Int>(),
        )

        impls.forEach { permute ->
            assertEquals(expected, permute(nums).toSet())
        }
    }

    @Test
    fun negativeNumbers() {
        val nums = intArrayOf(-1, 2, -3)
        val expected = setOf(
            listOf(-1, 2, -3),
            listOf(-1, -3, 2),
            listOf(2, -1, -3),
            listOf(2, -3, -1),
            listOf(-3, -1, 2),
            listOf(-3, 2, -1),
        )

        impls.forEach { permute ->
            assertEquals(expected, permute(nums).toSet())
        }
    }
}

