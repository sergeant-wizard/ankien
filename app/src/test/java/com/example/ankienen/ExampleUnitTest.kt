package com.example.ankienen

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


val exampleJson = """[
    {
    "shortdef":[
     "meaning1",
     "meaning2",
     "meaning3"
    ],
}, {
    "shortdef":[
     "meaning4",
    ]
}]""".trimIndent()

class ExampleUnitTest {
    @Test
    fun jsonParse() {
        val words = parseJson(org.json.JSONArray(exampleJson))
        val expected = arrayOf("meaning1", "meaning2", "meaning3", "meaning4")
        assertArrayEquals(expected, words)
    }
}
