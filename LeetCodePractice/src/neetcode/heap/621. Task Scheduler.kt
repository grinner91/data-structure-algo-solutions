package neetcode.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayDeque

/**
Main idea
We keep 2 data structures:
-> maxHeap → tasks ready to run now, ordered by highest remaining count
-> cooldown → tasks that were just used and cannot run until some future time

At each time unit:
1. increase time
2. move finished-cooldown tasks back to heap
3. run one task from heap if possible
4. otherwise this time slot is idle
 * */

class TaskSchedulerMaxHeap {
    fun leastInterval(tasks: CharArray, n: Int): Int {
        if (tasks.isEmpty()) return 0
        if (n == 0) return tasks.size

        val freq = tasks.asSequence().groupingBy { it }.eachCount()
        val maxHeap = PriorityQueue<Int>(compareByDescending { it })
        freq.forEach { maxHeap.add(it.value) }

        data class CoolingTask(val count: Int, val nextTimeSlot: Int)

        val que = ArrayDeque<CoolingTask>()
        var time = 0
        while (maxHeap.isNotEmpty() || que.isNotEmpty()) {
            time++
            while (que.isNotEmpty() && que.first().nextTimeSlot == time) {
                maxHeap.offer(que.removeLast().count)
            }
            if (maxHeap.isNotEmpty()) {
                val remaining = maxHeap.poll() - 1
                if (remaining > 0) {
//                    nextAvailableTime = currentTime + (n + 1)
//                    run → gap → gap → next run
//                          n gaps      +1 slot
                    que.addLast(CoolingTask(remaining, time + n + 1))
                }
            }
        }
        return time
    }
}

class TaskSchedulerTest {

    private val impls = listOf(
        TaskSchedulerMaxHeap()::leastInterval,
    )

    @Test
    fun example1() {
        val tasks = charArrayOf('A', 'A', 'A', 'B', 'B', 'B')
        val n = 2

        impls.forEach { leastInterval ->
            assertEquals(8, leastInterval(tasks, n))
        }
    }

    @Test
    fun example2() {
        val tasks = charArrayOf('A', 'C', 'A', 'B', 'D', 'B')
        val n = 1

        impls.forEach { leastInterval ->
            assertEquals(6, leastInterval(tasks, n))
        }
    }

    @Test
    fun example3() {
        val tasks = charArrayOf('A', 'A', 'A', 'B', 'B', 'B')
        val n = 3

        impls.forEach { leastInterval ->
            assertEquals(10, leastInterval(tasks, n))
        }
    }

    @Test
    fun noCooldown() {
        val tasks = charArrayOf('A', 'A', 'A', 'B', 'B', 'B')
        val n = 0

        impls.forEach { leastInterval ->
            assertEquals(6, leastInterval(tasks, n))
        }
    }

    @Test
    fun singleTask() {
        val tasks = charArrayOf('A')
        val n = 100

        impls.forEach { leastInterval ->
            assertEquals(1, leastInterval(tasks, n))
        }
    }

    @Test
    fun allUniqueTasks() {
        val tasks = charArrayOf('A', 'B', 'C', 'D', 'E', 'F')
        val n = 4

        impls.forEach { leastInterval ->
            assertEquals(6, leastInterval(tasks, n))
        }
    }

    @Test
    fun allSameTask() {
        val tasks = charArrayOf('A', 'A', 'A', 'A')
        val n = 2

        impls.forEach { leastInterval ->
            assertEquals(10, leastInterval(tasks, n))
        }
    }

    @Test
    fun multipleMaxFrequencyTasks() {
        val tasks = charArrayOf('A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'C')
        val n = 2

        impls.forEach { leastInterval ->
            assertEquals(9, leastInterval(tasks, n))
        }
    }

    @Test
    fun idleSlotsNeeded() {
        val tasks = charArrayOf('A', 'A', 'A', 'B', 'B', 'C')
        val n = 2

        impls.forEach { leastInterval ->
            assertEquals(7, leastInterval(tasks, n))
        }
    }

    @Test
    fun enoughTasksToFillIdleSlots() {
        val tasks = charArrayOf('A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'D', 'D')
        val n = 2

        impls.forEach { leastInterval ->
            assertEquals(10, leastInterval(tasks, n))
        }
    }

    @Test
    fun largeCooldown() {
        val tasks = charArrayOf('A', 'A', 'B', 'B')
        val n = 3

        impls.forEach { leastInterval ->
            assertEquals(6, leastInterval(tasks, n))
        }
    }
}