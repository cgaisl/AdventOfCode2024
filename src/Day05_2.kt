fun main() {
    val input = readInput("Day05")

    val rules: MutableMap<Int, MutableSet<Int>> = mutableMapOf()

    input
        .takeWhile { it.isNotEmpty() }
        .forEach {
            val rule = it.split("|").map { it.toInt() }.let { it[0] to it[1] }
            rules.computeIfAbsent(rule.first) { mutableSetOf() }.add(rule.second)
        }

    val updates = input
        .dropWhile { it.isNotEmpty() }
        .drop(1)
        .map { it.split(",").map { it.toInt() } }

    fun List<Int>.isValid(): Boolean {
        val visited = mutableSetOf<Int>()

        return all { page ->
            val preconditions = rules[page] ?: emptySet()

            preconditions
                .all { it !in this || it !in visited }
                .also { visited.add(page) }
        }
    }

    val invalidUpdates = updates.filter { !it.isValid() }

    fun List<Int>.fixed(): List<Int> {
        if (isValid()) return this

        val visited = mutableSetOf<Int>()
        val firstInvalidIndex = indexOfFirst { page ->
            val preconditions = rules[page] ?: emptySet()

            preconditions
                .any { it in visited }
                .also { visited.add(page) }
        }

        return withIndicesSwapped(firstInvalidIndex, firstInvalidIndex - 1).fixed()
    }

    val result = invalidUpdates
        .map { it.fixed() }
        .sumOf { it[it.size / 2] }

    println(result)
}
