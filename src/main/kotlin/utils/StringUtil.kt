package utils

fun String.indexOfOrThrow(c: Char): Int {
    val i = indexOf(c)
    if (i < 0) throw IllegalArgumentException()
    return i
}


fun String.indexesOf(c: Char) =
    mapIndexedNotNull() { index, elem ->
        index.takeIf { elem == c }
    }