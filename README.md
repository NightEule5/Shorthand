# Shorthand

Shorthand provides a more concise syntax for some common tasks in the Gradle kotlin
DSL.

## Installation

There are two ways to install Shorthand: via the `buildscript` block or via the `pluginManagement` block.

### `buildscript` block

Put this at the top of your `build.gradle.kts`:

```kotlin
buildscript {
	repositories {
		maven(url = "https://jitpack.io")
	}
	
	dependencies {
		classpath(group = "dev.strixpyrr", name = "shorthand", version = "0.0.2")
	}
}
```

### `pluginManagement` block

Put this somewhere in your `settings.gradle.kts`:

```kotlin
pluginManagement {
	repositories {
		maven(url = "https://jitpack.io")
	}
	
	plugins {
		id("dev.strixpyrr.shorthand") version "0.0.2"
	}
}
```

Then apply it using the `plugins` DSL:

```kotlin
plugins {
	id("dev.strixpyrr.shorthand")
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