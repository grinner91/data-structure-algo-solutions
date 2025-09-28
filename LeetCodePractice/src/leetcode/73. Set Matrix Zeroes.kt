package leetcode

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun setZeroes(matrix: Array<IntArray>): Unit {
    var firstRowZero = false
    var firstColZero = false
    for (r in matrix.indices) {
        for (c in matrix[0].indices) {
            if (matrix[r][c] == 0) {
                if (r == 0) firstRowZero = true
                if (c == 0) firstColZero = true
                matrix[r][0] = 0
                matrix[0][c] = 0
            }
        }
    }
    for (r in 1..matrix.indices.last) {
        for (c in 1..matrix[0].indices.last) {
            if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                matrix[r][c] = 0
            }
        }
    }
    if (firstRowZero) {
        for (c in matrix[0].indices) {
            matrix[0][c] = 0
        }
    }
    if (firstColZero) {
        for (r in matrix.indices) {
            matrix[r][0] = 0
        }
    }
}

class SetZeroesTests {
    @Test
    fun `should return correct zeroes for matrix 3x3`() {
        val expected = arrayOf(
            intArrayOf(1, 0, 1),
            intArrayOf(0, 0, 0),
            intArrayOf(1, 0, 1),
        )

        val actual = arrayOf(
            intArrayOf(1, 1, 1),
            intArrayOf(1, 0, 1),
            intArrayOf(1, 1, 1),
        )
        setZeroes(actual)
        assertTrue(expected.contentDeepEquals(actual))
    }

    @Test
    fun `should return correct zeroes for matrix 3x4`() {
        val expected = arrayOf(
            intArrayOf(0,0,0,0),
            intArrayOf(0,4,5,0),
            intArrayOf(0,3,1,0),
        )

        val actual = arrayOf(
            intArrayOf(0,1,2,0),
            intArrayOf(3,4,5,2),
            intArrayOf(1,3,1,5),
        )
        setZeroes(actual)
        assertTrue(expected.contentDeepEquals(actual))
    }
}