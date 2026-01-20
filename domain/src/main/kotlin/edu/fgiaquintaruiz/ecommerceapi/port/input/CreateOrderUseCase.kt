package edu.fgiaquintaruiz.ecommerceapi.port.input

import edu.fgiaquintaruiz.ecommerceapi.model.Order

// This is the Input Port.
// We call it UseCase to clearly state this is a business action.
fun interface CreateOrderUseCase {
    fun execute(command: CreateOrderCommand): Order
}

// Data class to wrap the input parameters (Command pattern)
data class CreateOrderCommand(
    val userId: Long,
    val items: List<OrderItemCommand>
)

data class OrderItemCommand(
    val productId: Long,
    val quantity: Int
)