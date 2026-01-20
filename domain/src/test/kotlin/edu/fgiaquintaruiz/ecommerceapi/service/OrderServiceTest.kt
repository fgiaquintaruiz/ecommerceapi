package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.model.Product
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderCommand
import edu.fgiaquintaruiz.ecommerceapi.port.input.OrderItemCommand
import edu.fgiaquintaruiz.ecommerceapi.port.output.OrderRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal

class OrderServiceTest : DescribeSpec({
    val productRepo = mockk<ProductRepositoryPort>()
    val orderRepo = mockk<OrderRepositoryPort>()
    val orderService = OrderService(productRepo, orderRepo)

    describe("Order Creation Logic") {

        it("should throw IllegalStateException when a product does not have enough stock") {
            // Arrange
            val productId = 1L
            val command = CreateOrderCommand(
                userId = 100L,
                items = listOf(OrderItemCommand(productId, quantity = 10))
            )
            val productWithLowStock = Product(
                id = productId,
                name = "Laptop",
                description = "Gaming",
                price = BigDecimal("1000"),
                stock = 2 // Only 2 in stock, but we want 10
            )

            every { productRepo.findByIds(listOf(productId)) } returns listOf(productWithLowStock)

            // Act & Assert
            shouldThrow<IllegalStateException> {
                orderService.execute(command)
            }
        }

        it("should throw NoSuchElementException when a product does not exist") {
            // Arrange
            val productId = 99L
            val command = CreateOrderCommand(userId = 1L, items = listOf(OrderItemCommand(productId, 1)))

            every { productRepo.findByIds(listOf(productId)) } returns emptyList()

            // Act & Assert
            shouldThrow<NoSuchElementException> {
                orderService.execute(command)
            }
        }
    }
})