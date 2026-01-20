package edu.fgiaquintaruiz.ecommerceapi.persistence.adapter

import edu.fgiaquintaruiz.ecommerceapi.model.Product
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc.ProductJdbcRepository
import edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc.entity.ProductEntity
import org.springframework.stereotype.Component

@Component
class ProductPersistenceAdapter(
    private val repository: ProductJdbcRepository
) : ProductRepositoryPort {

    override fun findById(id: Long): Product? {
        return repository.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun findAll(): List<Product> {
        return repository.findAll().map { it.toDomain() }
    }

    override fun findByIds(ids: List<Long>): List<Product> {
        return repository.findAllById(ids).map { it.toDomain() }
    }

    override fun save(product: Product): Product {
        val entity = ProductEntity.fromDomain(product)
        return repository.save(entity).toDomain()
    }
}