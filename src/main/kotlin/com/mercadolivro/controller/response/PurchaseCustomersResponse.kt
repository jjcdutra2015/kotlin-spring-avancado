package com.mercadolivro.controller.response

import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel

data class PurchaseCustomersResponse(
    val customer: CustomerModel,
    val books: List<BookModel>
)
