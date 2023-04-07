import java.io.File

fun String.indexOfOrThrow(c: Char): Int {
    val i = indexOf(c)
    if (i < 0) throw IllegalArgumentException()
    return i
}

fun shapePoints(x: Char): Int = "XYZ".indexOfOrThrow(x) + 1

fun winPoints(a: Char, x: Char): Int {
    val ai = "ABC".indexOfOrThrow(a)
    val xi = "XYZ".indexOfOrThrow(x)
    return ((xi - ai + 4) % 3) * 3
}

fun myChoice(a: Char, x: Char): Char {
    val ai = "ABC".indexOfOrThrow(a)
    val xi = "XYZ".indexOfOrThrow(x)
    return "XYZ"[(ai + xi + 2) % 3]
}

fun main() {
    val lines = File("src/main/resources", "02.txt").readLines()
    val rounds = lines.map { it.replace(" ", "") }
    // Part 1
    println(rounds.sumOf { shapePoints(it[1]) + winPoints(it[0], it[1]) })
    // Part 2
    println(rounds.sumOf {
        val a = it[0]
        val x = myChoice(a, it[1])
        shapePoints(x) + winPoints(a, x)
    })
}