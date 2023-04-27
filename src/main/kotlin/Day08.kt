import java.io.File

class Day08 {
    companion object {
        val heights = File("src/main/resources", "08.txt").readLines()
            .map(String::trim)
            .filter(String::isNotBlank)
            .map { it.map(Char::digitToInt) }
        val coordinates = heights.indices
            .flatMap { y -> heights[y].indices.map { x -> Pair(x, y) } }
    }

    // Part 1
    object Part1 {
        private fun isVisibleFromTop(y0: Int, x0: Int): Boolean {
            val height = heights[y0][x0]
            return IntRange(0, y0 - 1).all { y -> heights[y][x0] < height }
        }

        private fun isVisibleFromBottom(y0: Int, x0: Int): Boolean {
            val height = heights[y0][x0]
            return IntRange(y0 + 1, heights.lastIndex).all { y -> heights[y][x0] < height }
        }

        private fun isVisibleFromLeft(y0: Int, x0: Int): Boolean {
            val height = heights[y0][x0]
            return IntRange(0, x0 - 1).all { x -> heights[y0][x] < height }
        }

        private fun isVisibleFromRight(y0: Int, x0: Int): Boolean {
            val height = heights[y0][x0]
            return IntRange(x0 + 1, heights[y0].lastIndex).all { x -> heights[y0][x] < height }
        }

        fun isVisible(x0: Int, y0: Int): Boolean =
            isVisibleFromTop(y0, x0)
                    || isVisibleFromBottom(y0, x0)
                    || isVisibleFromLeft(y0, x0)
                    || isVisibleFromRight(y0, x0)
    }


    object Part2 {
        private fun score(view: List<Int>, h0: Int): Int {
            var count = 0
            for (h in view) {
                count++
                if (h >= h0) {
                    break
                }
            }
            return count
        }

        fun scenicScore(x0: Int, y0: Int): Int {
            if (x0 == 0 || y0 == 0 || x0 == heights[y0].lastIndex || y0 == heights.lastIndex) {
                return 0
            }
            val height = heights[y0][x0]
            val viewUp = score((y0 - 1 downTo 0).map { y -> heights[y][x0] }, height)
            val viewDown = score((y0 + 1..heights.lastIndex).map { y -> heights[y][x0] }, height)
            val viewLeft = score(heights[y0].subList(0, x0).reversed(), height)
            val viewRight = score(heights[y0].subList(x0 + 1, heights[y0].size), height)
            return viewUp * viewDown * viewLeft * viewRight
        }
    }
}

fun main() {
    println(Day08.coordinates.count { (x, y) -> Day08.Part1.isVisible(x, y) })
    println(Day08.coordinates.maxOf { (x, y) -> Day08.Part2.scenicScore(x, y) })
}

