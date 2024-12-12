import java.io.File

fun main() {
    first()
}

fun first() {
    var sum = 0
    File("resources/03.in").forEachLine { line ->
        val matches = Regex("""mul\(([0-9]+),([0-9]+)\)""").findAll(line)
        for (match in matches) {
            val (a, b) = match.destructured
            sum += a.toInt() * b.toInt()
        }
    }
    println(sum)
}
