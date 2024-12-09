typealias CharArray2 = Array<CharArray>

fun List<String>.toCharArray2() = Array(size) { get(it).toCharArray() }

fun CharArray2.size2(): P2 {
    val n = size
    val m = get(0).size
    for (i in 1..<n) require(get(i).size == m) { "Row $i has size ${get(i)}, but expected $m" }
    return P2(n, m)
}

inline fun CharArray2.forEachIndexed2(action: (i: Int, j: Int, c: Char) -> Unit) {
    for (i in indices) {
        val b = get(i)
        for (j in b.indices) {
            action(i, j, b[j])
        }
    }
}

inline fun <R> CharArray2.mapIndexed2NotNull(transform: (i: Int, j: Int, c: Char) -> R?): List<R> = buildList {
    forEachIndexed2 { i, j, c ->
        transform(i, j, c)?.let { add(it) }
    }
}

inline fun CharArray2.toListOfP2If(predicate: (c: Char) -> Boolean): List<P2> = buildList {
    forEachIndexed2 { i, j, c ->
        if (predicate(c)) add(P2(i, j))
    }
}


enum class DiagonalDirection {
    TOP_LEFT_TO_BOTTOM_RIGHT,
    TOP_RIGHT_TO_BOTTOM_LEFT
}

/**
 * For the input matrix:
 *
 * A B C
 * D E F
 * G H I
 * The output would be:
 *
 * Top-Left to Bottom-Right Diagonals:
 * A
 * BD
 * CEG
 * FH
 * I
 *
 * Top-Right to Bottom-Left Diagonals:
 * C
 * BF
 * AEI
 * DH
 * G
 */
fun Array<CharArray>.diagonalGrouping(direction: DiagonalDirection): Array<CharArray> {
    val rows = this.size
    val cols = this[0].size
    val diagonals = mutableListOf<MutableList<Char>>()

    when (direction) {
        DiagonalDirection.TOP_LEFT_TO_BOTTOM_RIGHT -> {
            for (d in 0 until (rows + cols - 1)) {
                val diagonal = mutableListOf<Char>()
                for (i in 0 until rows) {
                    val j = d - i
                    if (j in 0 until cols) {
                        diagonal.add(this[i][j])
                    }
                }
                diagonals.add(diagonal)
            }
        }
        DiagonalDirection.TOP_RIGHT_TO_BOTTOM_LEFT -> {
            // Traverse diagonals based on i - j
            for (d in -(cols - 1) until rows) {
                val diagonal = mutableListOf<Char>()
                for (i in 0 until rows) {
                    val j = i - d
                    if (j in 0 until cols) {
                        diagonal.add(this[i][j])
                    }
                }
                diagonals.add(diagonal)
            }
        }
    }

    return diagonals.map { it.toCharArray() }.toTypedArray()
}

fun CharArray2.transposed(): CharArray2 {
    val rowCount = size
    val colCount = firstOrNull()?.size ?: 0

    val transposed = Array(colCount) { CharArray(rowCount) }

    for (i in indices) {
        for (j in get(i).indices) {
            transposed[j][i] = get(i)[j]
        }
    }

    return transposed
}

/**
 * Applies the given [transform] function to each window of the specified [width] and [height] in the 2D array.
 */
fun<T> CharArray2.windowed(width: Int, height: Int, transform: (CharArray2) -> T): List<T>{
    val rowCount = size
    val colCount = firstOrNull()?.size ?: 0

    if(width > colCount || height > rowCount) return emptyList()

    val resultList = mutableListOf<T>()

    for (i in 0..rowCount - height) {
        for (j in 0..colCount - width) {
            val window = Array(height) { y ->
                CharArray(width) { x ->
                    get(i + y)[j + x]
                }
            }
            resultList.add((transform(window)))
        }
    }

    return resultList
}
