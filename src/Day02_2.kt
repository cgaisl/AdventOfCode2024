import kotlin.math.absoluteValue

fun main() {
    val input = readInput("Day02")

    val parsedInput = input.map { it.split(" ").map { it.toInt() } }

    val result = parsedInput
        .map { inputLine ->
            List(inputLine.size) {
                inputLine.filterIndexed { index, _ -> index != it }
            }
        }
        .sumOf {
            when {
                it.any { it.isSafe() } -> 1L
                else -> 0L
            }
        }

    println(result)
}


private fun List<Int>.isSafe(): Boolean {
    val isSafeDecreasing = windowed(2).fold(true) { acc, (a, b) ->
        acc && b < a && (b - a).absoluteValue <= 3
    }
    val isSafeIncreasing = windowed(2).fold(true) { acc, (a, b) ->
        acc && b > a && (b - a).absoluteValue <= 3
    }

    return isSafeDecreasing || isSafeIncreasing
}
