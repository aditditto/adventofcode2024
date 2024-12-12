import java.io.File

fun main() {
    first()
}

fun first() {
    var sum = 0
    var isMulOn = true
    File("resources/03.in").forEachLine { line ->
        val matches = Regex("""mul\(([0-9]+),([0-9]+)\)|do\(\)|don't\(\)""").findAll(line)
        for (match in matches) {
            if (match.value == "do()") {
                isMulOn = true
            } else if (match.value == "don't()") {
                isMulOn = false
            } else if (isMulOn) {
                val (a, b) = match.destructured
                sum += a.toInt() * b.toInt()
            }
        }
    }
    println(sum)
}
