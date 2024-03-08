package pt.isel

import java.io.Reader
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

private val yamlParsers: MutableMap<KClass<*>, YamlParserReflect<*>> = mutableMapOf()
/**
 * Creates a YamlParser for the given type using reflection if it does not already exist.
 * Keep it in an internal cache.
 */
fun <T : Any> yamlParserReflect(type: KClass<T>) : YamlParserReflect<T> {
    return yamlParsers.getOrPut(type) { YamlParserReflect(type) } as YamlParserReflect<T>
}
/**
 * A YamlParser that uses reflection to parse objects.
 */
class YamlParserReflect<T : Any>(private val type: KClass<T>) : YamlParser<T> {
    override fun parseObject(yaml: Reader) : T {
        TODO()
    }
    override fun parseList(yaml: Reader): List<T> {
        TODO()
    }
}
