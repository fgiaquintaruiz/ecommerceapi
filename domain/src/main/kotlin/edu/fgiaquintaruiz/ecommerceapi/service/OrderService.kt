package edu.fgiaquintaruiz.ecommerceapi.service

import edu.fgiaquintaruiz.ecommerceapi.port.input.CreateOrderCommand
import edu.fgiaquintaruiz.ecommerceapi.port.output.OrderRepositoryPort
import edu.fgiaquintaruiz.ecommerceapi.port.output.ProductRepositoryPort

class OrderService(productRepo: ProductRepositoryPort, orderRepo: OrderRepositoryPort) {
    fun execute(command: CreateOrderCommand): Any? {
        TODO("Not yet implemented")
    }
}