package leetcode

fun canCompleteCircuit2(gas: IntArray, cost: IntArray): Int {
    var start = -1
    for (i in gas.indices) {
        var currentGas = 0
        var currentCost = 0
        var j = i
        var stationCount = 0
        while (stationCount < gas.size) {
            currentGas = currentGas + gas[j] - currentCost
            if (currentGas < cost[j]) {
                break
            }
            currentCost = cost[j]
            j = (j + 1) % gas.size
            stationCount++
        }
        if (stationCount == gas.size) {
            start = i
        }
    }
    return start
}


fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
    var totalGain = 0
    var currentGain = 0
    var start = 0
    for (i in gas.indices) {
        totalGain += gas[i] - cost[i]
        currentGain += gas[i] - cost[i]
        if (currentGain < 0) {
            currentGain = 0
            start = i + 1
        }
    }
    return if (totalGain >= 0) start
    else -1
}

fun main() {
    println(canCompleteCircuit(intArrayOf(1, 2, 3, 4, 5), intArrayOf(3, 4, 5, 1, 2)))
    println(canCompleteCircuit(intArrayOf(2, 3, 4), intArrayOf(3, 4, 3)))
}