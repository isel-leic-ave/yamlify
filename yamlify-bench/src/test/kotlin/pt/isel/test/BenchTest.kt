package pt.isel.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pt.isel.*
import kotlin.test.assertFalse

class BenchTest {
    @Test
    fun checkYamlAccountBaseline() {
        val acc = YamlSavingsAccountParser()
            .parseObject(yamlSavingsAccount.reader())
        assertAccount(acc)
    }
    @Test
    fun checkYamlAccountReflect() {
        val acc = YamlParserReflect.yamlParser(SavingsAccount::class)
            .parseObject(yamlSavingsAccount.reader())
        assertAccount(acc)
    }
    private fun assertAccount(acc: SavingsAccount) {
        assertEquals(123.toShort(), acc.accountCode)
        assertEquals("John Doe", acc.holderName)
        assertEquals(50000L, acc.balance)
        assertEquals(true, acc.isActive)
        assertEquals(0.05, acc.interestRate)
        assertEquals(10, acc.transactionLimit)
        assertEquals(1000, acc.withdrawLimit)
    }
    @Test
    fun checkYamlStudentBaseline() {
        val std = YamlStudentParser().parseObject(yamlStudent.reader())
        assertStudent(std)
    }
    @Test
    fun checkYamlStudentReflect() {
        val std = YamlParserReflect
            .yamlParser(Student::class)
            .parseObject(yamlStudent.reader())
        assertStudent(std)
    }
    private fun assertStudent(std: Student) {
        assertEquals("Maria Candida", std.name)
        assertEquals(873435, std.nr)
        assertEquals("Oleiros", std.from)
        assertEquals("Rua Rosa", std.address?.street)
        assertEquals(78, std.address?.nr)
        assertEquals("Lisbon", std.address?.city)
        val grades1 = std.grades.iterator()
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

    }

}