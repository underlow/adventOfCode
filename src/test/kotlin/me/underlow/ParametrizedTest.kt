package me.underlow

import org.junit.jupiter.api.DynamicTest

data class TestData<E, A>(val expected: E, val actual: A, val message: String = "Test: $expected from $actual")

fun <E, A> List<TestData<E, A>>.parametrizedTest(
    block: (TestData<E, A>) -> Unit
): List<DynamicTest> {
    return mapIndexed { idx, testData ->
        DynamicTest.dynamicTest("Test [#$idx]: ${testData.expected} from ${testData.actual}") {
            block(testData)
        }
    }
}
