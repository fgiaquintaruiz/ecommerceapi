package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.port.output.OrderRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderCommand
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import jakarta.persistence.EntityNotFoundException

class OrderServiceTest {

    private val productRepo = mockk<ProductRepositoryPort>()
    private val orderRepo = mockk<OrderRepositoryPort>()
    private val orderService = OrderService(productRepo, orderRepo)

    @Test
    fun `should throw exception when product does not exist`() {
        // Arrange
        val command = CreateOrderCommand(userId = 1L, items = listOf())
        // We simulate that the repository finds nothing
        every { productRepo.findByIds(any()) } returns emptyList()

        // Act & Assert
        assertThrows<EntityNotFoundException> {
            orderService.execute(command)
        }
    }
}