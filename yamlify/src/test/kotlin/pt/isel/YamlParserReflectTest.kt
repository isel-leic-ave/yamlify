package pt.isel

import org.junit.jupiter.api.assertThrows
import pt.isel.test.Student
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class YamlParserReflectTest {

    @Test fun parseStudentWithMissingProperties() {
        val yaml = """
                name: Maria Candida
                nr: 873435
                from: Oleiros"""
        assertThrows<IllegalArgumentException> {
            yamlParserReflect(Student::class).parseObject(yaml.reader())
        }
    }
    @Test fun parseStudent() {
        val yaml = """
                name: Maria Candida
                nr: 873435
                birth: 1888
                from: Oleiros"""
        val st = yamlParserReflect(Student::class).parseObject(yaml.reader())
        assertEquals("Maria Candida", st.name)
        assertEquals(873435, st.nr)
        assertEquals(1888, st.birth)
        assertEquals("Oleiros", st.from)
    }
    @Test fun parseStudentWithAddress() {
        val yaml = """
                name: Maria Candida
                nr: 873435
                address:
                  street: Rua Rosa
                  nr: 78
                  city: Lisbon
                birth: 1888
                from: Oleiros"""
        val st = yamlParserReflect(Student::class).parseObject(yaml.reader())
        assertEquals("Maria Candida", st.name)
        assertEquals(873435, st.nr)
        assertEquals(1888, st.birth)
        assertEquals("Oleiros", st.from)
        assertEquals("Rua Rosa", st.address.street)
        assertEquals(78, st.address.nr)
        assertEquals("Lisbon", st.address.city)
    }

    @Test fun parseListOfStrings() {
        val yaml = """
            - Ola
            - Maria Carmen
            - Lisboa Capital
        """
        val seq = yamlParserReflect(String::class)
            .parseList(yaml.reader())
            .iterator()
        assertEquals("Ola", seq.next())
        assertEquals("Maria Carmen", seq.next())
        assertEquals("Lisboa Capital", seq.next())
        assertFalse { seq.hasNext() }
    }

    @Test fun parseListOfInts() {
        val yaml = """
            - 1
            - 2
            - 3
        """
        val seq = yamlParserReflect(Int::class)
            .parseList(yaml.reader())
            .iterator()
        assertEquals(1, seq.next())
        assertEquals(2, seq.next())
        assertEquals(3, seq.next())
        assertFalse { seq.hasNext() }
    }
    @Test fun parseListOfStudents(){
        val yaml = """
            -
              name: Maria Candida
              nr: 873435
              birth: 1888
              from: Oleiros
            - 
              name: Jose Carioca
              nr: 1214398
              birth: 1911
              from: Tamega
        """
        val seq = yamlParserReflect(Student::class)
            .parseList(yaml.reader())
            .iterator()
        val st1 = seq.next()
        assertEquals("Maria Candida", st1.name)
        assertEquals(873435, st1.nr)
        assertEquals(1888, st1.birth)
        assertEquals("Oleiros", st1.from)
        val st2 = seq.next()
        assertEquals("Jose Carioca", st2.name)
        assertEquals(1214398, st2.nr)
        assertEquals(1911, st2.birth)
        assertEquals("Tamega", st2.from)
        assertFalse { seq.hasNext() }
    }
    @Test fun parseListOfStudentsWithAddresses() {
        val yaml = """
            -
              name: Maria Candida
              nr: 873435
              address:
                street: Rua Rosa
                nr: 78
                city: Lisbon
              birth: 1888
              from: Oleiros
            - 
              name: Jose Carioca
              nr: 1214398
              address:
                street: Rua Azul
                nr: 12
                city: Porto
              birth: 1911
              from: Tamega
        """
        val seq = yamlParserReflect(Student::class)
            .parseList(yaml.reader())
            .iterator()
        val st1 = seq.next()
        assertEquals("Maria Candida", st1.name)
        assertEquals(873435, st1.nr)
        assertEquals(1888, st1.birth)
        assertEquals("Oleiros", st1.from)
        assertEquals("Rua Rosa", st1.address.street)
        assertEquals(78, st1.address.nr)
        assertEquals("Lisbon", st1.address.city)
        val st2 = seq.next()
        assertEquals("Jose Carioca", st2.name)
        assertEquals(1214398, st2.nr)
        assertEquals(1911, st2.birth)
        assertEquals("Tamega", st2.from)
        assertEquals("Rua Azul", st2.address.street)
        assertEquals(12, st2.address.nr)
        assertEquals("Porto", st2.address.city)
        assertFalse { seq.hasNext() }
    }
    @Test fun parseListOfStudentsWithAddressesAndGrades() {
        val yaml = """
            -
              name: Maria Candida
              nr: 873435
              address:
                street: Rua Rosa
                nr: 78
                city: Lisbon
              birth: 1888
              from: Oleiros
              grades:
                - 
                  subject: LAE
                  classification: 18
                -
                  subject: PDM
                  classification: 15
                -
                  subject: PC
                  classification: 19
            - 
              name: Jose Carioca
              nr: 1214398
              address:
                street: Rua Azul
                nr: 12
                city: Porto
              birth: 1911
              from: Tamega
              grades:
                -
                  subject: TDS
                  classification: 20
                - 
                  subject: LAE
                  classification: 18
        """
        val seq = yamlParserReflect(Student::class)
            .parseList(yaml.reader())
            .iterator()
        val st1 = seq.next()
        assertEquals("Maria Candida", st1.name)
        assertEquals(873435, st1.nr)
        assertEquals(1888, st1.birth)
        assertEquals("Oleiros", st1.from)
        assertEquals("Rua Rosa", st1.address.street)
        assertEquals(78, st1.address.nr)
        assertEquals("Lisbon", st1.address.city)
        val grades1 = st1.grades.iterator()
        val g1 = grades1.next()
        assertEquals("LAE", g1.subject)
        assertEquals(18, g1.classification)
        val g2 = grades1.next()
        assertEquals("PDM", g2.subject)
        assertEquals(15, g2.classification)
        val g3 = grades1.next()
        assertEquals("PC", g3.subject)
        assertEquals(19, g3.classification)
        assertFalse { grades1.hasNext() }
        val st2 = seq.next()
        assertEquals("Jose Carioca", st2.name)
        assertEquals(1214398, st2.nr)
        assertEquals(1911, st2.birth)
        assertEquals("Tamega", st2.from)
        assertEquals("Rua Azul", st2.address.street)
        assertEquals(12, st2.address.nr)
        assertEquals("Porto", st2.address.city)
        val grades2 = st2.grades.iterator()
        val g4 = grades2.next()
        assertEquals("TDS", g4.subject)
        assertEquals(20, g4.classification)
        val g5 = grades2.next()
        assertEquals("LAE", g5.subject)
        assertEquals(18, g5.classification)
        assertFalse { grades2.hasNext() }
        assertFalse { seq.hasNext() }
    }
}
