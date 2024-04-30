package pt.isel.test.specificParsers

import pt.isel.AbstractYamlParser
import pt.isel.YamlParserCojen
import pt.isel.test.Address
import pt.isel.test.Student
import kotlin.reflect.KClass

class AddressYamlParser() : YamlParserCojen<Address>(Address::class, 3) {

    override fun <T : Any> yamlParser(type: KClass<T>) = YamlParserCojen.yamlParser(type)
    override fun newInstance(args: Map<String, Any>): Address {
        val street: String = args["street"] as String
        val nr: Int = (args["nr"] as String).toInt()
        val city: String = args["city"] as String
        return Address(street, nr, city)

    }

}