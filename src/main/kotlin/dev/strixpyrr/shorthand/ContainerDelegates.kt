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
package dev.strixpyrr.shorthand

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.PolymorphicDomainObjectContainer
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.registering

inline fun <T : Any, reified R : T>       NamedDomainObjectContainer<T>.    getting() =     getting(R::class)
inline fun <T : Any, reified R : T> PolymorphicDomainObjectContainer<T>.   creating() =    creating(R::class)
inline fun <T : Any, reified R : T> PolymorphicDomainObjectContainer<T>.registering() = registering(R::class)

inline infix fun <T : Any, reified R : T>       NamedDomainObjectContainer<T>.getting    (
	noinline configure: R.() -> Unit
) = getting(type = R::class, configuration = configure)
inline infix fun <T : Any, reified R : T> PolymorphicDomainObjectContainer<T>.creating   (
	noinline configure: R.() -> Unit
) = creating(type = R::class, configuration = configure)
inline infix fun <T : Any, reified R : T> PolymorphicDomainObjectContainer<T>.registering(
	noinline configure: R.() -> Unit
) = registering(type = R::class, action = configure)