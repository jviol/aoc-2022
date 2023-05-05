import utils.indexesOf
import java.io.File
import java.util.NoSuchElementException

val map = File("src/main/resources/12.txt").readLines()

typealias Pos = Pair<Int, Int>

fun getHeight(pos: Pos): Int {
    val (x, y) = pos
    return when (val c = map[y][x]) {
        'S' -> 'a'
        'E' -> 'z'
        else -> c
    }.code
}

fun neighbours(pos: Pos): List<Pos> {
    val (x, y) = pos
    val res = ArrayList<Pos>(4)
    if (x > 0) {
        res.add(Pos(x - 1, y))
    }
    if (x < map[0].length - 1) {
        res.add(Pos(x + 1, y))
    }
    if (y > 0) {
        res.add(Pos(x, y - 1))
    }
    if (y < map.size - 1) {
        res.add(Pos(x, y + 1))
    }
    return res
}

fun canTravelTo(pos: Pos): List<Pos> {
    val h0 = getHeight(pos)
    return neighbours(pos)
        .filter { getHeight(it) >= h0 - 1 }
}

fun positionOf(c: Char): Pos {
    for ((y, line) in map.withIndex()) {
        val x = line.indexOf(c)
        if (x != -1) {
            return x to y
        }
    }
    throw NoSuchElementException()
}

fun main() {
    assert(map.map(String::length).distinct().size == 1)
    val end: Pos = positionOf('E')
    val distanceToEnd: MutableMap<Pos, Int> = mutableMapOf(end to 0)
    val queue = ArrayDeque<Pos>()
    val seen = mutableSetOf<Pos>()
    queue.add(end)
    seen.add(end)
    while (!queue.isEmpty()) {
        val cur = queue.removeFirst()
        val d = distanceToEnd[cur]!!
        for (node in canTravelTo(cur)) {
            if (node !in seen) {
                queue.addLast(node)
                seen.add(node)
                distanceToEnd[node] = d + 1
            }
        }
    }
    println("Part 1: ${distanceToEnd[positionOf('S')]}")
    val shortestFromAnyA = map.withIndex()
        .flatMap { (y, line) ->
            line.indexesOf('a')
                .map { x -> x to y }
        }
        .mapNotNull { distanceToEnd[it] }
        .min()
    println("Part 2: $shortestFromAnyA")

}
