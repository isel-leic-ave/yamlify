package pt.isel.test

class Student(val name: String, val nr: Int, val birth: Int, val from: String) {
    lateinit var address: Address
    lateinit var grades: List<Grade>
    constructor(name: String, nr: Int, birth: Int, from: String, address: Address) : this(name, nr, birth, from) {
        this.address = address
    }
    constructor(name: String, nr: Int, birth: Int, from: String, address: Address, grades: List<Grade>) : this(name, nr, birth, from, address) {
        this.grades = grades
    }
}