import java.io.File

fun main() {
    val rules = mutableListOf<Pair<Int, Int>>()
    val lines = mutableListOf<List<Int>>()
    var readingRules = true
    File("resources/05.in").forEachLine { line ->
        if (line.trim().isEmpty()) {
            readingRules = false
        } else if (readingRules) {
            val splitLine = line.split("|")
            rules.add(Pair(splitLine[0].toInt(), splitLine[1].toInt()))
        } else {
            lines.add(line.split(",").map { it.toInt() })
        }
    }

    println(first(lines, rules))
}

fun first(lines: MutableList<List<Int>>, rules: MutableList<Pair<Int, Int>>): Int {
    var sum = 0
    lines@ for (line in lines) {
        val previousNums = mutableSetOf<Int>()
        val ruleIsActive = MutableList(rules.size) { true }
        for (i in line.indices) {
            val num = line[i]
            for (j in rules.indices) {
                if (rules[j].first == num && ruleIsActive[j] && previousNums.contains(rules[j].second)) {
                    continue@lines
                }
            }
            previousNums.add(num)
            for (j in rules.indices) {
                if (rules[j].first == num) {
                    ruleIsActive[j] = false
                }
            }
            if (i == line.size - 1) {
                sum += line[line.size / 2]
            }
        }
    }
    return sum
}
