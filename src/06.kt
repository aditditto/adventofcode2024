import java.io.File

fun main() {
    val map = mutableListOf<MutableList<Char>>()
    File("resources/06.in").forEachLine { line ->
        map.add(mutableListOf())
        for (char in line) {
            map.last().add(char)
        }
    }

    val (firstAnswer, firstPos, hitObjects) = first(map)
    println(firstAnswer)
    println(second(map, firstPos, hitObjects))
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

fun first(originalMap: MutableList<MutableList<Char>>): Triple<Int, Pair<Int, Int>, MutableList<Pair<Int, Int>>> {
    val map = mutableListOf<MutableList<Char>>()
    for (i in originalMap.indices) {
        map.add(mutableListOf())
        for (j in originalMap[i].indices) {
            map[i].add(originalMap[i][j])
        }
    }

    val icons = Direction.entries.map { it.icon }
    var iconI = 0
    var iconJ = 0
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (icons.contains(map[i][j])) {
                iconI = i
                iconJ = j
            }
        }
    }
    val firstI = iconI
    val firstJ = iconJ

    var currentDirection = Direction.entries.find { it.icon == map[iconI][iconJ] }!!
    val blockedCell = '#'
    val newCell = '.'
    val visitedCell = 'X'
    val mapLimitI = map.size
    val mapLimitJ = map[0].size
    var sum = 1
    val hitObjects = mutableListOf<Pair<Int, Int>>()
    while (true) {
        val nextCellI = iconI + currentDirection.deltaI
        val nextCellJ = iconJ + currentDirection.deltaJ
        if (nextCellI < 0 || nextCellJ < 0 || nextCellI == mapLimitI || nextCellJ == mapLimitJ) {
            break
        }

        val nextCell = map[nextCellI][nextCellJ]
        if (nextCell == blockedCell) {
            hitObjects.add(Pair(nextCellI, nextCellJ))
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

    return Triple(sum, Pair(firstI, firstJ), hitObjects)
}

fun second(
    map: MutableList<MutableList<Char>>,
    firstPos: Pair<Int, Int>,
    hitObjects: MutableList<Pair<Int, Int>>
): Int {
    val foundPositions = setOf<Pair<Int, Int>>()
    for (point in hitObjects) {
        // start from top left
        // bottom left is missing
        var startPoint = Pair(point.first + 1, point.second)
        var direction = Direction.RIGHT
    }
    return -1
}
