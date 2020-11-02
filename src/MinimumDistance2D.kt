import java.util.*
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Finds the minimum distance between any two points in a data set of nodes in a 2d field
 */
fun main() {
    val dataSet = minDist(
            listOf(
                    Point(-8.0, 1.0, 0),
                    Point(4.0, 3.0, 1),
                    Point(6.0, 5.0, 2),
                    Point(5.0, 7.0, 3),
                    Point(7.0, 0.0, 4),
                    Point(-4.0, -3.0, 5),
                    Point(2.0, -6.0, 6),
                    Point(-1.0, 6.0, 7)
            )
    )
    println(dataSet)
}

data class Point(val x: Double, val y: Double, val index: Int) {
    override fun toString(): String {
        return "Point[$index]{$x,$y}"
    }
}

fun minDist(pointsUnsorted: List<Point>): Double {
    val xComparator = Comparator { p1: Point, p2: Point -> p1.x.toInt() - p2.x.toInt() }
    val points = pointsUnsorted.sortedWith(xComparator)

    if (points.size == 1) {
        return 999999999.9
    } else if (points.size == 2) {
        return euclideanDistance(points[0], points[1])
    }
    val left = points.subList(0, points.size / 2)
    val right = points.subList(points.size / 2, points.size)

    val L = (left.last().x + right.first().x) / 2

    var delta = min(minDist(left), minDist(right))
    val unsortedList = arrayListOf<Point>()

    points.forEach {
        if ((if (it.x > L) it.x - L else L - it.x) < delta) {
            unsortedList.add(it)
        }
    }

    val yComparator = Comparator { p1: Point, p2: Point -> p1.y.toInt() - p2.y.toInt() }
    val ySortedList = unsortedList.sortedWith(yComparator)

    var index = 0
    ySortedList.forEach { point1 ->
        loop@ for (i in index..ySortedList.lastIndex) {
            if (index == i) {
                continue@loop
            }
            val point2 = ySortedList[i]
            val euDist = euclideanDistance(point1, point2)
            if (euDist < delta) {
                delta = euDist
            }
        }
        index++
    }

    return delta

}

fun euclideanDistance(p1: Point, p2: Point): Double {
    return sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y))
}