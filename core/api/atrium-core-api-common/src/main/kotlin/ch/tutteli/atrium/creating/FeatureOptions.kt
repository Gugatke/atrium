package ch.tutteli.atrium.creating

import ch.tutteli.atrium.reporting.translating.Translatable

/**
 * Additional (non-mandatory) options to create an [Expect].
 *
 * @property description Defines a custom description if not null.
 * @property representationInsteadOfFeature Defines a custom representation based on a present subject if not null.
 */
data class FeatureOptions<R>(
    val description: Translatable? = null,
    val representationInsteadOfFeature: ((R) -> Any)? = null
) {
    /**
     * Merges the given [options] with this object creating a new [FeatureOptions]
     * where defined properties in [options] will have precedence over properties defined in this instance.
     *
     * For instance, this object has defined [representationInsteadOfFeature] (meaning it is not `null`) and
     * the given [options] as well, then the resulting [FeatureOptions] will have the
     * [representationInsteadOfFeature] of [options].
     */
    fun merge(options: FeatureOptions<R>): FeatureOptions<R> =
        FeatureOptions(
            options.description ?: description,
            options.representationInsteadOfFeature ?: representationInsteadOfFeature
        )
}
