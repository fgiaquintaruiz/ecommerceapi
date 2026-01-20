package edu.fgiaquintaruiz.ecommerceapi.model

import java.math.BigDecimal

/**
 * Represents a line item within an Order.
 * This is a Value Object in DDD terms.
 */
data class OrderItem(
    val productId: Long,
    val quantity: Int,
    val priceAtPurchase: BigDecimal
)