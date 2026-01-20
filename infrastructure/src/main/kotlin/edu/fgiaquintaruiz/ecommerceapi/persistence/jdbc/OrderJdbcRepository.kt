package edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc

import edu.fgiaquintaruiz.ecommerceapi.infrastructure.persistence.jdbc.entity.OrderEntity
import org.springframework.data.repository.ListCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderJdbcRepository : ListCrudRepository<OrderEntity, Long>