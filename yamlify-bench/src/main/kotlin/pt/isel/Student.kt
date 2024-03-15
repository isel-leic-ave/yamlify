package pt.isel

class Student(val name: String, val nr: Int, val from: String) {
    lateinit var address: Address
    lateinit var grades: Sequence<Grade>
    constructor(name: String, nr: Int, from: String, address: Address) : this(name, nr, from) {
        this.address = address
    }
    constructor(name: String, nr: Int, from: String, address: Address, grades: Sequence<Grade>) : this(name, nr, from, address) {
        this.grades = grades
    }
}