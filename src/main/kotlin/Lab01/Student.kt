package Lab01

class Student(name: String, age: Int, val courses: ArrayList<CourseRecord> = arrayListOf()) : Human(name, age) {
    fun addCourse(course: CourseRecord) {
        courses.add(course)
    }

    fun weightedAverage(year: Int ?= null): Double {
        var filteredCourses: List<CourseRecord> = courses

        if (year != null) {
            filteredCourses = filteredCourses.filter { it.yearCompleted == year }
        }

        if (filteredCourses.isEmpty()) {
            return 0.0
        }

        val totalWeight: Double = filteredCourses.fold(0.0) {total, course -> total + course.grade * course.credits}
        val totalCredits: Double = filteredCourses.fold(0.0) {total, course -> total + course.credits}

        return totalWeight / totalCredits
    }

    fun minMaxGrades(): Pair<Double, Double> {
        if (courses.isEmpty()) {
            return Pair(0.0, 0.0)
        }

        val minGrade = courses.minBy { it.grade }.grade
        val maxGrade = courses.maxBy { it.grade }.grade

        return Pair(minGrade, maxGrade)
    }
}