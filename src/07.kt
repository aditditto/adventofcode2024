import java.io.File

fun main() {
    val lines = mutableMapOf<Long, List<Long>>()
    File("resources/07.in").forEachLine { line ->
        val splitLine = line.split(": ")
        lines[splitLine[0].toLong()] = splitLine[1].split(" ").map { it.toLong() }
    }
    var sum = 0L
    lines.forEach { line ->
        if (isPossiblyTrue(line.key, line.value)) {
            sum += line.key
        }
    }
    println(sum)
}

fun isPossiblyTrue(result: Long, paramValues: List<Long>): Boolean {
    if (paramValues.isEmpty()) {
        return result == 0L
    }
    if (paramValues.size == 1) {
        return result == paramValues[0]
    }
    return isPossiblyTrueRecurse(result, paramValues, paramValues[0], 1, paramValues.size - 1)
}

fun isPossiblyTrueRecurse(
    targetResult: Long,
    paramValues: List<Long>,
    currentTotal: Long,
    currentIdx: Int,
    lastIndex: Int
): Boolean {
    val currentValue = paramValues[currentIdx]

    if (currentTotal > targetResult) {
        return false
    }
    if (currentIdx == lastIndex) {
        return when (targetResult) {
            currentTotal + currentValue -> {
                true
            }

            currentTotal * currentValue -> {
                true
            }

            (currentTotal.toString() + currentValue.toString()).toLong() -> {
                true
            }

            else -> {
                false
            }
        }
    }

    return if (isPossiblyTrueRecurse(
            targetResult,
            paramValues,
            currentTotal + currentValue,
            currentIdx + 1,
            lastIndex
        )
    ) {
        true
    } else if (isPossiblyTrueRecurse(
            targetResult,
            paramValues,
            currentTotal * currentValue,
            currentIdx + 1,
            lastIndex
        )
    ) {
        true
    } else {
        isPossiblyTrueRecurse(
            targetResult,
            paramValues,
            (currentTotal.toString() + currentValue.toString()).toLong(),
            currentIdx + 1,
            lastIndex
        )
    }
}
