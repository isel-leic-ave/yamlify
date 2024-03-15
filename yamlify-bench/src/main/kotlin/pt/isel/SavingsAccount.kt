package pt.isel

class SavingsAccount(
    val accountCode: Short,
    val holderName: String,
    val balance: Long,
    val isActive: Boolean,
    val interestRate: Double,
    val transactionLimit: Int,
    val withdrawLimit: Int,
)