package pt.isel.test

class Student @JvmOverloads constructor (
    val name: String,
    val nr: Int,
    val from: String,
    val address: Address? = null,
    val grades: List<Grade> = emptyList()
)