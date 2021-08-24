package com.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest(

    @field:NotEmpty(message = "Nome deve ser preenchido")
    var name: String,

    @field:Email(message = "Email deve ter formato válido")
    var email: String
)