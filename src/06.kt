import java.io.File

const val blockedCell = '#'
const val newCell = '.'
const val visitedCell = 'X'

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

data class IconState(var i: Int, var j: Int, var direction: Direction)

fun first(originalMap: MutableList<MutableList<Char>>): Triple<Int, IconState, MutableList<IconState>> {
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
    val firstDirection = Direction.entries.find { it.icon == map[iconI][iconJ] }!!
    var currentDirection = firstDirection
    val mapLimitI = map.size
    val mapLimitJ = map[0].size
    var sum = 1
    val iconSteps = mutableListOf<IconState>()
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
            iconSteps.add(IconState(iconI, iconJ, currentDirection))
        }
    }
    for (line in map) {
        println(line)
    }

    return Triple(sum, IconState(firstI, firstJ, firstDirection), iconSteps)
}

fun second(
    map: MutableList<MutableList<Char>>,
    firstPos: IconState,
    iconSteps: MutableList<IconState>
): Int {
    val mapLimitI = map.size
    val mapLimitJ = map[0].size
    val foundPositions = mutableSetOf<Pair<Int, Int>>()
    for (point in iconSteps) {
        map[point.i][point.j] = blockedCell
        val fastIcon = IconState(firstPos.i, firstPos.j, firstPos.direction)
        val slowIcon = IconState(firstPos.i, firstPos.j, firstPos.direction)
        var isSlowWalkTurn = false
        while (true) {
            val nextCellI = fastIcon.i + fastIcon.direction.deltaI
            val nextCellJ = fastIcon.j + fastIcon.direction.deltaJ
            if (nextCellI < 0 || nextCellJ < 0 || nextCellI == mapLimitI || nextCellJ == mapLimitJ) {
                break
            }


            val nextCell = map[nextCellI][nextCellJ]
            if (nextCell == blockedCell) {
                fastIcon.direction = fastIcon.direction.nextDirection()
            } else {
                fastIcon.i = nextCellI
                fastIcon.j = nextCellJ
            }
            if (isSlowWalkTurn) {
                val slowNextCellI = slowIcon.i + slowIcon.direction.deltaI
                val slowNextCellJ = slowIcon.j + slowIcon.direction.deltaJ
                val slowNextCell = map[slowNextCellI][slowNextCellJ]
                if (slowNextCell == blockedCell) {
                    slowIcon.direction = slowIcon.direction.nextDirection()
                } else {
                    slowIcon.i = slowNextCellI
                    slowIcon.j = slowNextCellJ
                }
                isSlowWalkTurn = false
            } else {
                isSlowWalkTurn = true
            }
            if (fastIcon == slowIcon) {
                println("$fastIcon $slowIcon")
                foundPositions.add(Pair(point.i, point.j))
                break
            }
        }
        map[point.i][point.j] = newCell

    }
    println(foundPositions)
    return foundPositions.size
}

fun walkUntilObstacleOrEdge(
    map: MutableList<MutableList<Char>>,
    initialPos: Pair<Int, Int>,
    direction: Direction
): Pair<Int, Int>? {
    val blockedCell = '#'
    val mapLimitI = map.size
    val mapLimitJ = map[0].size
    var iconI = initialPos.first
    var iconJ = initialPos.second
    while (true) {
        val nextCellI = iconI + direction.deltaI
        val nextCellJ = iconJ + direction.deltaJ
        if (nextCellI < 0 || nextCellJ < 0 || nextCellI == mapLimitI || nextCellJ == mapLimitJ) {
            return null
        }

        val nextCell = map[nextCellI][nextCellJ]
        if (nextCell == blockedCell) {
            return Pair(nextCellI, nextCellJ)
        } else {
            iconI = nextCellI
            iconJ = nextCellJ
        }
    }
}
