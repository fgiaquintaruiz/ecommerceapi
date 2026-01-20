package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.model.Order
import edu.fgiaquintaruiz.ecommerceapi.model.Product
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderCommand
import edu.fgiaquintaruiz.ecommerceapi.port.input.OrderItemCommand
import edu.fgiaquintaruiz.ecommerceapi.port.output.OrderRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal

class OrderServiceTest : DescribeSpec({
    val productRepo = mockk<ProductRepositoryPort>()
    val orderRepo = mockk<OrderRepositoryPort>()
    val orderService = OrderService(productRepo, orderRepo)

    describe("OrderService TDD - Success Case") {

        it("should successfully create an order, update stock and return the saved order") {
            // Given (Arrange)
            val productId = 1L
            val userId = 100L
            val quantity = 2
            val price = BigDecimal("50.0")

            val command = CreateOrderCommand(
                userId = userId,
                items = listOf(OrderItemCommand(productId, quantity))
            )

            val product = Product(
                id = productId,
                name = "Test Product",
                description = "Description",
                price = price,
                stock = 10
            )

            // Definimos el comportamiento de los mocks
            every { productRepo.findByIds(listOf(productId)) } returns listOf(product)
            every { productRepo.save(any()) } returns product // Simula actualización de stock
            every { orderRepo.save(any()) } answers { firstArg<Order>() } // Devuelve lo que recibe

            // When (Act)
            val result = orderService.execute(command)

            // Then (Assert)
            result shouldNotBe null
            result.userId shouldBe userId
            result.items.size shouldBe 1
            result.items[0].priceAtPurchase shouldBe price
            result.items[0].quantity shouldBe quantity

            // English comment: Verify that product stock was updated to 8 (10 - 2)
            verify(exactly = 1) {
                productRepo.save(withArg {
                    it.id shouldBe productId
                    it.stock shouldBe 8
                })
            }

            // English comment: Verify that the order was actually persisted
            verify(exactly = 1) { orderRepo.save(any()) }
        }
    }
})