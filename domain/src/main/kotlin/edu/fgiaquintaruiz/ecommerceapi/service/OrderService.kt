package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.exception.InsufficientStockException
import edu.fgiaquintaruiz.ecommerceapi.exception.ProductNotFoundException
import edu.fgiaquintaruiz.ecommerceapi.model.Order
import edu.fgiaquintaruiz.ecommerceapi.model.OrderItem
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderCommand
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderUseCase
import edu.fgiaquintaruiz.ecommerceapi.port.output.OrderRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort
import java.util.stream.Collectors

class OrderService(
    private val productRepository: ProductRepositoryPort,
    private val orderRepository: OrderRepositoryPort
) : CreateOrderUseCase {

    override fun execute(command: CreateOrderCommand): Order {
        // 1. Fetch products
        val productIds = command.items.stream()
            .map { it.productId }
            .collect(Collectors.toList())

        val products = productRepository.findByIds(productIds)

        // 2. Validate stock and map to OrderItems
        // English comment: If orElseThrow or the stock check triggers,
        // the execution stops here and the order is never saved.
        val orderItems = command.items.stream().map { itemCommand ->
            val product = products.stream()
                .filter { it.id == itemCommand.productId }
                .findFirst()
                .orElseThrow { ProductNotFoundException(itemCommand.productId) }

            if (product.stock < itemCommand.quantity) {
                throw InsufficientStockException(product.name)
            }

            OrderItem(
                productId = product.id!!,
                quantity = itemCommand.quantity,
                priceAtPurchase = product.price
            )
        }.collect(Collectors.toList())

        // 3. Update product stock
        products.forEach { product ->
            val requestedQuantity = command.items.stream()
                .filter { it.productId == product.id }
                .mapToInt { it.quantity }
                .sum()

            val updatedProduct = product.copy(stock = product.stock - requestedQuantity)
            productRepository.save(updatedProduct)
        }

        // 4. Persist the order
        val order = Order(
            userId = command.userId,
            items = orderItems
        )

        return orderRepository.save(order)
    }
}