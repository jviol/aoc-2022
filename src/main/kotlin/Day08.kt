import java.io.File

fun main() {
    val lines = File("src/main/resources", "08.txt").readLines().map(String::trim).filter(String::isNotBlank)
    val heights = lines.map { it.map(Char::digitToInt) }
    fun isVisibleFromTop(y0: Int, x0: Int): Boolean {
        val height = heights[y0][x0]
        return IntRange(0, y0 - 1).all { y -> heights[y][x0] < height }
    }

    fun isVisibleFromBottom(y0: Int, x0: Int): Boolean {
        val height = heights[y0][x0]
        return IntRange(y0 + 1, heights.lastIndex).all { y -> heights[y][x0] < height }
    }

    fun isVisibleFromLeft(y0: Int, x0: Int): Boolean {
        val height = heights[y0][x0]
        return IntRange(0, x0 - 1).all { x -> heights[y0][x] < height }
    }

    fun isVisibleFromRight(y0: Int, x0: Int): Boolean {
        val height = heights[y0][x0]
        return IntRange(x0 + 1, heights[y0].lastIndex).all { x -> heights[y0][x] < height }
    }

    fun isVisible(x0: Int, y0: Int): Boolean =
        isVisibleFromTop(y0, x0)
                || isVisibleFromBottom(y0, x0)
                || isVisibleFromLeft(y0, x0)
                || isVisibleFromRight(y0, x0)

    // Part 1
    val visible = heights.indices
        .flatMap { y -> heights[y].indices.map { x -> Pair(x, y) } }
        .count { (x, y) -> isVisible(x, y) }
    println(visible)

    // Part 2

}
