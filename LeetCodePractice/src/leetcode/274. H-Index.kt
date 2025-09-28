package leetcode

import kotlin.math.min
// good explanation
//https://leetcode.com/problems/h-index/solutions/785570/java-100-no-sort-o-n-explanation

fun hIndex2(citations: IntArray): Int {
    val papers = IntArray(citations.size + 1) { 0 }
    citations.forEach { x -> papers[min(x, citations.size)]++ }
    var totalPapers = 0
    for(hIndex in citations.size downTo 0) {
        totalPapers += papers[hIndex]
        if(totalPapers >= hIndex)
            return  hIndex
    }
    return 0
}

fun hIndex(citations: IntArray): Int {
    val n = citations.size
    citations.sort() //nlogn
    citations.forEachIndexed { i, cit ->
        if (cit >= n - i) {
            return n - i
        }
    }
    return 0
}

fun main() {
    println(hIndex(intArrayOf(1)))
    println(hIndex(intArrayOf(3,0,6,1,5)))

}