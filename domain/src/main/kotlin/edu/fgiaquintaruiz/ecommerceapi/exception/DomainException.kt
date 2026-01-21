package edu.fgiaquintaruiz.ecommerceapi.exception

//Base class for all domain-specific business exceptions
sealed class DomainException(message: String) : RuntimeException(message)

class InsufficientStockException(productName: String) :
    DomainException("Insufficient stock for product: $productName")

class ProductNotFoundException(id: Long) :
    DomainException("Product with ID $id not found")