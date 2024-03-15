package pt.isel

import java.io.Reader
import kotlin.reflect.KClass

abstract class AbstractYamlParser<T : Any>(private val type: KClass<T>) : YamlParser<T> {
    /**
     * Used to get a parser for other Type using the same parsing approach.
     */
    abstract fun <T : Any> yamlParser(type: KClass<T>) : AbstractYamlParser<T>
    /**
     * Creates a new instance of T through the first constructor
     * that has all the arguments in the map.
     */
    abstract fun newInstance(args: Map<String, Any>): T


    final override fun parseObject(yaml: Reader): T {
        TODO("Not yet implemented")
    }

    final override fun parseList(yaml: Reader): List<T> {
        TODO("Not yet implemented")
    }

}