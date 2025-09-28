package leetcode

private fun createLines(words: Array<String>, maxWidth: Int): List<List<String>> {
    var lineIdx = 0
    return words.fold(mutableListOf(mutableListOf<String>())) { lines, word ->
        //words length + spaces among words
        val currentLength = lines[lineIdx].sumOf { it.length } + lines[lineIdx].size - 1
        //add word length
        if (currentLength + word.length + 1 > maxWidth) {
            lineIdx++
            lines.add(mutableListOf<String>())
        }
        lines[lineIdx].add(word)
        lines
    }
}

private fun justifyLines(wordsList: List<List<String>>, maxWidth: Int): List<String> {
    return wordsList.map { it.toMutableList() }
        .mapIndexed { idx, line ->
            if (line.size == 1 || idx == wordsList.lastIndex) {
                line[line.lastIndex] = line.last().plus(" ".repeat(maxWidth - line.sumOf { it.length } - line.size + 1))
            } else {
                var wordIdx = 0
                while (line.sumOf { it.length } + line.size - 1 < maxWidth) {
                    line[wordIdx] = "${line[wordIdx]} "
                    wordIdx = if (wordIdx >= line.size - 2) 0 else wordIdx + 1
                }
            }
            line.joinToString(" ")
        }
}

fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
    return justifyLines(createLines(words, maxWidth), maxWidth)
}

fun main() {
    println(fullJustify(arrayOf("This", "is", "an", "example", "of", "text", "justification."), 16))
//    assertEquals(
//        fullJustify(
//            arrayOf("This", "is", "an", "example", "of", "text", "justification."),
//            16
//        ),
//        listOf(
//            "This    is    an",
//            "example  of text",
//            "justification.  "
//        )
//    )
}

/*

class Solution {
    fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
        val lines = getLines(words, maxWidth)
        return withPadding(lines, maxWidth)
    }

    private fun getLines(words: Array<String>, maxWidth: Int): List<List<String>> {
        var lineIndex = 0

        return words.fold(mutableListOf(mutableListOf<String>())) { lines, word ->
            val currentLength = lines[lineIndex].sumOf { it.length } + lines[lineIndex].size - 1

            if (currentLength + word.length + 1 <= maxWidth) {
                lines[lineIndex].add(word)
            } else {
                lines.add(mutableListOf(word))
                lineIndex++
            }

            lines
        }
    }

    private fun withPadding(lines: List<List<String>>, maxWidth: Int) =
        lines
            .map { it.toMutableList() }
            .mapIndexed { index, line ->
                if (line.size == 1 || index == lines.lastIndex) {
                    line[line.lastIndex] =
                        line.last().plus(" ".repeat(maxWidth - line.sumOf { it.length } - line.size + 1))
                } else {
                    var wordIndex = 0

                    while (line.sumOf { it.length } + line.size - 1 < maxWidth) {
                        line[wordIndex] = "${line[wordIndex]} "

                        wordIndex = if (wordIndex >= line.size - 2) {
                            0
                        } else {
                            wordIndex + 1
                        }
                    }

                }

                line.joinToString(separator = " ")
            }
}
* */