package utils

fun CharSequence.indexOfOrThrow(c: Char): Int {
    val i = indexOf(c)
    if (i < 0) throw IllegalArgumentException()
    return i
}


fun CharSequence.indexesOf(c: Char): List<Int> =
    mapIndexedNotNull { index, elem ->
        index.takeIf { elem == c }
    }