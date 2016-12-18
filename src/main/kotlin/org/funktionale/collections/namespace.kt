/*
 * Copyright 2013 Mario Arias
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

package org.funktionale.collections

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 5/07/15
 * Time: 11:59 PM
 */


fun <T> List<T>.tail(): List<T> = this.drop(1)

infix fun <T> T.prependTo(list: List<T>): List<T> = listOf(this) + list

fun <T> List<T>.destructured(): Pair<T, List<T>> = first() to tail()

fun <T> List<T>.chunk(size: Int): List<List<T>> {
    tailrec fun <T> List<T>._chunk(size: Int, initial: List<List<T>> = emptyList()): List<List<T>> {
        val result = initial + listOf(this.take(size))
        return if (this.size > size) this.drop(size)._chunk(size, result) else result
    }
    return this._chunk(size)
}