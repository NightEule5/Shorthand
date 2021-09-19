# Shorthand

Shorthand provides a more concise syntax for some common tasks in the Gradle kotlin
DSL.

## Installation

Put this at the top of your `build.gradle.kts`:

```kotlin
buildscript {
	repositories {
		maven(url = "https://jitpack.io")
	}
	
	dependencies {
		classpath(group = "dev.strixpyrr", name = "shorthand", version = "0.0.1")
	}
}
```

## Usage

### Commonly-Used Compiler Arguments

```kotlin
// Before
tasks {
	compileKotlin {
		freeCompilerArgs =
			listOf(
				"-Xopt-in=kotlin.RequiresOptIn",
				"-Xjvm-default=all"
			)
	}
}

// After
tasks {
	compileKotlin {
		freeCompilerArgs {
			optIn(RequiresOptIn)
			
			jvmDefault = JvmDefaultMode.All
		}
	}
}
```

### `Property` Accessors

```kotlin
// Before
tasks {
	jar {
		archiveClassifier.set("...")
		
		val classifier = archiveClassifier.get()
	}
}

// After
tasks {
	jar {
		archiveClassifier("...")
		
		val classifier = archiveClassifier()
	}
}
```

### Plugin Application

```kotlin
// Before
subprojects {
	apply(plugin = "org.jetbrains.kotlin.multiplatform")
	apply(plugin = "maven-publish")
}

// After
subprojects {
	applyPlugins {
		kotlin("multiplatform")
		
		id("maven-publish")
	}
}
```