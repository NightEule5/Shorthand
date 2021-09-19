@file:Suppress("SuspiciousCollectionReassignment")

plugins {
	kotlin("jvm") version "1.5.30"
	`java-gradle-plugin`
	`maven-publish`
}

group = "dev.strixpyrr.shorthand"
version = "0.0.1"

repositories()
{
	mavenCentral()
}

dependencies()
{
	implementation(kotlin("stdlib"))
	implementation(kotlin("gradle-plugin", version = "1.5.30"))
	implementation(gradleApi())
	implementation(gradleKotlinDsl())
}

tasks()
{
	compileKotlin()
	{
		kotlinOptions.run()
		{
			jvmTarget       = "1.8"
			languageVersion = "1.5"
			
			freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
		}
	}
}

gradlePlugin()
{
	plugins()
	{
		val shorthand by creating()
		{
			id = "dev.strixpyrr.shorthand"
			implementationClass = "dev.strixpyrr.shorthand.EmptyPlugin"
			displayName = "Shorthand"
			description =
				"""
				Provides an easy shorthand for certain tasks in the Gradle Kotlin
				DSL without obfuscating function.
				""".trimIndent()
		}
	}
}

val kotlinSourcesJar by tasks

publishing()
{
	repositories()
	{
		mavenLocal()
	}
	
	publications()
	{
		create<MavenPublication>("shorthand")
		{
			from(components["kotlin"])
			artifact(kotlinSourcesJar)
			
			artifactId = artifactId.toLowerCase()
		}
	}
}