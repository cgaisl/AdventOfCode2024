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

    val validUpdates = updates
        .filter { update ->
            val visited = mutableSetOf<Int>()

            update.all { page ->
                val preconditions = rules[page] ?: emptySet()

                preconditions
                    .all { it !in update || it !in visited }
                    .also { visited.add(page) }
            }
        }

    val result = validUpdates.sumOf { it[it.size / 2] }

    println(result)
}
