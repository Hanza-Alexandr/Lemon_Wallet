package com.example.lemonwallet

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val worker = mockk<MyWorker>()
        val exawasd = 12
        assertEquals(4, 2 + 2)
        every { worker.foo() } returns exawasd

        verify() { worker.foo() }
    }
}