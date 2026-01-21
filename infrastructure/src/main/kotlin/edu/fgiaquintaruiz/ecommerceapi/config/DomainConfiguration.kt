package edu.fgiaquintaruiz.ecommerceapi.config

import edu.fgiaquintaruiz.ecommerceapi.annotation.DomainService
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    basePackages = ["edu.fgiaquintaruiz.ecommerceapi"],
    includeFilters = [
        ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = [DomainService::class]
        )
    ]
)
class DomainConfiguration