package ch.tutteli.atrium.logic

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.core.Option
import ch.tutteli.atrium.creating.Expect
import kotlin.reflect.KClass

interface Logic<T> {
    val maybeSubject: Option<T>
    fun <I : Any> getImpl(kClass: KClass<I>, defaultFactory: () -> I): I
    fun addAssertion(a: Assertion): Expect<@UnsafeVariance T>
}
