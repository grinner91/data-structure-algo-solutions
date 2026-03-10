package neetcode.linkedlist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FindDuplicateNumberSlowFastPointer {
    fun findDuplicate(nums: IntArray): Int {
        var slow = nums[0]
        var fast = nums[0]
        do {
            slow = nums[slow]
            fast = nums[nums[fast]]
        } while (slow != fast)

       //distance(start → entry) = distance(meeting → entry)
        slow = nums[0]
        while (slow != fast) {
            //both move at same speed, one step, meet at entry point
            slow = nums[slow]
            fast = nums[fast]
        }

        return slow
    }
}

class FindDuplicateNumberTest {

    private val impls = listOf(
        FindDuplicateNumberSlowFastPointer()::findDuplicate,
    )

    @Test
    fun example1() {
        val nums = intArrayOf(1, 3, 4, 2, 2)

        impls.forEach { findDuplicate ->
            assertEquals(2, findDuplicate(nums.copyOf()))
        }
    }

    @Test
    fun example2() {
        val nums = intArrayOf(3, 1, 3, 4, 2)

        impls.forEach { findDuplicate ->
            assertEquals(3, findDuplicate(nums.copyOf()))
        }
    }

    @Test
    fun duplicateRepeatedMoreThanTwice() {
        val nums = intArrayOf(3, 3, 3, 3, 3)

        impls.forEach { findDuplicate ->
            assertEquals(3, findDuplicate(nums.copyOf()))
        }
    }

    @Test
    fun duplicateAtBeginning() {
        val nums = intArrayOf(2, 1, 4, 3, 2)

        impls.forEach { findDuplicate ->
            assertEquals(2, findDuplicate(nums.copyOf()))
        }
    }

    @Test
    fun smallestValidInput() {
        val nums = intArrayOf(1, 1)

        impls.forEach { findDuplicate ->
            assertEquals(1, findDuplicate(nums.copyOf()))
        }
    }

    @Test
    fun anotherCase() {
        val nums = intArrayOf(1, 4, 6, 2, 5, 3, 6)

        impls.forEach { findDuplicate ->
            assertEquals(6, findDuplicate(nums.copyOf()))
        }
    }
}