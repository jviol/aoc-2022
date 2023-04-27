import java.io.File
import kotlin.math.abs

private fun updateTail(hx: Int, hy: Int, tail: Pair<Int, Int>): Pair<Int, Int> {
    var tx1 = tail.first
    var ty1 = tail.second
    if (abs(hx - tx1) > 1) {
        tx1 += if (hx > tx1) 1 else -1
        if (hy > ty1) {
            ty1 += 1
        } else if (hy < ty1) {
            ty1 -= 1
        }
    } else if (abs(hy - ty1) > 1) {
        ty1 += if (hy > ty1) 1 else -1
        if (hx > tx1) {
            tx1 += 1
        } else if (hx < tx1) {
            tx1 -= 1
        }
    }
    return Pair(tx1, ty1)
}

private fun part1(movePairs: List<Pair<Char, Int>>) {
    var hx = 0
    var hy = 0
    var tail = 0 to 0
    val visited = mutableSetOf(tail)
    movePairs.forEach { (dir, steps) ->
        repeat(steps) {
            when (dir) {
                'R' -> hx += 1
                'L' -> hx -= 1
                'U' -> hy += 1
                'D' -> hy -= 1
            }
            tail = updateTail(hx, hy, tail)
            visited.add(tail)
        }
    }
    println(visited.size)
}

fun part2(movePairs: List<Pair<Char, Int>>) {
    var hx = 0
    var hy = 0
    val tail = Array(9) { 0 to 0 }
    val visited = mutableSetOf(tail[8])
    movePairs.forEach { (dir, steps) ->
        repeat(steps) {
            when (dir) {
                'R' -> hx += 1
                'L' -> hx -= 1
                'U' -> hy += 1
                'D' -> hy -= 1
            }
            tail[0] = updateTail(hx, hy, tail[0])
            for (i in 1..8) {
                tail[i] = updateTail(tail[i - 1].first, tail[i - 1].second, tail[i])
            }
            visited.add(tail[8])
        }
    }
    println(visited.size)
}

fun main() {
    val movePairs = File("src/main/resources/09.txt").readLines()
        .map { it[0] to it.substring(2).toInt() }
    part1(movePairs)
    part2(movePairs)
}

