import java.io.File

fun main() {
    var validLines = 0
    File("resources/02.in").forEachLine { line ->
        val nums = line.split("\\s+".toRegex()).map { el -> el.toInt() }
        val isIncreasing = nums[1] > nums[0]
        nums.forEachIndexed { i, it ->
            if (i == 0) {
                return@forEachIndexed
            }
            val difference = it - nums[i - 1]
            if (
                (isIncreasing && difference >= 1 && difference <= 3) ||
                (!isIncreasing && difference <= -1 && difference >= -3)
            ) {
                return@forEachIndexed
            } else {
                return@forEachLine
            }
        }
        validLines++
    }

    println(validLines)
    println(secondQuestion())
    println(canBeIncreasing(intArrayOf(100, 21, 100)))
}

fun secondQuestion(): Int {
    var validLines = 0;
    File("resources/02.in").forEachLine { line ->
        val nums = line.split("\\s+".toRegex()).map { el -> el.toInt() }

        skips@ for (i in nums.indices) {
            var frontIdx = 1
            var backIdx = 0
            var firstRun = true
            var isIncreasing = true
            while (frontIdx < nums.size) {
                if (frontIdx == i) {
                    frontIdx++
                    if (frontIdx == nums.size) {
                        validLines++
                        return@forEachLine
                    }
                }
                if (backIdx == i) {
                    backIdx++
                    if (i == 0) {
                        frontIdx++
                    }
                }
                val difference = nums[frontIdx] - nums[backIdx]
                if (firstRun) {
                    isIncreasing = difference > 0
                    firstRun = false
                }
                if (
                    (isIncreasing && difference >= 1 && difference <= 3) ||
                    (!isIncreasing && difference <= -1 && difference >= -3)
                ) {
                    if (frontIdx == nums.size - 1) {
                        validLines++
                        return@forEachLine
                    } else {
                        backIdx++
                        frontIdx++
                    }
                } else {
                    continue@skips
                }
            }
        }
    }
    return validLines
}

fun canBeIncreasing(nums: IntArray): Boolean {
    skips@ for (i in nums.indices) {
        var frontIdx = 1
        var backIdx = 0
        while (frontIdx < nums.size) {
            if (frontIdx == i) {
                frontIdx++
            }
            if (backIdx == i) {
                backIdx++
            }
            if (backIdx == frontIdx) {
                frontIdx++
            }
            if (frontIdx == nums.size) {
                return true
            }
            val difference = nums[frontIdx] - nums[backIdx]
            if (difference > 0) {
                if (frontIdx == nums.size - 1) {
                    return true
                } else {
                    backIdx++
                    frontIdx++
                }
            } else {
                continue@skips
            }
        }
    }
    return false
}
