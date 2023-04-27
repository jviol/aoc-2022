import java.io.File

class Monkey(
    val items: ArrayDeque<Int>,
    val operation: (Int) -> Long,
    val divisor: Int,
    val trueCase: Int,
    val falseCase: Int
) {
    var inspections: Int = 0

    private fun test(item: Int) =
        if (item % divisor == 0) trueCase else falseCase

    fun turn() {
        while (!items.isEmpty()) {
            var item = items.removeFirst()
            inspections++
            item = (operation(item) % commonDivisor).toInt()
            monkeys[test(item)].items.add(item)
        }
    }
}

private fun parseMonkeys(): List<Monkey> {
    val lines = File("src/main/resources/11.txt").readLines().iterator()
    val monkeys = mutableListOf<Monkey>()
    while (lines.hasNext()) {
        lines.next()
        val items = lines.next().split(": ")[1].split(", ").map(String::toInt)

        val opLine = lines.next().split("= old ")[1]
        val operator: (Int, Int) -> Long = when (opLine[0]) {
            '+' -> { a,b -> a.toLong() + b.toLong() }
            '*' -> { a,b -> a.toLong() * b.toLong() }
            else -> throw IllegalArgumentException()
        }
        val term = opLine.substring(1).trim().toIntOrNull()
        val operation: (Int) -> Long = { item ->
            operator(item, term ?: item)
        }

        val divisor = lines.next().split("by ")[1].toInt()
        val trueCase = lines.next().split("monkey ")[1].toInt()
        val falseCase = lines.next().split("monkey ")[1].toInt()

        val monkey = Monkey(ArrayDeque(items), operation, divisor, trueCase, falseCase)
        monkeys.add(monkey)
        if (lines.hasNext()) lines.next()
    }
    return monkeys
}

val monkeys = parseMonkeys()
val commonDivisor = monkeys.map { it.divisor }.reduce(Int::times)

fun main() {
    println(commonDivisor)
    repeat(10_000) { i ->
//        println("round ${i + 1}")
        monkeys.forEach(Monkey::turn)
//        if (i in listOf(1, 20, 1000, 2000, 10000))
//        monkeys.forEachIndexed { j, monkey ->
//            println("$j: ${monkey.items}")
//        }
    }

    val monkeyBusiness = monkeys.map { it.inspections.toULong() }
        .sorted().takeLast(2).reduce(ULong::times)


    println(monkeys.map { it.inspections })
    println(monkeyBusiness)

}

