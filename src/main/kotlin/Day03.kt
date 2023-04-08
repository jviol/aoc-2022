import java.io.File

fun main() {
    val lines = File("src/main/resources", "03.txt").readLines()
    part1(lines)
    part2(lines)
}

private fun part1(lines: List<String>) {
    val rucksacks = lines
        .map { it.chunked(it.length / 2).map(String::toSet) }
    val shared = rucksacks
        .map { it.reduce(Set<Char>::intersect) }
        .onEach { assert(it.size == 1) }
        .map { it.first() }

    println(shared.sumOf {
        priority(it)
    })
}

private fun part2(lines: List<String>) {
    val groups = lines.chunked(3)
    println(groups.map { group ->
        assert(group.size == 3)
        val shared = group.map { it.toSet() }
            .reduce(Set<Char>::intersect)
        assert(shared.size == 1)
        shared.first()
    }.sumOf(::priority))
}

private fun priority(it: Char): Int =
    if (it.isUpperCase())
        it - 'A' + 27
    else
        it - 'a' + 1