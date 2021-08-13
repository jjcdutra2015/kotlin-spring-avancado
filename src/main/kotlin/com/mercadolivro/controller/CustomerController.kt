package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.model.CustomerModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController {

    @GetMapping
    fun helloWorld(): CustomerModel {
        return CustomerModel("1", "Julio", "email@email")
    }

    @PostMapping
    fun create(@RequestBody customer: PostCustomerRequest) {
        println(customer)
    }
}