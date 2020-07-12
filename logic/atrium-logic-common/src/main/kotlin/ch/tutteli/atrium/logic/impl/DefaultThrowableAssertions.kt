package ch.tutteli.atrium.logic.impl

import ch.tutteli.atrium.core.ExperimentalNewExpectTypes
import ch.tutteli.atrium.creating.AssertionContainer
import ch.tutteli.atrium.creating.FeatureExpect
import ch.tutteli.atrium.creating.FeatureExpectOptions
import ch.tutteli.atrium.domain.creating.changers.ChangedSubjectPostStep
import ch.tutteli.atrium.logic.ThrowableAssertions
import ch.tutteli.atrium.logic.changeSubject
import ch.tutteli.atrium.logic.impl.creating.changers.ThrowableThrownFailureHandler
import ch.tutteli.atrium.logic.manualFeature
import ch.tutteli.atrium.logic.toAssertionContainer
import ch.tutteli.atrium.translations.DescriptionThrowableAssertion
import ch.tutteli.atrium.translations.DescriptionThrowableAssertion.OCCURRED_EXCEPTION_CAUSE
import kotlin.reflect.KClass

class DefaultThrowableAssertions : ThrowableAssertions {

    @Suppress("DEPRECATION" /* OptIn is only available since 1.3.70 which we cannot use if we want to support 1.2 */)
    @UseExperimental(ExperimentalNewExpectTypes::class)
    override fun <TExpected : Throwable> cause(
        container: AssertionContainer<out Throwable>,
        expectedType: KClass<TExpected>
    ): ChangedSubjectPostStep<Throwable?, TExpected> =
        container.manualFeature(OCCURRED_EXCEPTION_CAUSE) { cause }.getExpectOfFeature().let { previousExpect ->
            //TODO 0.14.0 factor out a pattern, we are doing this more than once, in API we have withOptions
            FeatureExpect(
                previousExpect,
                FeatureExpectOptions(representationInsteadOfFeature = {
                    it ?: DescriptionThrowableAssertion.NOT_CAUSED
                })
            ).toAssertionContainer().changeSubject.reportBuilder()
                .downCastTo(expectedType)
                .withFailureHandler(ThrowableThrownFailureHandler())
                .build()
        }
}
