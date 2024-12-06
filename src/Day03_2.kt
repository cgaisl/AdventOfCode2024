fun main() {
    val input = readInput("Day03")

    val regex = Regex("""mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)""")

    var isEnabled = true

    val result = input
        .map { regex.findAll(it).map { it.value }.toList() }
        .sumOf {
            var acc = 0
            it.forEach {
                when (it) {
                    "do()" -> isEnabled = true
                    "don't()" -> isEnabled = false
                    else -> {
                        if (isEnabled) {
                            acc += Regex("""mul\((\d+),(\d+)\)""")
                                .matchEntire(it)!!
                                .destructured
                                .let { (a, b) ->
                                    a.toInt() * b.toInt()
                                }
                        }
                    }
                }
            }

            acc
        }

    println(result)
}
