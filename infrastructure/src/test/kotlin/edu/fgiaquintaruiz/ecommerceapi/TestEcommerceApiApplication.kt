package edu.fgiaquintaruiz.ecommerceapi

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<EcommerceApiApplication>().with(TestcontainersConfiguration::class).run(*args)
}
