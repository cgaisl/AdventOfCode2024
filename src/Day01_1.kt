import kotlin.math.absoluteValue

fun main() {
    val input = readInput("Day01").map { it.split("   ") }

    val leftList = input.map { it.first() }.sorted()
    val rightList = input.map { it.last() }.sorted()

    val resultList = leftList.zip(rightList)

    val result = resultList.sumOf { (first, second) -> (first.toInt() - second.toInt()).absoluteValue }

    println(result)
}
