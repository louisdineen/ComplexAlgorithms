import kotlin.math.max

/**
 * Finds the maximum amount of weighted intervals (jobs) that can be scheduled in a time period
 */
fun main() {
    val dataSet = arrayOf(
            Job("A", 7, 13, 8),
            Job("B", 13, 18, 5),
            Job("C", 1, 6, 8),
            Job("D", 18, 21, 5),
            Job("E", 10, 16, 3),
            Job("F", 15, 19, 7),
            Job("G", 3, 9, 4),
            Job("H", 15, 20, 5)
    )
    getP(dataSet)

    val maxValue = calcOPT(dataSet)
    print(maxValue)
}

data class Job(val name: String, val start: Int, val end: Int, val value: Int) {
    var p: Job? = null

    override fun toString(): String {
        return "Job[$name]($start->$end) [$value] P($p)"
    }
}

fun getP(jobs: Array<Job>) {
    val pComparator = Comparator { p1: Job, p2: Job -> p1.end - p2.end }
    val jobsSorted = jobs.sortedWith(pComparator).reversed()

    jobsSorted.forEach { jobBase ->
        jobsSorted.forEach { jobCompare ->
            if (jobBase != jobCompare && jobBase.start >= jobCompare.end && jobBase.p == null) {
                jobBase.p = jobCompare
            }
        }
    }
}

fun getJobPValue(job: Job?): Int {
    if (job == null) {
        return 0
    }
    var value = job.value
    value += getJobPValue(job.p)

    return value
}

fun calcOPT(jobs: Array<Job>): Int {
    val pComparator = Comparator { p1: Job, p2: Job -> p1.end - p2.end }
    val jobsSorted = jobs.sortedWith(pComparator)

    val M = arrayListOf(0)
    for (i in 1 until jobsSorted.size) {
        M.add(max(M[i - 1], getJobPValue(jobsSorted[i])))
    }

    return M.last();
}