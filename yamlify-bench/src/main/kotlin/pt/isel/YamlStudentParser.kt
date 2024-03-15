package pt.isel

import java.io.Reader

class YamlStudentParser : YamlParser<Student> {
    override fun parseObject(yaml: Reader): Student {
        yaml.useLines { yaml ->
            val lines = yaml.filter { line -> line.isNotEmpty() }.iterator()
            val map = mutableMapOf<String, Any>()
            while (lines.hasNext()) {
                val (name: String, value: String) = lines
                    .next()
                    .split(":", limit = 2)
                    .map(String::trim)
                if(value.isNotEmpty()) {
                    map[name] = value
                }
                else { // Here start processing address
                    break
                }
            }
            yamlToMap(lines).let {
                map["address"] = Address(
                    it["street"]!!,
                    it["nr"]!!.toInt(),
                    it["city"]!!,
                )
            }
            map["grades"] = sequence {
                require(lines.next().trim() == "-")
                while (lines.hasNext()) {
                    yield(yamlToMap(lines).let {
                         Grade(
                            it["subject"]!!,
                            it["classification"]!!.toInt(),
                        )
                    })
                }
            }.toList().asSequence()
            return Student(
                map["name"]!! as String,
                (map["nr"]!! as String).toInt(),
                map["from"]!! as String,
                map["address"]!! as Address,
                map["grades"]!! as Sequence<Grade>,
            )
        }
    }
    fun yamlToMap(lines: Iterator<String>) = mutableMapOf<String, String>().also {
        var l = lines.next()
        val indent = nrOfSpaces(l)
        do {
            val (n, v) = l.split(":", limit = 2).map(String::trim)
            it[n] = v
            if(!lines.hasNext()) break
            l = lines.next()
        } while(nrOfSpaces(l) >= indent)
    }

    override fun parseList(yaml: Reader): List<Student> {
        TODO("Not yet implemented")
    }
}

/**
 * Be careful with the performance of this method, namely the Regex!!!!!
 * Require line to not include tabs (only spaces).
 */
private fun nrOfSpaces(line: String): Int {
    // require(!line.contains("\t")) { "Tabs are not allowed" }
    // val matchResult = Regex("""^\s*""").find(line) // !!!!!! OVERHEAD
    // return matchResult?.value?.length ?: 0
    var count = 0
    for (char in line) {
        if (char == ' ') {
            count++
        } else {
            break
        }
    }
    return count
}