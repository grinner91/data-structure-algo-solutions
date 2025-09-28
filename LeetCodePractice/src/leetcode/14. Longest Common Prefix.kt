package leetcode

fun longestCommonPrefix(strs: Array<String>): String = buildString {
    strs[0]
        .forEachIndexed { i, c ->
            if (strs.all { it[i] == c })
                append(c)
            else
                return toString()
        }
}

fun main() {
    println(longestCommonPrefix(arrayOf("flower", "flow", "flight"))) // fl
    println(longestCommonPrefix(arrayOf("dog", "racecar", "car")))
}

//fun longestCommonPrefix(strs: Array<String>) = strs.reduce(String::commonPrefixWith)
fun longestCommonPrefix1(a: Array<String>) = buildString {
    a.minBy { it.length }
        ?.forEachIndexed { i, c ->
            if (a.all { it[i] == c })
                append(c)
            else return toString()
        }
}