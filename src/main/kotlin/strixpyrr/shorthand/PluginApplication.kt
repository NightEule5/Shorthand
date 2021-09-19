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
package strixpyrr.shorthand

import org.gradle.api.plugins.PluginAware

/**
 * Applies plugins to a project, emulating the root `plugins` DSL.
 */
inline fun PluginAware.applyPlugins(apply: PluginApplicationScope.() -> Unit)
{
	PluginApplicationScope(container = this).apply()
}

/**
 * Applies the Kotlin plugin to a project.
 */
fun PluginAware.applyKotlin(platform: String) =
	applyPlugin("org.jetbrains.kotlin.$platform")

internal fun PluginAware.applyPlugin(id: String) = apply(mapOf("plugin" to id))

@JvmInline
value class PluginApplicationScope
@PublishedApi
internal constructor(
	private val container: PluginAware
)
{
	fun kotlin(platform: String) = container.applyKotlin(platform)
	
	fun id(id: String) = container.applyPlugin(id)
}