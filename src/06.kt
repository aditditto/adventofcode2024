import java.io.File

fun main() {
    val map = mutableListOf<MutableList<Char>>()
    File("resources/06.in").forEachLine { line ->
        map.add(mutableListOf())
        for (char in line) {
            map.last().add(char)
        }
    }

    println(first(map))
}

enum class Direction(val icon: Char, val deltaI: Int, val deltaJ: Int) {
    UP('^', -1, 0) {
        override fun nextDirection() = RIGHT
    },
    DOWN('v', 1, 0) {
        override fun nextDirection() = LEFT
    },
    LEFT('<', 0, -1) {
        override fun nextDirection() = UP
    },
    RIGHT('>', 0, 1) {
        override fun nextDirection() = DOWN
    };

    abstract fun nextDirection(): Direction
}

fun first(map: MutableList<MutableList<Char>>): Int {
    val icons = Direction.entries.map { it.icon }
    var iconI = 0
    var iconJ = 0
    var sum = 1
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (icons.contains(map[i][j])) {
                iconI = i
                iconJ = j
            }
        }
    }

    var currentDirection = Direction.entries.find { it.icon == map[iconI][iconJ] }!!
    val blockedCell = '#'
    val newCell = '.'
    val visitedCell = 'X'
    val mapLimitI = map.size
    val mapLimitJ = map[0].size
    while (true) {
        val nextCellI = iconI + currentDirection.deltaI
        val nextCellJ = iconJ + currentDirection.deltaJ
        if (nextCellI < 0 || nextCellJ < 0 || nextCellI == mapLimitI || nextCellJ == mapLimitJ) {
            break
        }

        val nextCell = map[nextCellI][nextCellJ]
        if (nextCell == blockedCell) {
            currentDirection = currentDirection.nextDirection()
            map[iconI][iconJ] = currentDirection.icon
        } else {
            if (nextCell == newCell) {
                sum++
            }
            map[iconI][iconJ] = visitedCell
            map[nextCellI][nextCellJ] = currentDirection.icon
            iconI = nextCellI
            iconJ = nextCellJ
        }
    }
    for (line in map) {
        println(line)
    }

    return sum
}
