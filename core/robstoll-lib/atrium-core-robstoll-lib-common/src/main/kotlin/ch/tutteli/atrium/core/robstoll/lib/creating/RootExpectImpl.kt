package ch.tutteli.atrium.core.robstoll.lib.creating

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.assertions.builders.assertionBuilder
import ch.tutteli.atrium.assertions.builders.invisibleGroup
import ch.tutteli.atrium.assertions.builders.root
import ch.tutteli.atrium.core.Option
import ch.tutteli.atrium.core.coreFactory
import ch.tutteli.atrium.core.getOrElse
import ch.tutteli.atrium.core.polyfills.cast
import ch.tutteli.atrium.creating.*
import ch.tutteli.atrium.reporting.AtriumError
import ch.tutteli.atrium.reporting.SHOULD_NOT_BE_SHOWN_TO_THE_USER_BUG
import ch.tutteli.atrium.reporting.Text
import ch.tutteli.atrium.reporting.translating.Translatable
import ch.tutteli.kbox.forEachIn
import kotlin.reflect.KClass

abstract class BaseExpectImpl<T>(
    override val maybeSubject: Option<T>
) : Expect<T>, AssertionContainer<T> {

    @Deprecated(
        "Do not access subject as it might break reporting. In contexts where it is safe to access the subject, it is passed by parameter and can be accessed via `it`. See KDoc for migration hints; will be removed with 1.0.0",
        ReplaceWith("it")
    )
    final override val subject: T by lazy {
        maybeSubject.getOrElse {
            @Suppress("DEPRECATION")
            throw ch.tutteli.atrium.creating.PlantHasNoSubjectException()
        }
    }

    override fun addAssertionsCreatedBy(assertionCreator: Expect<T>.() -> Unit): Expect<T> {
        val assertions = coreFactory.newCollectingAssertionContainer(maybeSubject)
            .addAssertionsCreatedBy(assertionCreator)
            .getAssertions()
        return addAssertions(assertions)
    }

    protected fun addAssertions(assertions: List<Assertion>): Expect<T> {
        return when (assertions.size) {
            0 -> this
            1 -> addAssertion(assertions.first())
            else -> addAssertion(assertionBuilder.invisibleGroup.withAssertions(assertions).build())
        }
    }

    override fun addAssertion(assertion: Assertion): Expect<T> =
        append(assertion)

    private val implFactories: MutableMap<KClass<*>, (() -> Nothing) -> () -> Any> = mutableMapOf()


    override fun <I : Any> getImpl(kClass: KClass<I>, defaultFactory: () -> I): I {
        @Suppress("UNCHECKED_CAST")
        val implFactory = implFactories[kClass] as ((() -> I) -> () -> Any)?
        return if (implFactory != null) {
            val impl = implFactory(defaultFactory)()
            kClass.cast(impl)
        } else defaultFactory()
    }

    @PublishedApi
    internal fun <I : Any> registerImpl(kClass: KClass<I>, implFactory: (oldFactory: () -> I) -> () -> I) {
        implFactories[kClass] = implFactory
    }
}

@Suppress("DEPRECATION" /* OptIn is only available since 1.3.70 which we cannot use if we want to support 1.2 */)
@UseExperimental(ExperimentalExpectConfig::class)
class RootExpectImpl<T>(
    maybeSubject: Option<T>,
    private val assertionVerb: Translatable,
    options: ExpectOptions<T>?
) : BaseExpectImpl<T>(maybeSubject), RootExpect<T>, AssertionContainer<T> {

    private val representation: Any? =
        options?.representationInsteadOfSubject?.let { provider ->
            this.maybeSubject.fold({ null }) { provider(it) }
        } ?: maybeSubject.getOrElse {
            // a RootExpect without a defined subject is almost certain a bug
            Text(SHOULD_NOT_BE_SHOWN_TO_THE_USER_BUG)
        }

    private val reporter = options?.reporter ?: ch.tutteli.atrium.reporting.reporter

    /**
     * All made assertions so far.
     * This list is intentionally not thread-safe, this class is not intended for multi-thread usage.
     */
    private val assertions: MutableList<Assertion> = mutableListOf()

    //TODO move to ExpectOptions
    inline fun <reified I : Any> withImplFactory(noinline implFactory: (oldFactory: () -> I) -> () -> I) {
        registerImpl(I::class, implFactory)
    }

    override fun append(assertion: Assertion): Expect<T> {
        assertions.add(assertion)
        if (!assertion.holds()) {
            val assertionGroup = assertionBuilder.root
                .withDescriptionAndRepresentation(assertionVerb, representation)
                .withAssertion(assertion)
                .build()

            val sb = StringBuilder()
            reporter.format(assertionGroup, sb)
            throw AtriumError.create(sb.toString(), reporter.atriumErrorAdjuster)
        }
        return this
    }

}

class FeatureExpectImpl<T, R>(
    override val previousExpect: Expect<T>,
    maybeSubject: Option<R>,
    @Suppress("DEPRECATION" /* OptIn is only available since 1.3.70 which we cannot use if we want to support 1.2 */)
    @UseExperimental(ExperimentalExpectConfig::class)
    private val featureConfig: FeatureExpectConfig,
    assertions: List<Assertion>
) : BaseExpectImpl<R>(maybeSubject),
    FeatureExpect<T, R> {

    init{
        addAssertions(assertions)
    }

    /**
     * All made assertions so far.
     * This list is intentionally not thread-safe, this class is not intended for multi-thread usage.
     */
    private val assertions: MutableList<Assertion> = mutableListOf()
//
//
//
//    override fun getAssertions(): List<Assertion> = getCopyOfAssertions()

    @Suppress("DEPRECATION" /* OptIn is only available since 1.3.70 which we cannot use if we want to support 1.2 */)
    @UseExperimental(ExperimentalExpectConfig::class)
    override fun append(assertion: Assertion): Expect<R> {
        assertions.add(assertion)
        if (!assertion.holds()) {
            previousExpect.addAssertion(
                assertionBuilder.feature
                    .withDescriptionAndRepresentation(featureConfig.description, featureConfig.representation)
                    .withAssertions(ArrayList(assertions))
                    .build()
            )
        }
        return this
    }
}
