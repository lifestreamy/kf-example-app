package com.example

import org.junit.Test

class ParserUnitTest {
    @Test
    fun testHexToAscii() {
        val ascii = Converter.hexToAscii("0B")
        val s = "0B".toInt(16).toChar()
        print(s)
//        assertEquals("12", ascii)
    }
}
