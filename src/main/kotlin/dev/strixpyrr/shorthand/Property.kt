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
@file:Suppress("NOTHING_TO_INLINE")
package dev.strixpyrr.shorthand

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

inline operator fun <T> Property<T>.invoke(         ): T = get()
inline operator fun <T> Property<T>.invoke(value: T?)    = set(value)

// MapProperty and ListProperty don't implement Property, so we'll have to define
// these as separate cases.

inline operator fun <K, V> MapProperty<K, V>.invoke(                 ): Map<K, V> = get()
inline operator fun <K, V> MapProperty<K, V>.invoke(value: Map<K, V>?)            = set(value)

inline operator fun <T> ListProperty<T>.invoke(               ): List<T> = get()
inline operator fun <T> ListProperty<T>.invoke(value: List<T>?)          = set(value)

// Collection operations

operator fun <K, V> MapProperty<K, V>.get(key: K): V? = getting(key).orNull

operator fun <K, V> MapProperty<K, V>.set(key: K, value:          V ) = put(key, value)
operator fun <K, V> MapProperty<K, V>.set(key: K, value: Provider<V>) = put(key, value)

operator fun <K, V> MapProperty<K, V>.contains(key: K) = key in keySet().get()

operator fun <K, V> MapProperty<K, V>.plus      (other: Map<K, V>          ) = get() + other
operator fun <K, V> MapProperty<K, V>.plusAssign(other:          Map<K, V> ) = putAll(other)
operator fun <K, V> MapProperty<K, V>.plusAssign(other: Provider<Map<K, V>>) = putAll(other)

fun MapProperty<*, *>.clear() { empty() }

operator fun <T> ListProperty<T>.plus      (value: T          ) = get() + value
operator fun <T> ListProperty<T>.plusAssign(value:          T ) = add   (value)
operator fun <T> ListProperty<T>.plusAssign(value: Provider<T>) = add   (value)
operator fun <T> ListProperty<T>.plusAssign(value: Iterable<T>) = addAll(value)

fun ListProperty<*>.clear() { empty() }
