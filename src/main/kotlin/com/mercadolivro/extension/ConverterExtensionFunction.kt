package com.mercadolivro.extension

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.controller.response.PurchaseCustomersResponse
import com.mercadolivro.enums.BookSatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email, status = CustomerStatus.ATIVO, password = this.password)
}

fun PutCustomerRequest.toCustomerModel(customerPrevious: CustomerModel): CustomerModel {
    return CustomerModel(
        id = customerPrevious.id,
        name = this.name,
        email = this.email,
        status = customerPrevious.status,
        password = customerPrevious.password
    )
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(name = this.name, price = this.price, status = BookSatus.ATIVO, customer = customer)
}

fun PutBookRequest.toBookModel(bookPrevious: BookModel): BookModel {
    return BookModel(
        id = bookPrevious.id,
        name = this.name ?: bookPrevious.name,
        price = this.price ?: bookPrevious.price,
        status = bookPrevious.status,
        customer = bookPrevious.customer
    )
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer,
        status = this.status
    )
}

fun PurchaseModel.toResponse(): PurchaseCustomersResponse {
    return PurchaseCustomersResponse(
        customer = this.customer,
        books = this.books
    )
}