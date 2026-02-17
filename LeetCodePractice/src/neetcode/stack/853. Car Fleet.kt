package neetcode.stack

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CarFleetStack {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
        val n = position.size
        if (n <= 1) return n

        val cars = position.zip(speed).sortedByDescending { it.first }
        val stack = ArrayDeque<Double>()

        for ((p, s) in cars) {
            val time = (target - p).toDouble() / s.toDouble()
            if (stack.isEmpty() || time > stack.last()) {
                stack.addLast(time)
            }
            //else merge into fleet ahead
        }
        return stack.size
    }
}

class CarFleetIteration {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
        val n = position.size
        if (n <= 1) return 1
        val cars = position.zip(speed).sortedByDescending { it.first }
        var fleet = 0
        var maxTime = 0.0
        for ((p, s) in cars) {
            val curTime = (target - p).toDouble() / s.toDouble()
            if (curTime > maxTime) {
                fleet++
                maxTime = curTime
            }
        }
        return fleet
    }
}

class CarFleetTest {

    private val impls = listOf(
        CarFleetStack()::carFleet,
        CarFleetIteration()::carFleet
    )

    @Test
    fun example1() {
        val target = 12
        val position = intArrayOf(10, 8, 0, 5, 3)
        val speed = intArrayOf(2, 4, 1, 1, 3)
        impls.forEach { f ->
            assertEquals(3, f(target, position, speed))
        }
    }

    @Test
    fun example2_singleCar() {
        val target = 10
        val position = intArrayOf(3)
        val speed = intArrayOf(3)
        impls.forEach { f ->
            assertEquals(1, f(target, position, speed))
        }
    }

    @Test
    fun example3_twoCars_noCatchUp() {
        val target = 100
        val position = intArrayOf(0, 2)
        val speed = intArrayOf(4, 2)
        impls.forEach { f ->
            assertEquals(1, f(target, position, speed))
        }
    }

    @Test
    fun alreadyInOrder_orNotDoesNotMatter() {
        val target = 10
        val position = intArrayOf(6, 8)
        val speed = intArrayOf(3, 2)
        impls.forEach { f ->
            assertEquals(2, f(target, position, speed))
        }
    }

    @Test
    fun allMergeIntoOneFleet() {
        val target = 10
        val position = intArrayOf(0, 4, 2)
        val speed = intArrayOf(2, 1, 3)
        // After sorting by pos desc: (4,1)->6.0, (2,3)->2.666 joins, (0,2)->5.0 joins -> 1 fleet
        impls.forEach { f ->
            assertEquals(1, f(target, position, speed))
        }
    }

    @Test
    fun allSeparateFleets() {
        val target = 10
        val position = intArrayOf(0, 2, 4)
        val speed = intArrayOf(1, 1, 1)
        impls.forEach { f ->
            assertEquals(3, f(target, position, speed))
        }
    }

    @Test
    fun largeNumbers_precisionSafe() {
        val target = 1_000_000
        val position = intArrayOf(999_999, 0)
        val speed = intArrayOf(1, 1)
        impls.forEach { f ->
            assertEquals(2, f(target, position, speed))
        }
    }
}
