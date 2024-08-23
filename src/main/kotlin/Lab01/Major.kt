package Lab01

class Major(val name: String, val students: ArrayList<Student> = arrayListOf()) {
    fun addStudent(student: Student) {
        students.add(student)
    }

    fun stats(courseName: String = ""): Triple<Double, Double, Double> {
        if (students.isEmpty()) {
            return Triple(0.0, 0.0, 0.0)
        }

        val averages: List<Double> = if (courseName.isEmpty()) {
            students.map { it.weightedAverage() }
        } else {
            students.map { it.courses.first { it.name == courseName }.grade }
        }

        return Triple(averages.min(), averages.max(), averages.average())
    }
}