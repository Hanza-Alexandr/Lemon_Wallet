package com.example.domain.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ToFormatAmountTest {

    // inp 10000 out 100
    // inp 0 out 0
    // inp 112 out 1.12
    // inp 10 out 0.1
    // inp 1 out 0.01
    @Test
    fun `toFormatAmount inp 10000 out 100`() {
        val amount: Long = 10000

        // Act
        val result = amount.toFormatAmount()

        // Assert
        assertEquals("100", result)
    }

    @Test
    fun `toFormatAmount inp 0 out 0`(){
        val amount: Long = 0

        // Act
        val result = amount.toFormatAmount()

        // Assert
        assertEquals("0", result)
    }

    @Test
    fun `toFormatAmount inp 123 out 1,23`(){
        val amount: Long = 123

        // Act
        val result = amount.toFormatAmount()

        // Assert
        assertEquals("1,23", result)
    }

    @Test
    fun `toFormatAmount inp 10 out 0,1`(){
        val amount: Long = 10

        // Act
        val result = amount.toFormatAmount()

        // Assert
        assertEquals("0,1", result)
    }

    @Test
    fun `toFormatAmount inp 1 out 0,01`(){
        val amount: Long = 1

        // Act
        val result = amount.toFormatAmount()

        // Assert
        assertEquals("0,01", result)
    }
}