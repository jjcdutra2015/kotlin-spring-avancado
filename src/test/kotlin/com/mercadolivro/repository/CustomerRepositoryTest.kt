package com.mercadolivro.repository

import com.mercadolivro.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @Test
    fun `should return name containing`() {
        val marcos = customerRepository.save(buildCustomer(name = "Marcos"))
        val matheus = customerRepository.save(buildCustomer(name = "Matheus"))
        customerRepository.save(buildCustomer(name = "Alex"))

        val customers = customerRepository.findByNameContaining("ma")

        assertEquals(listOf(marcos, matheus), customers)
    }

    @Nested
    inner class `exists by email` {
        @Test
        fun `should return true when email exists`() {
            val email = "fakeEmail@email.com"
            customerRepository.save(buildCustomer(email = email))

            val existsByEmail = customerRepository.existsByEmail(email)

            assertTrue(existsByEmail)
        }

        @Test
        fun `should return false when email not exists`() {
            val email = "notExistsEmail@email.com"

            val existsByEmail = customerRepository.existsByEmail(email)

            assertFalse(existsByEmail)
        }
    }

    @Nested
    inner class `find by email` {
        @Test
        fun `should return customer when email exists`() {
            val email = "fakeEmail@email.com"
            val customer = customerRepository.save(buildCustomer(email = email))

            val existsByEmail = customerRepository.findByEmail(email)

            assertNotNull(existsByEmail)
            assertEquals(customer, existsByEmail)
        }

        @Test
        fun `should return false when email not exists`() {
            val email = "notExistsEmail@email.com"

            val existsByEmail = customerRepository.findByEmail(email)

            assertNull(existsByEmail)
        }
    }
}