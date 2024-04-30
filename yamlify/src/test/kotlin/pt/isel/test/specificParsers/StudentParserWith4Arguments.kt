package pt.isel.test.specificParsers

import pt.isel.YamlParserCojen
import pt.isel.test.Address
import pt.isel.test.Student

class StudentParserWith4Arguments() : YamlParserCojen<Student>(Student::class, 4) {
    override fun newInstance(args: Map<String, Any>): Student {
        val name = args["name"] as String
        val nr = (args["nr"] as String).toInt()
        val from = args["from"] as String

        val addressMap = args["address"] as Map<String, Any>
        //val yamlParserAddress = YamlParserCojen.yamlParser(Address::class, addressMap.size)
        val yamlParserAddress = AddressYamlParser()
        var address: Address = yamlParserAddress.newInstance(addressMap)

        return Student(name, nr, from, address)
    }
}