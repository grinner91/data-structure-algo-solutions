package leetcode

fun convert(s: String, numRows: Int): String {
    if (numRows <= 1) return s
    val zigzag = Array(numRows) { StringBuilder() }
    var rows = 0
    var goDown = true
    s.forEach { c ->
        zigzag[rows].append(c)
        if (goDown) rows++
        else rows--

        if (rows >= numRows - 1) goDown = false
        else if (rows <= 0) goDown = true

    }
    return zigzag.joinToString("")
}

fun main() {
    println(convert("PAYPALISHIRING", 3)) //"PAHNAPLSIIGYIR"
    println(convert("PAYPALISHIRING", 4)) //PINALSIGYAHRPI
}