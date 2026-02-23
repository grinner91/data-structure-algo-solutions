package neetcode.binarysearch

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SearchMatrixBinarySearch1D {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        fun binarySearch(row: IntArray): Boolean {
            var l = 0
            var r = row.lastIndex
            while (l <= r) {
                val mid = l + (r - l) / 2
                when {
                    row[mid] == target -> return true
                    row[mid] < target -> l = mid + 1
                    else -> r = mid - 1
                }
            }
            return false
        }
        for (row in matrix) {
            if (target >= row[0] && target <= row[row.lastIndex]) {
                if (binarySearch(row))
                    return true
            }
        }
        return false
    }
}

class SearchMatrixStairCase {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        val rows = matrix.size
        if (rows <= 0) return false

        var r = 0
        var c = matrix[0].size - 1
        while (r < rows && c >= 0) {
            val x = matrix[r][c]
            when {
                target == x -> return true
                target > x -> r++
                else -> c--
            }
        }
        return false
    }
}
//
//
//class SearchMatrixBinarySearch1D {
//    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
//
//    }
//}


class SearchMatrixTest {

    private val impls = listOf(
        SearchMatrixBinarySearch1D()::searchMatrix,
//        SearchMatrixStairCase()::searchMatrix,
    )

    private val matrix = arrayOf(
        intArrayOf(1, 3, 5, 7),
        intArrayOf(10, 11, 16, 20),
        intArrayOf(23, 30, 34, 60)
    )

    @Test
    fun `target exists`() {
        impls.forEach { f ->
            assertTrue(f(matrix, 3))
        }
    }

    @Test
    fun `target does not exist`() {
        impls.forEach { f ->
            assertFalse(f(matrix, 13))
        }
    }

    @Test
    fun `single element`() {
        val single = arrayOf(intArrayOf(5))
        impls.forEach { f ->
            assertTrue(f(single, 5))
            assertFalse(f(single, 2))
        }
    }

    @Test
    fun `empty matrix`() {
        val empty = arrayOf<IntArray>()
        impls.forEach { f ->
            assertFalse(f(empty, 1))
        }
    }
}
