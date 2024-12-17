import java.io.File

fun main() {
    val matrix = mutableListOf<MutableList<Char>>()
    File("resources/04.in").forEachLine { line ->
        matrix.add(mutableListOf())
        for (char in line) {
            matrix.last().add(char)
        }
    }

    println(solve(matrix))
}

fun solve(matrix: MutableList<MutableList<Char>>): Pair<Int, Int> {
    val rowsSize = matrix.size
    val colsSize = matrix[0].size
    var first = 0
    var second = 0
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            first += checkMatches(matrix, i, j, rowsSize, colsSize)
            if (isXMas(matrix, i, j, rowsSize, colsSize)) {
                second++
            }
        }
    }

    return Pair(first, second)
}

fun checkMatches(matrix: MutableList<MutableList<Char>>, startI: Int, startJ: Int, rowsSize: Int, colsSize: Int): Int {
    val words = listOf("XMAS", "SAMX")
    var count = 0
    for (word in words) {
        // horizontal
        for (charIdx in word.indices) {
            if (startJ + charIdx >= colsSize) {
                break
            }
            if (matrix[startI][startJ + charIdx] != word[charIdx]) {
                break
            }
            if (charIdx == word.length - 1) {
                count++
            }
        }

        // vertical
        for (charIdx in word.indices) {
            if (startI + charIdx >= rowsSize) {
                break
            }
            if (matrix[startI + charIdx][startJ] != word[charIdx]) {
                break
            }
            if (charIdx == word.length - 1) {
                count++
            }
        }

        // diagonal down right
        for (charIdx in word.indices) {
            if (startI + charIdx >= rowsSize || startJ + charIdx >= colsSize) {
                break
            }
            if (matrix[startI + charIdx][startJ + charIdx] != word[charIdx]) {
                break
            }
            if (charIdx == word.length - 1) {
                count++
            }
        }

        // diagonal down left
        for (charIdx in word.indices) {
            if (startI + charIdx >= rowsSize || startJ - charIdx < 0) {
                break
            }
            if (matrix[startI + charIdx][startJ - charIdx] != word[charIdx]) {
                break
            }
            if (charIdx == word.length - 1) {
                count++
            }
        }
    }
    return count
}

fun isXMas(matrix: MutableList<MutableList<Char>>, startI: Int, startJ: Int, rowsSize: Int, colsSize: Int): Boolean {
    var hasFirstMas = false
    if (startI - 1 < 0 || startI + 1 >= rowsSize || startJ - 1 < 0 || startJ + 1 >= colsSize) {
        return false
    }
    if ((matrix[startI - 1][startJ - 1] == 'M' && matrix[startI][startJ] == 'A' && matrix[startI + 1][startJ + 1] == 'S')
        || (matrix[startI - 1][startJ - 1] == 'S' && matrix[startI][startJ] == 'A' && matrix[startI + 1][startJ + 1] == 'M')
    ) {
        hasFirstMas = true
    }
    if (hasFirstMas) {
        if ((matrix[startI - 1][startJ + 1] == 'M' && matrix[startI][startJ] == 'A' && matrix[startI + 1][startJ - 1] == 'S')
            || (matrix[startI - 1][startJ + 1] == 'S' && matrix[startI][startJ] == 'A' && matrix[startI + 1][startJ - 1] == 'M')
        ) {
            return true
        }
    }
    return false
}
