/*
 * Copyright 2013 - 2016 Mario Arias
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.funktionale.composition

inline infix fun <P1, IP, R> ((P1) -> IP).andThen(crossinline f: (IP) -> R): (P1) -> R = forwardCompose(f)

inline infix fun <IP, R> (() -> IP).andThen(crossinline f: (IP) -> R): () -> R = forwardCompose(f)

inline infix fun <P1, IP, R> ((P1) -> IP).forwardCompose(crossinline f: (IP) -> R): (P1) -> R {
    return { p1: P1 -> f(this(p1)) }
}

inline infix fun <IP, R> (() -> IP).forwardCompose(crossinline f: (IP) -> R): () -> R {
    return { f(this()) }
}

inline infix fun <IP, R, P1> ((IP) -> R).compose(crossinline f: (P1) -> IP): (P1) -> R {
    return { p1: P1 -> this(f(p1)) }
}
