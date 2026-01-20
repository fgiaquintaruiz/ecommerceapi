package edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc

import edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc.entity.ProductEntity
import org.springframework.data.repository.ListCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductJdbcRepository : ListCrudRepository<ProductEntity, Long>