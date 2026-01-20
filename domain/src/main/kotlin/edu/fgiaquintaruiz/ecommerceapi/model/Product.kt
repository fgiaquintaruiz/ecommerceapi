package edu.fgiaquintaruiz.ecommerceapi.model

import java.math.BigDecimal

data class Product(
    val id: Long? = null,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int
)