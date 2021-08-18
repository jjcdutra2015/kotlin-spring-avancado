package com.mercadolivro.repository

import com.mercadolivro.enums.BookSatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<BookModel, Int> {
    fun findByStatus(status: BookSatus): List<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
}