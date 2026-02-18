package neetcode.stack

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LargestRectangleInHistogramBruteForceExpand {
    fun largestRectangleArea(heights: IntArray): Int {
        val n = heights.size
        var maxArea = 0
        for (i in heights.indices) {
            val h = heights[i]
            var l = i
            //left expand
            while (l - 1 >= 0 && heights[l - 1] >= h) l--
            //right expand
            var r = i
            while (r + 1 < n && heights[r + 1] >= h) r++
            maxArea = maxOf(maxArea, h * (r - l + 1))
        }
        return maxArea
    }
}

class LargestRectangleInHistogramMinScan {
    fun largestRectangleArea(heights: IntArray): Int {
        val n = heights.size
        var maxArea = 0
        for (l in heights.indices) {
            var minH = Int.MAX_VALUE
            for (r in l until n) {
                minH = minOf(minH, heights[r])
                maxArea = maxOf(maxArea, minH * (r - l + 1))
            }
        }
        return maxArea
    }
}

class LargestRectangleInHistogramStackTwoPass {
    fun largestRectangleArea(heights: IntArray): Int {
        val n = heights.size
        val stack = ArrayDeque<Int>()
        val leftMost = IntArray(n) { -1 }
        for (i in heights.indices) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeLast()
            }
            if (stack.isNotEmpty()) {
                leftMost[i] = stack.last()
            }
            stack.addLast(i)
        }
        val rightMost = IntArray(n) { n }
        stack.clear()
        for (i in n - 1 downTo 0) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeLast()
            }
            if (stack.isNotEmpty()) {
                rightMost[i] = stack.last()
            }
            stack.addLast(i)
        }
        var maxArea = 0
        for (i in 0 until n) {
            leftMost[i]++
            rightMost[i]--
            maxArea = maxOf(maxArea, heights[i] * (rightMost[i] - leftMost[i] + 1))
        }
        return maxArea
    }
}

class LargestRectangleInHistogramStackOnePass {
    fun largestRectangleArea(heights: IntArray): Int {
        var maxArea = 0
        val stack = ArrayDeque<Pair<Int, Int>>() //index, height
        heights.forEachIndexed { i, h ->
            var start = i
            while (stack.isNotEmpty() && stack.last().second > h) {
                val (li, lh) = stack.removeLast()
                maxArea = maxOf(maxArea, lh * (i - li))
                start = li
            }
            stack.addLast(start to h)
        }

        for ((i, h) in stack) {
            maxArea = maxOf(maxArea, h * (heights.size - i))
        }
        return maxArea
    }
}

class LargestRectangleInHistogramMonotonicStackOptimal {
    fun largestRectangleArea(heights: IntArray): Int {
        val n = heights.size
        var maxArea = 0
        val stack = ArrayDeque<Int>()
        for (r in 0..n) { //iterate till n (included)
            val curH = if (r == n) 0 else heights[r] //sentinel at end
            while (stack.isNotEmpty() && curH < heights[stack.last()]) {
                val h = heights[stack.removeLast()]
                val l = if (stack.isEmpty()) -1 else stack.last()
                // smaller heights exclude from left and right
                // left boundary  l + 1 and right boundary r - 1
                // w = (r-1)-(l+1) + 1
                val w = r - l - 1
                maxArea = maxOf(maxArea, h * w)
            }
            if (r < n) {
                stack.addLast(r)
            }
        }
        return maxArea
    }
}

class LargestRectangleInHistogramTest {

    // Your preferred function-reference list style
    private val impls = listOf(
//        LargestRectangleInHistogramBruteForceExpand()::largestRectangleArea,
//        LargestRectangleInHistogramMinScan()::largestRectangleArea,
//        LargestRectangleInHistogramStackTwoPass()::largestRectangleArea,
//        SolutionDivideAndConquer()::largestRectangleArea,
//        LargestRectangleInHistogramStackOnePass()::largestRectangleArea,
        LargestRectangleInHistogramMonotonicStackOptimal()::largestRectangleArea,
    )

    @Test
    fun example1() {
        val heights = intArrayOf(2, 1, 5, 6, 2, 3)
        impls.forEach { f ->
            assertEquals(10, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun example2_singleBar() {
        val heights = intArrayOf(2)
        impls.forEach { f ->
            assertEquals(2, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun empty() {
        val heights = intArrayOf()
        impls.forEach { f ->
            assertEquals(0, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun allEqual() {
        val heights = intArrayOf(3, 3, 3, 3)
        // best = 3 * 4 = 12
        impls.forEach { f ->
            assertEquals(12, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun strictlyIncreasing() {
        val heights = intArrayOf(1, 2, 3, 4, 5)
        // best = 3*3=9 (bars 3,4,5 min=3 width=3) OR 4*2=8 OR 5*1=5
        impls.forEach { f ->
            assertEquals(9, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun strictlyDecreasing() {
        val heights = intArrayOf(5, 4, 3, 2, 1)
        // best = 3*3=9 (first three min=3 width=3) OR 4*2=8 OR 5*1=5
        impls.forEach { f ->
            assertEquals(9, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun zerosIncluded() {
        val heights = intArrayOf(2, 0, 2)
        impls.forEach { f ->
            assertEquals(2, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun classicPlateau() {
        val heights = intArrayOf(2, 4, 4, 4, 1)
        // best = 4 * 3 = 12
        impls.forEach { f ->
            assertEquals(12, f(heights), "Failed for impl=$f")
        }
    }

    @Test
    fun wideLowBeatsTallNarrow() {
        val heights = intArrayOf(6, 2, 5, 4, 5, 1, 6)
        // known answer = 12 (5,4,5 => min=4 width=3 => 12)
        impls.forEach { f ->
            assertEquals(12, f(heights), "Failed for impl=$f")
        }
    }
}
