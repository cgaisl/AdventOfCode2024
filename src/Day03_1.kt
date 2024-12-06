fun main() {
    val input = readInput("Day03")

    val regex = Regex("""mul\(\d{1,3},\d{1,3}\)""")

    val result = input
        .map {
            regex.findAll(it).map { it.value }.toList()
        }
        .flatten()
        .sumOf {
            Regex("""mul\((\d+),(\d+)\)""").matchEntire(it)!!.destructured.let { (a,b) ->
                a.toInt() * b.toInt()
            }
        }

    println(result)
}
