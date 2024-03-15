package pt.isel

import org.openjdk.jmh.annotations.*

@BenchmarkMode(Mode.Throughput)
@State(Scope.Benchmark)
open class YamlParserAccountBenchmark  {

    private val parserStudentBaseline = YamlSavingsAccountParser()
    private val parserStudentReflect = YamlParserReflect.yamlParser(SavingsAccount::class)

    @Benchmark
    fun accountBaseline(): SavingsAccount {
        return parserStudentBaseline
            .parseObject(yamlSavingsAccount.reader())
    }

    @Benchmark
    fun accountReflect(): SavingsAccount {
        return parserStudentReflect.parseObject(yamlSavingsAccount.reader())
    }
}

