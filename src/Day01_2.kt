fun main() {
    val input = readInput("Day01").map { it.split("   ") }

    val leftList = input.map { it.first() }.sorted()
    val rightList = input.map { it.last() }.groupBy { it }

    val result = leftList.sumOf { number ->
        number.toInt() * (rightList[number]?.size ?: 0)
    }

    println(result)
}
