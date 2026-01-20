package edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc.entity

import edu.fgiaquintaruiz.ecommerceapi.model.Order
import edu.fgiaquintaruiz.ecommerceapi.model.OrderItem
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("orders")
data class OrderEntity(
    @Id
    val id: Long? = null,
    val userId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),

    // English comments: MappedCollection manages the one-to-many relationship in JDBC
    // idColumn is the foreign key in order_items table
    @MappedCollection(idColumn = "order_id")
    val items: Set<OrderItemEntity>
) {
    // English comments: Maps JDBC Entity back to Domain Model
    fun toDomain(): Order = Order(
        id = this.id,
        userId = this.userId,
        items = this.items.map { it.toDomain() }
    )

    companion object {
        // English comments: Maps Domain Model to JDBC Entity
        fun fromDomain(order: Order): OrderEntity = OrderEntity(
            id = order.id,
            userId = order.userId,
            items = order.items.map { OrderItemEntity.fromDomain(it) }.toSet()
        )
    }
}

@Table("order_items")
data class OrderItemEntity(
    @Id
    val id: Long? = null,
    val productId: Long,
    val quantity: Int,
    val priceAtPurchase: BigDecimal
) {
    fun toDomain(): OrderItem = OrderItem(
        productId = this.productId,
        quantity = this.quantity,
        priceAtPurchase = this.priceAtPurchase
    )

    companion object {
        fun fromDomain(item: OrderItem): OrderItemEntity = OrderItemEntity(
            productId = item.productId,
            quantity = item.quantity,
            priceAtPurchase = item.priceAtPurchase
        )
    }
}