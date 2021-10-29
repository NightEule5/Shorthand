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
package dev.strixpyrr.shorthandTest

import dev.strixpyrr.shorthand.applyPlugins
import dev.strixpyrr.shorthand.isJitpack
import dev.strixpyrr.shorthandTest.MiscTest.shouldContainKotlinPlugin
import dev.strixpyrr.shorthandTest.MiscTest.shouldContainPlugin
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.system.withEnvironment
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.gradle.api.plugins.PluginAware

object MiscTest : StringSpec(
{
	"Jitpack is detected"()
	{
		withEnvironment(key = "JITPACK", value = "true")
		{
			isJitpack shouldBe true
		}
		
		isJitpack shouldBe false
	}
	
	"Plugins are applied"()
	{
		val application = slot<Map<String, *>>()
		
		val plugins: PluginAware = mockk()
		{
			every()
			{
				apply(capture(application))
			} answers { }
		}
		
		plugins.applyPlugins()
		{
			id("example.plugin")
			
			application shouldContainPlugin "example.plugin"
			
			kotlin("multiplatform")
			
			application shouldContainKotlinPlugin "multiplatform"
		}
	}
})
{
	@JvmStatic
	internal infix fun CapturingSlot<Map<String, Any?>>.shouldContainKotlinPlugin(platform: String) =
		shouldContainPlugin("org.jetbrains.kotlin.$platform")
	
	@JvmStatic
	internal infix fun CapturingSlot<Map<String, Any?>>.shouldContainPlugin(id: String) =
		captured shouldContainExactly mapOf("plugin" to id)
}