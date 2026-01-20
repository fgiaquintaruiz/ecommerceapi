package edu.fgiaquintaruiz.ecommerceapi.persistence.adapter

import edu.fgiaquintaruiz.ecommerceapi.model.Order
import edu.fgiaquintaruiz.ecommerceapi.port.output.OrderRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc.OrderJdbcRepository
import edu.fgiaquintaruiz.ecommerceapi.infrastructure.persistence.jdbc.entity.OrderEntity
import org.springframework.stereotype.Component

@Component
class OrderPersistenceAdapter(
    private val orderJdbcRepository: OrderJdbcRepository
) : OrderRepositoryPort {

    override fun save(order: Order): Order {
        // Map Domain model to JDBC Entity
        val entity = OrderEntity.fromDomain(order)

        // Save using Spring Data JDBC and map back to Domain
        return orderJdbcRepository.save(entity).toDomain()
    }
}