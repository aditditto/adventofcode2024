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

fun first(map: MutableList<MutableList<Char>>): Int {
    val charIcons = listOf('^', '>', '<', 'v')
    var charJ: Int
    var charI: Int
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (charIcons.contains(map[i][j])) {
                charI = i
                charJ = j
            }
        }
    }


    return -0
}