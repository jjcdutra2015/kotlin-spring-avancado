package com.mercadolivro.service

import com.mercadolivro.enums.BookSatus
import com.mercadolivro.event.PurchaseEvent
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.helper.buildBook
import com.mercadolivro.helper.buildPurchase
import com.mercadolivro.repository.PurchaseRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockKExtension::class)
class PurchaseServiceTest {

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    val purchaseSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`() {
        val purchase = buildPurchase(books = mutableListOf(buildBook(status = BookSatus.ATIVO)))

        every { purchaseRepository.save(purchase) } returns purchase
        every { applicationEventPublisher.publishEvent(any()) } just runs

        purchaseService.create(purchase)

        verify(exactly = 1) { purchaseRepository.save(purchase) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(capture(purchaseSlot)) }

        assertEquals(purchase, purchaseSlot.captured.purchaseModel)
    }

    @Test
    fun `should throw when create purchase with book status is not active`() {
        val purchase = buildPurchase(books = mutableListOf(buildBook(status = BookSatus.DELETADO)))

        val error = assertThrows<BadRequestException> {
            purchaseService.create(purchase)
        }

        assertEquals("Unauthorized sale to book with status [${BookSatus.DELETADO}]", error.message)
        assertEquals("ML-103", error.errorCode)
        verify(exactly = 0) { purchaseRepository.save(purchase) }
    }

    @Test
    fun `should update purchase`() {
        val purchase = buildPurchase()
        val update = purchase.copy(nfe = "sds46fs4s6df")

        every { purchaseRepository.save(update) } returns purchase

        purchaseService.update(update)

        verify(exactly = 1) { purchaseRepository.save(update) }
    }
}