package ch.tutteli.atrium.creating

import ch.tutteli.atrium.reporting.Reporter
import ch.tutteli.atrium.reporting.translating.Translatable

/**
 * Additional (non-mandatory) options to create an [Expect].
 *
 * @property assertionVerb Defines a custom assertion verb if not null.
 * @property representationInsteadOfSubject Defines a custom representation based on a present subject if not null.
 * @property reporter Defines a custom reporter if not null.
 */
data class ExpectOptions<T>(
    val assertionVerb: Translatable? = null,
    val representationInsteadOfSubject: ((T) -> Any)? = null,
    val reporter: Reporter? = null
) {
    /**
     * Merges the given [options] with this object creating a new [ExpectOptions]
     * where defined properties in [options] will have precedence over properties defined in this instance.
     *
     * For instance, this object has defined [representationInsteadOfSubject] (meaning it is not `null`) and
     * the given [options] as well, then the resulting [ExpectOptions] will have the
     * [representationInsteadOfSubject] of [options].
     */
    fun merge(options: ExpectOptions<T>): ExpectOptions<T> =
        ExpectOptions(
            options.assertionVerb ?: assertionVerb,
            options.representationInsteadOfSubject ?: representationInsteadOfSubject,
            options.reporter ?: reporter
        )
}
