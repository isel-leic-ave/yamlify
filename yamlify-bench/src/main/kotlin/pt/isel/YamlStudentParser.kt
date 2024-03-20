package pt.isel

import java.io.Reader

/**
 * This is an ad-hoc YAML parser for a Student with the following format:
 *   name: Maria Candida
 *   nr: 873435
 *   from: Oleiros
 *   address:
 *     street: Rua Rosa
 *     nr: 78
 *     city: Lisbon
 *   grades:
 *     -
 *       subject: LAE
 *       classification: 18
 *     -
 *       subject: PDM
 *       classification: 15
 */
class YamlStudentParser : YamlParser<Student> {
    override fun parseObject(yaml: Reader): Student {
        yaml.useLines { yaml ->
            val lines = yaml
                .filter { line -> line.isNotEmpty() }
                .iterator()
            /*
             * Parsing first mappings into Map<String, Any>, namely, e.g.:
             *   name: Maria Candida
             *   nr: 873435
             *   from: Oleiros
             */
            val map = mutableMapOf<String, Any>().apply { yamlToMap(lines) }
            /*
             * Parsing a Student and Grades and fill the map.
             */
            map["address"] = parseYamlToStudent(lines)
            map["grades"] = parseYamlToGrades(lines)
            /*
             * Instantiate a Student
             */
            return Student(
                map["name"]!! as String,
                (map["nr"]!! as String).toInt(),
                map["from"]!! as String,
                map["address"]!! as Address,
                map["grades"]!! as List<Grade>,
            )
        }
    }
    /**
     * Parsing mappings of an address into a Map<String, Any> and then instantiates an Address.
     * The mappings are for example:
     *     street: Rua Rosa
     *     nr: 78
     *     city: Lisbon
     */
    private fun parseYamlToStudent(lines: Iterator<String>) : Address {
        return mutableMapOf<String, Any>().run {
            yamlToMap(lines)
            Address(
                this["street"]!! as String,
                (this["nr"]!! as String).toInt(),
                this["city"]!! as String,
            )
        }
    }
    /**
     * Parsing a sequence of grades into a List<Grade>.
     * The Grades are in YAML like:
     *     -
     *       subject: LAE
     *       classification: 18
     *     -
     *       subject: PDM
     *       classification: 15
     */
    private fun parseYamlToGrades(lines: Iterator<String>) = mutableListOf<Grade>().apply {
        require(lines.next().trim() == "-")
        while (lines.hasNext()) {
            add(mutableMapOf<String, Any>().run {
                yamlToMap(lines)
                Grade(
                    this["subject"]!! as String,
                    (this["classification"]!! as String).toInt(),
                )
            })
        }
    }

    /**
     * Auxiliary function to parse YAML mappings (i.e. key: value) from an Iterator<String>
     * into a MutableMap<String, Any>, which is the receiver.
     * It finishes when a value is black, or iterator has finished, or indentation has changed.
     */
    private fun MutableMap<String, Any>.yamlToMap(lines: Iterator<String>) {
        var l = lines.next()
        val indent = nrOfSpaces(l)
        do {
            val (n, v) = l.split(":", limit = 2).map(String::trim)
            if(v.isBlank()) break
            this[n] = v
            if(!lines.hasNext()) break
            l = lines.next()
        } while(nrOfSpaces(l) == indent)
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
    require(!line.contains("\t")) { "Tabs are not allowed" }
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