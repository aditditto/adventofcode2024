import java.io.File
import kotlin.math.abs

fun main() {
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    File("resources/01_1.in").forEachLine {
        val splitIt = it.split("\\s+".toRegex())
        list1.add(splitIt[0].toInt())
        list2.add(splitIt[1].toInt())
    }
    list1.sort()
    list2.sort()

    var sum = 0
    for ((i, el1) in list1.withIndex()) {
        sum += abs(el1 - list2[i])
    }

    println(sum)

    val freqMap = mutableMapOf<Int, Int>()
    for (el in list2) {
        freqMap[el] = (freqMap[el] ?: 0) + 1;
    }
    var simScore = 0;
    for (el in list1) {
        simScore += (freqMap[el] ?: 0) * el
    }

    println(simScore)
}
