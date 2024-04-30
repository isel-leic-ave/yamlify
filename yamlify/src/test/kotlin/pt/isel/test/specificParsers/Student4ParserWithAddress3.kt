package pt.isel.test.specificParsers

import org.cojen.maker.ClassMaker
import org.cojen.maker.MethodMaker
import org.cojen.maker.Variable
import pt.isel.AbstractYamlParser
import pt.isel.YamlParserCojen
import pt.isel.test.Address
import pt.isel.test.Student
import java.io.FileOutputStream
import java.io.Reader
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance


open public class Student4ParserWithAddress() : YamlParserCojen<Student>(Student::class, 4) {
    companion object {
        fun yamlParserHardCoded(): AbstractYamlParser<Student> = Student3ParserWithoutAddress()
        fun yamlParserCogen() : AbstractYamlParser<Student>  {
            val studentParserClass = generateParserCogen().finish()
            return studentParserClass.kotlin.createInstance() as AbstractYamlParser<Student>
        }

        fun saveYamlParserCogen()   {
            generateParserCogen().finishTo(FileOutputStream("Student4ParserWithAddressGen.class"))
        }


    }
    override fun <T : Any> yamlParser(type: KClass<T>) = YamlParserCojen.yamlParser(type)
    override fun newInstance(args: Map<String, Any>): Student {
        val name: String = args["name"] as String
        val nr: Int = (args["nr"] as String).toInt()
        val from: String = args["from"] as String
        val addressMap: Map<String, Any> = args["address"] as Map<String, Any>
        //val addressYamlParser: AbstractYamlParser<Address> = YamlParserCojen.yamlParser(Address::class, addressMap.keys.size)
        val addressYamlParser: AbstractYamlParser<Address> = AddressYamlParser()
        val address = addressYamlParser.newInstance(addressMap)

        return Student(name, nr, from, address)

    }

    override fun parseObject(yaml: Reader): Student {
        val mapAddress = mapOf(
            "street" to "Rua Rosa",
            "nr" to "78",
            "city" to "Lisbon")

        val mapStudent = mapOf(
            "name" to "Maria Candida",
            "nr" to "873435",
            "address" to mapAddress,
            "from" to "Oleiros"
        )
        return newInstance(mapStudent)
    }

}


private fun generateParserCogen(): ClassMaker {
    val cm = ClassMaker
        .begin("Student4ParserWithAddress")
        .public_()
        //.extend(Student4ParserWithAddress::class.java)
        .extend(YamlParserCojen::class.java)

    val init: MethodMaker = cm.addConstructor().public_()
    init.invokeSuperConstructor(Student::class, 3)


    val newInstance: MethodMaker = cm.addMethod(Student::class.java, "newInstance", Map::class.java).public_()

    val argsVar: Variable = newInstance.param(0)

    //val name: String = args["name"] as String
    val name: Variable = argsVar.invoke("get", "name").cast(String::class.java)

    // val nr: Int = (args["nr"] as String).toInt()
    val nrStr: Variable = argsVar.invoke("get", "nr").cast(String::class.java)
    val nr: Variable = newInstance.`var`(Int::class.java).invoke("parseInt", nrStr)

    // val from: String = args["from"] as String
    val from: Variable = argsVar.invoke("get", "from").cast(String::class.java)

    //    val addressMap: Map<String, Any> = args["address"] as Map<String, Any>
    val addressMap: Variable = argsVar.invoke("get", "address").cast(Map::class.java)
    //    val addressYamlParser: AbstractYamlParser<Address> = AddressYamlParser()
    val addressYamlParser: Variable = newInstance.new_(AddressYamlParser::class.java)

    //    val address = addressYamlParser.newInstance(addressMap)
    val address: Variable = addressYamlParser.invoke("newInstance", addressMap).cast(Address::class.java)

    // return Student(name, nr, from, address)
    val student: Variable = newInstance.new_(Student::class.java, name, nr, from, address)

    newInstance.return_(student)

    return cm

}
