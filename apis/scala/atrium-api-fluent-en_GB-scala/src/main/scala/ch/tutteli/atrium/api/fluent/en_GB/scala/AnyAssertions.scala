package ch.tutteli.atrium.api.fluent.en_GB.scala

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.ExpectImpl
import ch.tutteli.atrium.domain.builders.creating.AnyAssertionsBuilder

class AnyAssertions[T](expect: Expect[T]) {
    def addAssertion(f: AnyAssertionsBuilder => Assertion): Expect[T] =
        expect.addAssertion(f(ExpectImpl.INSTANCE.getAny()))

    def toBe(expected: T): Expect[T] = addAssertion(_.toBe(expect, expected))

    def notToBe(expected: T): Expect[T] = addAssertion(_.notToBe(expect, expected))

    def isSameAs(expected: T): Expect[T] = addAssertion(_.isSame(expect, expected))

    def isNotSameAs(expected: T): Expect[T] = addAssertion(_.isNotSame(expect, expected))
}
