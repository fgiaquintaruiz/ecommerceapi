package edu.fgiaquintaruiz.ecommerceapi.port.output

import edu.fgiaquintaruiz.ecommerceapi.model.Product

interface ProductRepositoryPort {
    fun findById(id: Long): Product?
    fun findAll(): List<Product>
    fun findByIds(ids: List<Long>): List<Product>
    fun save(product: Product): Product
}