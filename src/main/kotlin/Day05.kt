import java.io.File

fun main() {
    day05(true)
    day05(false)
}

private fun day05(part1: Boolean) {
    val lines = File("src/main/resources", "05.txt").readLines()
    val stacks = Array<ArrayDeque<Char>>(9) { ArrayDeque() }
    val pattern = Regex("""move (\d+) from (\d+) to (\d+)""")
    for (line in lines) {
        if (line.startsWith("[")) {
            for ((i, c) in line.withIndex()) {
                if (i % 4 == 1 && c.isLetter()) {
                    stacks[(i - 1) / 4].addFirst(c)
                }
            }
        } else {
            pattern.matchEntire(line)?.run {
                val moveCount = groupValues[1].toInt()
                val from = groupValues[2].toInt() - 1
                val to = groupValues[3].toInt() - 1
                var elements = IntRange(1, moveCount)
                    .map { stacks[from].removeLast() }
                if (!part1) {
                    elements = elements.asReversed()
                }
                stacks[to].addAll(elements)
            }
        }
    }
    println(stacks.map { it.last() }.joinToString(""))
}