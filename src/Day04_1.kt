fun main() {
    val input = readInput("Day04").toCharArray2()

    val horizontal = input.findXmasHorizontally()

    val vertical = input.transposed().findXmasHorizontally()

    val diagonals = input.diagonalGrouping(DiagonalDirection.TOP_LEFT_TO_BOTTOM_RIGHT).findXmasHorizontally() +
            input.diagonalGrouping(DiagonalDirection.TOP_RIGHT_TO_BOTTOM_LEFT).findXmasHorizontally()

    println(horizontal + vertical + diagonals)
}

private fun CharArray2.findXmasHorizontally(): Int  = sumOf {
    val regular = Regex("XMAS").findAll(it.joinToString("")).map { it.value }.count()
    val reverse = Regex("SAMX").findAll(it.joinToString("")).map { it.value }.count()
    regular + reverse
}
