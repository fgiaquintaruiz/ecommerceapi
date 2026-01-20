plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // Solo Kotlin y librerías de testing
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
}

tasks.withType<Test> {
    useJUnitPlatform()
}