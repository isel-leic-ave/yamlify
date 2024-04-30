package pt.isel.test.specificParsers

import pt.isel.AbstractYamlParser
import pt.isel.YamlParserCojen
import pt.isel.test.Student
import kotlin.reflect.KClass

class Student3ParserWithoutAddress() : AbstractYamlParser<Student>(Student::class) {

    override fun <T : Any> yamlParser(type: KClass<T>) = YamlParserCojen.yamlParser(type)
    override fun newInstance(args: Map<String, Any>): Student {
        val name: String = args["name"] as String
        val nr: Int = (args["nr"] as String).toInt()
        val from: String = args["from"] as String
        return Student(name, nr, from)

    }

}