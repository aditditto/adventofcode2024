import java.io.File

fun main() {
    val rules = mutableListOf<Pair<Int, Int>>()
    val lines = mutableListOf<String>()
    var readingRules = true
    File("resources/05.in").forEachLine { line ->
        if (line.trim().isEmpty()) {
            readingRules = false
        } else if (readingRules) {
            val splitLine = line.split("|")
            rules.add(Pair(splitLine[0].toInt(), splitLine[1].toInt()))
        } else {
            lines.add(line)
        }
    }
}
