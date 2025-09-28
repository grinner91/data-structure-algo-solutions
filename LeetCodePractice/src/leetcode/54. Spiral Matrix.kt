package leetcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun spiralOrder(matrix: Array<IntArray>): List<Int> {
    var nums = mutableListOf<Int>()
    var rowStart = 0
    var rowEnd = matrix.size - 1
    var colStart = 0
    var colEnd = matrix[0].size - 1

    while (rowStart <= rowEnd && colStart <= colEnd) {
        //traverse - top row left to right
        for (c in colStart..colEnd) {
            nums.add(matrix[rowStart][c])
        }
        rowStart++
        //traverse - last column top to bottom
        for(r in rowStart..rowEnd) {
            nums.add(matrix[r][colEnd])
        }
        colEnd--
        //traverse - bottom row right to left
        if(rowStart <= rowEnd) {
            for (c in colEnd downTo colStart step 1) {
                nums.add(matrix[rowEnd][c])
            }
        }
        rowEnd--
        //traverse - first column bottom to top
        if(colStart <= colEnd) {
            for (r in rowEnd downTo rowStart step 1) {
                nums.add(matrix[r][colStart])
            }
        }
        colStart++
    }

    return nums.toList()
}

class SpiralOrderTests {
    @Test
    fun `should return list when given 3x4 matrix`() {
        val expected = listOf(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7)
        assertEquals(
            expected,
            spiralOrder(
                arrayOf(
                    intArrayOf(1, 2, 3, 4),
                    intArrayOf(5, 6, 7, 8),
                    intArrayOf(9, 10, 11, 12)
                )
            )
        )

    }
}