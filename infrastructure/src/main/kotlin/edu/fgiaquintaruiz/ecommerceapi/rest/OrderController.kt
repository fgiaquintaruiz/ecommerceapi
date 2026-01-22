package edu.fgiaquintaruiz.ecommerceapi.rest

import edu.fgiaquintaruiz.ecommerceapi.model.Order
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderCommand
import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Endpoints for managing customer orders")
class OrderController(private val createOrderUseCase: CreateOrderUseCase) {

    @PostMapping
    @Operation(summary = "Create a new order", description = "Validates stock and persists the order")
    fun createOrder(@RequestBody command: CreateOrderCommand): ResponseEntity<Order> {
        return ResponseEntity.status(201).body(createOrderUseCase.execute(command))
    }
}
