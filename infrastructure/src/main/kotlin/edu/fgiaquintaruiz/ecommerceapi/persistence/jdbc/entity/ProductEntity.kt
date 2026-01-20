package edu.fgiaquintaruiz.ecommerceapi.persistence.jdbc.entity

import edu.fgiaquintaruiz.ecommerceapi.model.Product
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("products")
data class ProductEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int
) {
    fun toDomain() = Product(id, name, description, price, stock)

    companion object {
        fun fromDomain(p: Product) = ProductEntity(p.id, p.name, p.description, p.price, p.stock)
    }
}