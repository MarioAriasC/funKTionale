/*
 * Original work Copyright 2013 - 2016 Mario Arias
 * Modified work Copyright 2017 Petter Ljungqvist [Houston Inc.]
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

package org.funktionale.eithernoopt

import org.funktionale.eithernoopt.*
import org.funktionale.eithernoopt.Either.Left
import org.funktionale.eithernoopt.Either.Right
import org.testng.Assert.*
import org.testng.annotations.Test

/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 17/05/13
 * Time: 21:53
 */
class EitherTest {

    val pair = 5 to "kotlin"
    val left = pair.toLeft()
    val right = pair.toRight()

    @Test fun get() {
        assertEquals(left.left().get(), 5)
        assertEquals(right.right().get(), "kotlin")
    }

    //[Test(expectedExceptions = array(javaClass<NoSuchElementException>()))]

    @Test fun getWithException() {
        try {
            assertEquals(right.left().get(), 5)
            fail()
        } catch(e: Exception) {
            //expected
        }
        try {
            assertEquals(left.right().get(), "kotlin")
            fail()
        } catch(e: Exception) {
            //Expected
        }
    }

    @Test fun forEach() {
        left.left().forEach {
            assertEquals(it * 2, 10)
        }

        right.right().forEach {
            assertEquals(it.length, 6)
        }
    }

    @Test fun getOrElse() {
        assertEquals(left.left().getOrElse { 2 }, 5)
        assertEquals(left.right().getOrElse { "java" }, "java")
    }

    @Test fun exists() {
        assertTrue(left.left().exists { it == 5 })
        assertFalse(left.right().exists { it == "kotlin" })
    }

    @Test fun flatMap() {
        assertEquals(left.left().flatMap { Left<String, Int>(it.toString()) }.left().get(), "5")
        assertEquals(right.right().flatMap { Right<String, Int>(it.length) }.right().get(), 6)
    }

    @Test fun map() {
        assertEquals(left.left().map(Int::toString).left().get(), "5")
        assertEquals(right.right().map { it.length }.right().get(), 6)
    }

    @Test fun filter() {
        assertEquals(left.left().filter { it == 5 }?.left()?.get(), 5)
        assertNull(left.left().filter { it == 6 })
        assertEquals(right.right().filter { it.startsWith('k') }?.right()?.get(), "kotlin")
        assertNull(right.right().filter { it.startsWith('j') })
    }

    @Test fun toList() {
        assertEquals(left.left().toList(), listOf(5))
        assertEquals(left.right().toList(), listOf<Int>())
    }

    @Test fun toOption() {
        assertEquals(left.left().getOrNull(), 5)
        assertNull(left.right().getOrNull())
    }

    @Test fun fold() {
        assertEquals(left.fold(Int::toString) { it }, "5")
    }

    @Test fun swap() {
        assertEquals(left.swap().right().get(), 5)
        assertEquals(right.swap().left().get(), "kotlin")
    }

    @Test fun components() {
        val (aInt, aNullString) = left
        assertNotNull(aInt)
        assertNull(aNullString)

        val (aNullInt, aString) = right
        assertNull(aNullInt)
        assertNotNull(aString)
    }

    @Test fun merge() {
        assertEquals(left.merge(), 5)
        assertEquals(right.merge(), "kotlin")
    }

    @Test fun either() {
        val e: Either<Throwable, Nothing> = eitherTry {
            throw RuntimeException()
        }
        assertTrue(e.isLeft())
    }

    @Test fun sequential() {
        fun parseInts(ints: List<String>): Either<Throwable, List<Int>> {
            return ints.map { eitherTry { it.toInt() } }.eitherSequential()
        }

        assertEquals(parseInts(listOf("1", "2", "3")), Right<Exception, List<Int>>(listOf(1, 2, 3)))
        assertTrue(parseInts(listOf("1", "foo", "3")) is Left)
    }

    val some: String? = "kotlin"
    val none: String? = null

    @Test fun toRight() {
        assertTrue(some.toEitherRight { 0 }.isRight())
        assertFalse(none.toEitherRight { 0 }.isRight())
    }


    @Test fun toLeft() {
        assertTrue(some.toEitherLeft { 0 }.isLeft())
        assertFalse(none.toEitherLeft { 0 }.isLeft())
    }
}
