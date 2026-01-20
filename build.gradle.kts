plugins {
	alias(libs.plugins.kotlin.jvm) apply false
	alias(libs.plugins.kotlin.spring) apply false
	alias(libs.plugins.spring.boot) apply false
	alias(libs.plugins.spring.dependency.management) apply false
	alias(libs.plugins.kotlin.jpa) apply false
}

allprojects {
	group = "edu.fgiaquintaruiz"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")

	extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
		jvmToolchain(24)
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}
}