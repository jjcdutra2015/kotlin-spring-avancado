package com.mercadolivro.service

import com.mercadolivro.event.PurchaseEvent
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
        purchaseRepository.save(purchaseModel)

        println("Disparando evento de compra")
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
        println("Finalizando evento de compra")
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }
}
