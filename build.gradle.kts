@file:Suppress("SuspiciousCollectionReassignment")

import Build_gradle.PublicationConstants.ApacheName
import Build_gradle.PublicationConstants.ApacheUrl
import Build_gradle.PublicationConstants.Description
import Build_gradle.PublicationConstants.DisplayName
import Build_gradle.PublicationConstants.Inception
import Build_gradle.PublicationConstants.Url

plugins {
	kotlin("jvm") version "1.5.31"
	`java-gradle-plugin`
	`maven-publish`
	signing
}

group = "dev.strixpyrr"
version = "0.0.4"

repositories()
{
	mavenCentral()
}

dependencies()
{
	implementation(kotlin("stdlib"))
	implementation(kotlin("gradle-plugin", version = "1.5.31"))
	implementation(gradleApi())
	implementation(gradleKotlinDsl())
	
	testImplementation(deps.kotest)
	testImplementation(deps.mockk )
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
	
	withType<Test>
	{
		useJUnitPlatform()
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
			displayName = DisplayName
			description = Description
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
			
			pom()
			{
				description  .set(Description)
				url          .set(Url)
				inceptionYear.set(Inception)
				
				licenses()
				{
					license()
					{
						name.set(ApacheName)
						url .set(ApacheUrl )
					}
				}
				
				developers()
				{
					developer()
					{
						name.set("NightEule5")
					}
				}
			}
		}
	}
}

if (!isJitpack)
	signing()
	{
		useGpgCmd()
		
		sign(publishing.publications["shorthand"])
	}

object PublicationConstants
{
	const val Description =
		"Provides an easy shorthand for certain tasks in the Gradle Kotlin DSL " +
		"without obfuscating function."
	
	const val DisplayName = "Shorthand"
	
	const val Url = "https://github.com/NightEule5/Shorthand"
	
	const val Inception = "2021"
	
	// License
	
	const val ApacheName = "Apache-2.0"
	const val ApacheUrl  = "http://www.apache.org/licenses/LICENSE-2.0"
}

val isJitpack get() = System.getenv("JITPACK") != null