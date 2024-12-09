import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val field = readInput("Day06").toCharArray2()

    var count = AtomicInteger(0)

    val jobs = mutableListOf<Job>()

    field.forEachIndexed2 { y, x, c ->
        jobs.add(
            GlobalScope.launch {
                if (field[y][x] in setOf('<', '>', '^', 'v')) return@launch

                val fieldCopy = field.map { it.copyOf() }.toTypedArray()
                fieldCopy[y][x] = '#'
                if (isInfiniteLoop(fieldCopy)) count.andIncrement.also { println(it)}

//                println("$y, $x of field ${field.size2()}, count = $count")
            }
        )
    }

    jobs.forEach { it.join() }

    println(count)
}

fun isInfiniteLoop(field: CharArray2): Boolean {
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

    val pastPositionsAndDirection = mutableSetOf<Triple<Int, Int, Char>>()

    while (field.any { it.any { it in directions } }) {
        var currentDirection = field[posY][posX]

        if (pastPositionsAndDirection.contains(Triple(posY, posX, currentDirection))) return true
        pastPositionsAndDirection.add(Triple(posY, posX, currentDirection))

        fun facesObstacle(): Boolean {
            return runCatching {
                when (currentDirection) {
                    left -> field[posY][posX - 1] == '#'
                    right -> field[posY][posX + 1] == '#'
                    up -> field[posY - 1][posX] == '#'
                    down -> field[posY + 1][posX] == '#'
                    else -> false
                }
            }.getOrDefault(false)
        }

        while (facesObstacle()) currentDirection = when (currentDirection) {
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

    return false
}
