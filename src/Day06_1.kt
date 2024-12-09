fun main() {
    val field = readInput("Day06").toCharArray2()

    val left = '<'
    val right = '>'
    val up = '^'
    val down = 'v'
    val directions = setOf(left, right, up, down)

    var posX = 0
    var posY = 0
    field.forEachIndexed2 { y, x, c ->
        if (c in directions) {
            posY = y
            posX = x
            return@forEachIndexed2
        }
    }

    while (field.any { it.any { it in directions } }) {
        var currentDirection = field[posY][posX]

        val facesObstacle = runCatching {
            when (currentDirection) {
                left -> field[posY][posX - 1] == '#'
                right -> field[posY][posX + 1] == '#'
                up -> field[posY - 1][posX] == '#'
                down -> field[posY + 1][posX] == '#'
                else -> false
            }
        }.getOrDefault(false)

        if (facesObstacle) currentDirection = when (currentDirection) {
            up -> right
            right -> down
            down -> left
            left -> up
            else -> error("Invalid direction")
        }

        field[posY][posX] = 'X'

        when (currentDirection) {
            left -> posX--
            right -> posX++
            up -> posY--
            down -> posY++
        }

        try {
            field[posY][posX] = currentDirection
        } catch (_: ArrayIndexOutOfBoundsException) {
            break
        }
    }

    println(field.sumOf { it.count { it == 'X' } })
}
