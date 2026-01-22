package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.annotation.DomainService
import edu.fgiaquintaruiz.ecommerceapi.model.Product
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateProductCommand
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateProductUseCase
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort

@DomainService
class ProductService(
    private val productRepository: ProductRepositoryPort
) : CreateProductUseCase {

    override fun execute(command: CreateProductCommand): Product {
        val product = Product(
            name = command.name,
            description = command.description,
            price = command.price,
            stock = command.stock
        )
        // English: The port handles the communication with the infrastructure layer
        return productRepository.save(product)
    }
}