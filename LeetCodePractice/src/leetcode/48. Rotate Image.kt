package leetcode

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

fun rotate(matrix: Array<IntArray>): Unit {
    val n = matrix.size - 1
    for (r in 0 until  (n + 2) / 2) {
        for (c in 0 until  ((n + 1) / 2)) {
            val temp = matrix[r][c]
            matrix[r][c] = matrix[n - c][r]
            matrix[n - c][r] = matrix[n - r][n - c]
            matrix[n - r][n - c] = matrix[c][n - r]
            matrix[c][n - r] = temp
        }
    }
}

class RotateImageTests {
    @Test
    fun `test rotate when rotation is correct`() {
        val expected = arrayOf(
            intArrayOf(7, 4, 1),
            intArrayOf(8, 5, 2),
            intArrayOf(9, 6, 3)
        )
        val image =
            arrayOf(
                intArrayOf(1, 2, 3),
                intArrayOf(4, 5, 6),
                intArrayOf(7, 8, 9)
            )
        rotate(image)
        assertTrue(expected.contentDeepEquals(image))
    }
    @Test
    fun `test rotate when rotation is correct 2`() {
        //Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
        //Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]

        val expected = arrayOf(
            intArrayOf(15,13,2,5),
            intArrayOf(14,3,4,1),
            intArrayOf(12,6,8,9),
            intArrayOf(16,7,10,11)
        )
        val image =
            arrayOf(
                intArrayOf(5,1,9,11),
                intArrayOf(2,4,8,10),
                intArrayOf(13,3,6,7),
                intArrayOf(15,14,12,16),
            )
        rotate(image)
        assertTrue(expected.contentDeepEquals(image))
    }
}