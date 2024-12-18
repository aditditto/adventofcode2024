import java.io.File

fun main() {
    val rules = mutableListOf<Pair<Int, Int>>()
    val lines = mutableListOf<MutableList<Int>>()
    var readingRules = true
    File("resources/05.in").forEachLine { line ->
        if (line.trim().isEmpty()) {
            readingRules = false
        } else if (readingRules) {
            val splitLine = line.split("|")
            rules.add(Pair(splitLine[0].toInt(), splitLine[1].toInt()))
        } else {
            lines.add(line.split(",").map { it.toInt() }.toMutableList())
        }
    }

    val (firstAnswer, fails) = first(lines, rules)
    println(firstAnswer)
    println(second(fails, rules))
}

fun first(
    lines: MutableList<MutableList<Int>>,
    rules: MutableList<Pair<Int, Int>>
): Pair<Int, MutableList<MutableList<Int>>> {
    var sum = 0
    val fails = mutableListOf<MutableList<Int>>()
    lines@ for (line in lines) {
        val previousNums = mutableSetOf<Int>()
        val ruleIsActive = MutableList(rules.size) { true }
        for (i in line.indices) {
            val num = line[i]
            for (j in rules.indices) {
                if (rules[j].first == num && ruleIsActive[j] && previousNums.contains(rules[j].second)) {
                    fails.add(line)
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
    return Pair(sum, fails)
}

fun second(fails: MutableList<MutableList<Int>>, rules: MutableList<Pair<Int, Int>>): Int {
    var sum = 0
    for (line in fails) {
        var isCorrect = false
        fix@ while (!isCorrect) {
            val previousNums = mutableSetOf<Pair<Int, Int>>()
            val ruleIsActive = MutableList(rules.size) { true }
            for (i in line.indices) {
                val num = line[i]
                // check for broken rules
                for (j in rules.indices) {
                    if (rules[j].first == num && ruleIsActive[j]) {
                        val toSwitch = previousNums.find { it.first == rules[j].second }
                        if (toSwitch != null) {
                            line[i] = toSwitch.first
                            line[toSwitch.second] = num
                            continue@fix
                        }
                    }
                }
                previousNums.add(Pair(num, i))
                for (j in rules.indices) {
                    if (rules[j].first == num) {
                        ruleIsActive[j] = false
                    }
                }
                if (i == line.size - 1) {
                    sum += line[line.size / 2]
                    isCorrect = true
                }
            }

        }
    }
    return sum
}
