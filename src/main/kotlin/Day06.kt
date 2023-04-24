import java.io.File

fun main() {
    val input = File("src/main/resources", "06.txt").readText().trim()
    println(firstDistinct(input, 4))
    println(firstDistinct(input, 14))
}

fun firstDistinct(input: String, n: Int): Int {
    val first = generateSequence(0) { it + 1 }
        .first { i -> input.substring(i, i + n).toSet().size == n }
    return first + n
}

