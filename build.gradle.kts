plugins {
	kotlin("jvm") version "1.5.30-RC"
	`java-gradle-plugin`
}

group = "strixpyrr.shorthand"
version = "0.0.1"

repositories()
{
	mavenCentral()
}

dependencies()
{
	implementation(kotlin("stdlib"))
	implementation(gradleKotlinDsl())
}

tasks()
{
	compileKotlin()
	{
		kotlinOptions.run()
		{
			languageVersion = "1.5"
			jvmTarget       = "1.8"
		}
	}
}

gradlePlugin()
{
	plugins()
	{
		val shorthand by creating()
		{
			id = "strixpyrr.shorthand"
			implementationClass = "strixpyrr.shorthand.EmptyPlugin"
			displayName = "Shorthand"
			description =
				"""
				Provides an easy shorthand for certain tasks in the Gradle Kotlin
				DSL without obfuscating function.
				""".trimIndent()
		}
	}
}