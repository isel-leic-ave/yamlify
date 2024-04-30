package pt.isel.test.specificParsers

import pt.isel.YamlParserCojen
import pt.isel.test.Student

class StudentParserWith3Arguments() : YamlParserCojen<Student>(Student::class, 3) {
    override fun newInstance(args: Map<String, Any>): Student {
        val name = args["name"] as String
        val nr = (args["nr"] as String).toInt()
        val from = args["from"] as String

        return Student(name, nr, from)
    }
}