// Copyright 2021 Strixpyrr
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
@file:Suppress("SpellCheckingInspection")
package strixpyrr.shorthand

import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJsOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

private const val XAllowKotlinPackage = "-Xallow-kotlin-package"

// Multiplatform projects specify their opt-in annotations in a different way, so
// this feature is exposed here on the JVM and JS, but not on Common.

inline fun KotlinJvmOptions.freeCompilerArgs(populate: CompilerArgumentScope.() -> Unit) =
	freeCompilerArgsInternal(populate)

inline fun KotlinJsOptions.freeCompilerArgs(populate: CompilerArgumentScope.() -> Unit) =
	freeCompilerArgsInternal(populate)

@PublishedApi
internal inline fun KotlinCommonOptions.freeCompilerArgsInternal(populate: CompilerArgumentScope.() -> Unit)
{
	val scope = CompilerArgumentScope().apply(populate)
	
	freeCompilerArgs = freeCompilerArgs + scope.arguments
}

@Suppress("MemberVisibilityCanBePrivate")
class CompilerArgumentScope
@PublishedApi
internal constructor()
{
	val optInAnnotations = mutableSetOf<String>()
	
	fun optIn(annotation: String)
	{
		optInAnnotations += annotation
	}
	
	fun optIn(vararg annotations: String)
	{
		optInAnnotations += annotations
	}
	
	fun optIn(annotations: Collection<String>)
	{
		optInAnnotations += annotations
	}
	
	var allowKotlinPackage = false
	
	private fun count() = optInAnnotations.size + (if (allowKotlinPackage) 1 else 0)
	
	@OptIn(ExperimentalStdlibApi::class)
	@PublishedApi
	internal val arguments get() = buildList(capacity = count())
	{
		optInAnnotations.mapTo(destination = this) { "-Xopt-in=$it" }
		
		if (allowKotlinPackage)
			this += XAllowKotlinPackage
	}
	
	companion object
	{
		const val RequiresOptIn = "kotlin.RequiresOptIn"
	}
}