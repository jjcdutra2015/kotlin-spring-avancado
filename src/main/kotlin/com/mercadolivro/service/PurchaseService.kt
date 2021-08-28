package com.mercadolivro.service

import com.mercadolivro.enums.BookSatus
import com.mercadolivro.enums.Errors.ML103
import com.mercadolivro.event.PurchaseEvent
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun create(purchaseModel: PurchaseModel) {
        purchaseModel.books.map {
            if (it.status == BookSatus.ATIVO) {
                purchaseRepository.save(purchaseModel)
            } else {
                throw BadRequestException(ML103.message.format(it.status), ML103.code)
            }
        }

        println("Disparando evento de compra")
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
        println("Finalizando evento de compra")
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }
}
