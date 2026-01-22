package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.model.Product
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateProductCommand
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal

class ProductServiceTest : DescribeSpec({
    val productRepo = mockk<ProductRepositoryPort>()
    val productService = ProductService(productRepo)

    describe("Product Service Logic") {
        it("should successfully create and persist a new product") {
            // Arrange
            val command = CreateProductCommand("Mouse", "Wireless", BigDecimal("25.0"), 50)
            val expectedProduct = Product(1L, "Mouse", "Wireless", BigDecimal("25.0"), 50)

            every { productRepo.save(any()) } returns expectedProduct

            // Act
            val result = productService.execute(command)

            // Assert
            result.name shouldBe "Mouse"
            verify(exactly = 1) { productRepo.save(any()) }
        }
    }
})