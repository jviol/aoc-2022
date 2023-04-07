import java.io.File

fun main() {
    val elves = mutableListOf<Int>()
    var elf = 0
    val cals = File("src/main/resources", "01.txt").readLines()
        .map { it.toIntOrNull() }
    for (cal in cals) {
        if (cal != null) {
            elf += cal
        } else {
            elves.add(elf)
            elf = 0
        }
    }
    println(elves.max())
    println(elves.sorted().takeLast(3).sum())
}