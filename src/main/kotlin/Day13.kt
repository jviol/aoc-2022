import utils.toPair
import java.io.File

sealed interface Value : Comparable<Value>
class IntValue(val value: Int) : Value {
    override fun compareTo(other: Value): Int {
        return when (other) {
            is IntValue -> this.value.compareTo(other.value)
            else -> ListValue(listOf(this)).compareTo(other)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}

class ListValue(val elements: List<Value>) : Value {
    override fun compareTo(other: Value): Int {
        if (other !is ListValue) return compareTo(ListValue(listOf(other)))
        else {
            fun compareIndex(i: Int): Int? {
                val myElement = elements.getOrNull(i)
                val otherElement = other.elements.getOrNull(i)
                return when {
                    myElement == null && otherElement == null -> null
                    myElement == null -> -1
                    otherElement == null -> 1
                    else -> {
                        val comparison = myElement.compareTo(otherElement)
                        return if (comparison == 0) compareIndex(i + 1)
                        else comparison
                    }
                }
            }
            return compareIndex(0) ?: 0
        }
    }

    override fun toString(): String {
        return elements.toString()
    }
}


fun evaluateLine(string: String): ListValue {
    val queue = string.iterator()
    var c = queue.nextChar()

    fun parseInt(): IntValue {
        val stringBuilder = StringBuilder()
        do {
            stringBuilder.append(c)
            c = queue.nextChar()
        } while (c.isDigit())
        val value = stringBuilder.toString().toInt()
        return IntValue(value)
    }

    fun parseList(): ListValue {
        val elements = mutableListOf<Value>()
        c = queue.nextChar()
        while (c != ']') {
            when {
                c == ',' -> c = queue.nextChar()
                c.isDigit() -> elements.add(parseInt())
                c == '[' -> elements.add(parseList())
            }
        }
        if (queue.hasNext()) {
            c = queue.nextChar()
        }
        return ListValue(elements)
    }

    return parseList()
}

fun main() {
    val pairs: List<Pair<ListValue, ListValue>> = File("src/main/resources/13.txt").readText().split("\n\n")
        .map { it.split("\n") }
        .map { it.filter(String::isNotBlank).map(::evaluateLine).toPair() }
    val part1 = pairs
        .mapIndexedNotNull { i, (first, second) ->
            if (first < second) i + 1 else null
        }.sum()
    println(part1)

    // part 2
    val packets = pairs.flatMap { it.toList() }.toMutableList()
    val divider1 = evaluateLine("[[2]]")
    val divider2 = evaluateLine("[[6]]")
    packets.add(divider1)
    packets.add(divider2)
    packets.sort()
    println((packets.indexOf(divider1) + 1) * (packets.indexOf(divider2) + 1))
}