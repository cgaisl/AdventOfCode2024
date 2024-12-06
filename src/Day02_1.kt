import kotlin.math.absoluteValue

fun main() {
    val input = readInput("Day02")

    val parsedInput = input.map { it.split(" ").map { it.toInt() } }

    val result = parsedInput.sumOf {
        val isSafeDecreasing = it.windowed(2).fold(true) { acc, (a, b) ->
            acc && b < a && (b - a).absoluteValue <= 3
        }
        val isSafeIncreasing = it.windowed(2).fold(true) { acc, (a, b) ->
            acc && b > a  && (b - a).absoluteValue <= 3
        }

        when {
            isSafeDecreasing || isSafeIncreasing -> 1L
            else -> 0L
        }
    }

    println(result)
}
