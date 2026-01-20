package edu.fgiaquintaruiz.ecommerceapi.port.output

import edu.fgiaquintaruiz.ecommerceapi.model.Order

fun interface OrderRepositoryPort {
    fun save(order: Order): Order
}