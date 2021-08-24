package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostBookRequest(

    @field:NotEmpty(message = "Nome deve ser informado")
    val name: String,

    @field:NotNull(message = "Price deve ser informado")
    val price: BigDecimal,

    @field:NotNull
    @JsonAlias("customer_id")
    val customerId: Int
)