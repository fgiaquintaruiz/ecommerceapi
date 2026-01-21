package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.exception.InsufficientStockException
import edu.fgiaquintaruiz.ecommerceapi.exception.ProductNotFoundException
import edu.fgiaquintaruiz.ecommerceapi.model.Order
import edu.fgiaquintaruiz.ecommerceapi.model.Product
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderCommand
import edu.fgiaquintaruiz.ecommerceapi.port.input.OrderItemCommand
import edu.fgiaquintaruiz.ecommerceapi.port.output.OrderRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal

class OrderServiceTest : DescribeSpec({
    val productRepo = mockk<ProductRepositoryPort>()
    val orderRepo = mockk<OrderRepositoryPort>()
    val orderService = OrderService(productRepo, orderRepo)

    // English comment: Reset mocks before each test to prevent cross-test interference
    beforeTest {
        clearMocks(productRepo, orderRepo)
    }

    describe("Order Service Logic") {

        it("should successfully create an order and update stock") {
            val productId = 1L
            val command = CreateOrderCommand(100L, listOf(OrderItemCommand(productId, 2)))
            val product = Product(productId, "Laptop", "Gaming", BigDecimal("1000"), 10)

            every { productRepo.findByIds(listOf(productId)) } returns listOf(product)
            every { productRepo.save(any()) } returns product
            every { orderRepo.save(any()) } answers { firstArg<Order>() }

            val result = orderService.execute(command)

            result shouldNotBe null
            verify(exactly = 1) { productRepo.save(withArg { it.stock shouldBe 8 }) }
            verify(exactly = 1) { orderRepo.save(any()) }
        }

        it("should throw InsufficientStockException and NOT save the order when stock is low") {
            val productId = 1L
            val command = CreateOrderCommand(100L, listOf(OrderItemCommand(productId, 10)))
            val product = Product(productId, "Laptop", "Gaming", BigDecimal("1000"), 2)

            every { productRepo.findByIds(listOf(productId)) } returns listOf(product)

            shouldThrow<InsufficientStockException> {
                orderService.execute(command)
            }

            // English comment: Ensure the repository was never called due to the exception
            verify(exactly = 0) { orderRepo.save(any()) }
        }

        it("should throw ProductNotFoundException when product does not exist") {
            val productId = 99L
            val command = CreateOrderCommand(1L, listOf(OrderItemCommand(productId, 1)))

            every { productRepo.findByIds(listOf(productId)) } returns emptyList()

            shouldThrow<ProductNotFoundException> {
                orderService.execute(command)
            }
        }
    }
})
