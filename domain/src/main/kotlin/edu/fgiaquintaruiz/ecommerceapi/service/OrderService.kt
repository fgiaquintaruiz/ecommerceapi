package edu.fgiaquintaruiz.ecommerceapi.service

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
        // 1. Obtener IDs y buscar productos (Eficiencia: una sola llamada al repo)
        val productIds = command.items.stream()
            .map { it.productId }
            .collect(Collectors.toList())

        val products = productRepository.findByIds(productIds)

        // 2. Mapear a OrderItems validando stock (Uso de Streams y Optional-style)
        val orderItems = command.items.stream().map { itemCommand ->
            val product = products.stream()
                .filter { it.id == itemCommand.productId }
                .findFirst()
                .orElseThrow { NoSuchElementException("Producto con ID ${itemCommand.productId} no encontrado") }

            check(product.stock >= itemCommand.quantity) {
                throw IllegalStateException("Stock insuficiente para el producto: ${product.name}")
            }

            OrderItem(
                productId = product.id!!,
                quantity = itemCommand.quantity,
                priceAtPurchase = product.price
            )
        }.collect(Collectors.toList())

        // 3. Actualizar stock en el dominio y persistir cambios
        products.forEach { product ->
            val requestedQuantity = command.items.stream()
                .filter { it.productId == product.id }
                .mapToInt { it.quantity }
                .sum()

            val updatedProduct = product.copy(stock = product.stock - requestedQuantity)
            productRepository.save(updatedProduct)
        }

        // 4. Crear y guardar la Orden
        val order = Order(
            userId = command.userId,
            items = orderItems
        )

        return orderRepository.save(order)
    }
}