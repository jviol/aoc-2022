import utils.toPair
import java.io.File

fun main() {
    val lines = File("src/main/resources", "04.txt").readLines()
    val rangePairs = lines
        .map { it.split(',').map(::parseRange).toPair() }
    // Part 1
    println(rangePairs.count { (first, second) ->
        first.contains(second) || second.contains(first)
    })
    // Part 2
    println(rangePairs.count { (a, b) ->
        a.overlaps(b)
    })
}

private fun parseRange(rangeStr: String): IntRange {
    rangeStr.split("-").map(String::toInt).toPair().apply {
        return first.rangeTo(second)
    }
}

fun IntRange.contains(other: IntRange): Boolean =
    contains(other.first) && contains(other.last)

fun IntRange.overlaps(other: IntRange): Boolean =
    other.any { it in this }

