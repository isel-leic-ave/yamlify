package pt.isel

import java.io.Reader

class YamlSavingsAccountParser : YamlParser<SavingsAccount> {
    override fun parseObject(yaml: Reader): SavingsAccount {
        yaml.useLines {
            val map: Map<String, String> = it
                .filter { line -> line.isNotEmpty() }
                .associate { line ->
                    val (name, value) = line.split(":", limit = 2).map(String::trim)
                    name to value
                }
            return SavingsAccount(
                map["accountCode"]!!.toShort(),
                map["holderName"]!!,
                map["balance"]!!.toLong(),
                map["isActive"]!!.toBoolean(),
                map["interestRate"]!!.toDouble(),
                map["transactionLimit"]!!.toInt(),
                map["withdrawLimit"]!!.toInt()
            )
        }
    }

    override fun parseList(yaml: Reader): List<SavingsAccount> {
        TODO("Not yet implemented")
    }
}