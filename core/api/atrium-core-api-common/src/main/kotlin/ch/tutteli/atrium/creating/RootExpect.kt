package ch.tutteli.atrium.creating

/**
 * Represents the root of an [Expect] chain, intended as extension point for functionality
 * which should only be available on the root.
 *
 * It exposes the [config] in contrast to [Expect].
 */
interface RootExpect<T> : Expect<T> {
//    /**
//     * The chosen [RootExpectConfig].
//     */
//    @Suppress("DEPRECATION" /* OptIn is only available since 1.3.70 which we cannot use if we want to support 1.2 */)
//    @UseExperimental(ExperimentalExpectConfig::class)
//    //TODO move to AssertionContainer
//    val config: RootExpectConfig
}
