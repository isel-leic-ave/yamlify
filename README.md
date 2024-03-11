## yamlify - YAML Parser

YAML Parser through Reflection API and bytecode generation for JVM, 
with both eager and lazy sequences processing.

***

Use this project as the template for your implementation of a YAML parser.

Throughout the semester, you will implement various approaches to a basic YAML
parser, adhering to specific aspects of the [YAML version 1.2.2 specification](https://yaml.org/spec/1.2.2).
It's important to note that these implementations will have certain limitations:
* **No support** either block string nor line folding with `|` or `>`.
* **No support** of _flow styles_
* Type-safe ONLY with constructor injection. 
* No support of mutable properties injection.

Namely, examples 2.5 and 2.6 of [YAML 1.2.2](https://yaml.org/spec/1.2.2)
denoting the use of _flow styles_ should **NOT** be supported by your implementation
of the YAML parser.

## Assignment 1 - Types at runtime and Reflection API

### 1.1

Implement the `YamlParserReflect` class provided in this project to ensure it passes the provided unit tests.

### 1.2

Properties of the domain class should be able to have names different from
those used in the YAML representation.
For example, a property in YAML may have the name `city of birth`, while in
Kotlin, it might be named `from`.
To address the mapping between properties with distinct names, implement a
`YamlArg` annotation that can be used on the parameters of a domain class
constructor, indicating the corresponding name in YAML (e.g., `@YamlArg("city
of birth")`).
Modify `YamlParserReflect` to support the specified behavior and validate it
with unit tests.

### 1.3

The `YamlParserReflect` should support extensibility with custom parsers
provided by the domain class.
For instance, when dealing with a YAML mapping like `birth: 2004-05-26`, you
might want to parse the value as an instance of `LocalDate`. 
To achieve this, you can annotate the corresponding constructor argument as
follows:

```kotlin
class Student(..., @YamlConvert(YamlToDate::class) val birth: LocalDate, ...)
```

In this example, `YamlToDate` is a class provided by the same developer that
implements the domain class `Student`.

Adjust your implementation of `YamlParserReflect` to seamlessly integrate any
parser provided by its clients for any data type.
A parser should be a class that offers a function taking a `String` argument and
returning an `Object`.

Validate your implementation with the provided example of the `birth` property
and create another example to showcase your approach with a different property
type.