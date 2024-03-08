package pt.isel

import java.io.Reader

interface YamlParser<T> {
    fun parseObject(yaml: Reader): T
    fun parseList(yaml: Reader): List<T>
}