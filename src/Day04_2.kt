fun main() {
    val input = readInput("Day04").toCharArray2()

    val msSet = setOf('M', 'S')

    val result = input
        .windowed(3, 3) { window ->
            val topLeftDownRight = setOf(window[0][0], window[2][2])
            val topRightDownLeft = setOf(window[0][2], window[2][0])

            window[1][1] == 'A' && topLeftDownRight == msSet && topRightDownLeft == msSet
        }
        .count { it }

    println(result)
}
