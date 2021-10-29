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

import dev.strixpyrr.shorthand.CompilerArgumentScope
import dev.strixpyrr.shorthand.CompilerArgumentScope.Companion.RequiresOptIn
import dev.strixpyrr.shorthand.JvmCompilerArgumentScope
import dev.strixpyrr.shorthand.JvmDefaultMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain

object KotlinOptionsTest : StringSpec(
{
	"JvmDefault"()
	{
		JvmCompilerArgumentScope().run()
		{
			jvmDefault = JvmDefaultMode.All
			
			arguments shouldContain "-Xjvm-default=all"
			
			jvmDefault = JvmDefaultMode.AllCompatibility
			
			arguments shouldContain "-Xjvm-default=all-compatibility"
		}
	}
	
	"OptIn"()
	{
		CompilerArgumentScope().run()
		{
			optIn(RequiresOptIn)
			
			arguments shouldContain "-Xopt-in=$RequiresOptIn"
		}
	}
	
	"AllowKotlinPackage"()
	{
		CompilerArgumentScope().run()
		{
			allowKotlinPackage = true
			
			arguments shouldContain "-Xallow-kotlin-package"
			
			allowKotlinPackage = false
			
			arguments shouldNotContain "-Xallow-kotlin-package"
		}
	}
})