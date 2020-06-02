package ch.tutteli.atrium.creating

import ch.tutteli.atrium.assertions.Assertion

/**
 * Represents an [Expect] which results due to a change of the [Expect.maybeSubject] to a feature of the subject.
 *
 * A change can for instance be a feature extraction such as `expect(listOf(1)).get(0)`
 * but also just a feature assertion such as `expect(listOf(1)).feature { f(it::size) }`
 *
 * It exposes the [config] as well as the already specified [Assertion]s (see [getAssertions]) in contrast to [Expect].
 */
interface FeatureExpect<T, R> : Expect<R> {

    /**
     * The [Expect] from which this feature is derived/was extracted.
     */
    val previousExpect: Expect<T>

    /**
     * The chosen [FeatureExpectConfig].
     */
    @Suppress("DEPRECATION" /* OptIn is only available since 1.3.70 which we cannot use if we want to support 1.2 */)
    @UseExperimental(ExperimentalExpectConfig::class)
    val config: FeatureExpectConfig
//
//    /**
//     * The so far specified [Assertion]s for this feature.
//     */
//    fun getAssertions(): List<Assertion>
}
