package edu.fgiaquintaruiz.ecommerceapi.port.input

import edu.fgiaquintaruiz.ecommerceapi.model.Product
import java.math.BigDecimal

data class CreateProductCommand(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int
)

fun interface CreateProductUseCase {
    fun execute(command: CreateProductCommand): Product
}