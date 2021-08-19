package com.mercadolivro.model

import com.mercadolivro.enums.BookSatus
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
) {
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookSatus? = null
        set(value) {
            if (field == BookSatus.DELETADO || field == BookSatus.CANCELADO) {
                throw Exception("Não é possível alterar um  livro com status $field")
            }
            field = value
        }

    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        customer: CustomerModel? = null,
        status: BookSatus?
    ) : this(id, name, price, customer) {
        this.status = status
    }
}