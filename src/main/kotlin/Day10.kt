import java.io.File

fun main() {
    val instructions = File("src/main/resources/10.txt").readLines()
//    println(instructions)
    var cycle = 1
    var x = 1
    val signalStrengths = mutableListOf<Int>()
    val screen = Array(6) { CharArray(40) { '.' } }
    fun cycle() {
        val pos = (cycle - 1) % 40
        val line = (cycle - 1) / 40
        if (pos in x - 1..x + 1) {
            screen[line][pos] = '#'
        }
        cycle++
        if ((cycle - 20) % 40 == 0) {
            signalStrengths.add(cycle * x)
        }
    }
    for (instruction in instructions) {
        cycle()
        if (instruction.startsWith("addx ")) {
            cycle()
            x += instruction.substring(5).toInt()
        }
    }
//    println(signalStrengths.sum())
    screen.forEach { println(String(it)) }
}