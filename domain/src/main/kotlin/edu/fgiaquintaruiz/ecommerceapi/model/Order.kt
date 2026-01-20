package edu.fgiaquintaruiz.ecommerceapi.model

import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Root Aggregate of the Order domain.
 */
data class Order(
    val id: Long? = null,
    val userId: Long,
    val items: List<OrderItem>,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * Business logic to calculate the total price of the order using Streams.
     * This addresses the feedback regarding Java 8+ features.
     */
    fun calculateTotal(): BigDecimal {
        return items.stream()
            .map { it.priceAtPurchase.multiply(BigDecimal(it.quantity)) }
            .reduce(BigDecimal.ZERO, BigDecimal::add)
    }
}