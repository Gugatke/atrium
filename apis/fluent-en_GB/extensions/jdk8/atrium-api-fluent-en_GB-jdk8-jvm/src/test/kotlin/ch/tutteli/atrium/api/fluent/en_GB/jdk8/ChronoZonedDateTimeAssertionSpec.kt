package ch.tutteli.atrium.api.fluent.en_GB.jdk8

import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.specs.fun1
import ch.tutteli.atrium.specs.notImplemented
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.chrono.ChronoLocalDate
import java.time.chrono.ChronoZonedDateTime

class ChronoZonedDateTimeAssertionSpec : ch.tutteli.atrium.specs.integration.ChronoZonedDateTimeAssertionSpec(
    fun1(Expect<ZonedDateTime>::isBefore),
    fun1(Expect<ZonedDateTime>::isBeforeOrEquals),
    fun1(Expect<ZonedDateTime>::isAfter)
) {
    @Suppress("unused", "UNUSED_VALUE", /* TODO #289 remove again => */ "UNUSED_VARIABLE")
    private fun ambiguityTest() {
        val chronoZonedDateTime: ChronoZonedDateTime<*> = notImplemented()
        var a1: Expect<ChronoZonedDateTime<ChronoLocalDate>> = notImplemented()
        var a2: Expect<ChronoZonedDateTime<LocalDate>> = notImplemented()
        var a3: Expect<ChronoZonedDateTime<*>> = notImplemented()
        var a4: Expect<ZonedDateTime> = notImplemented()

        //TODO #289 we need to change the signature, it should not accept T but ChronoZonedDateTime<*>
//        a1 = a1.isBefore(ZonedDateTime.now())
//        a1 = a1.isBeforeOrEquals(ZonedDateTime.now())
//        a1 = a1.isAfter(ZonedDateTime.now())

        a2 = a2.isBefore(ZonedDateTime.now())
        a2 = a2.isBeforeOrEquals(ZonedDateTime.now())
        a2 = a2.isAfter(ZonedDateTime.now())

        //TODO #289 we need to change the signature, it should not accept T but ChronoZonedDateTime<*>
//        a3 = a3.isBefore(ZonedDateTime.now())
//        a3 = a3.isBeforeOrEquals(ZonedDateTime.now())
//        a3 = a3.isAfter(ZonedDateTime.now())

        a4 = a4.isBefore(ZonedDateTime.now())
        a4 = a4.isBeforeOrEquals(ZonedDateTime.now())
        a4 = a4.isAfter(ZonedDateTime.now())

        //TODO #289 this should actually be supported
//        a1 = a1.isBefore(chronoZonedDateTime)
//        a1 = a1.isBeforeOrEquals(chronoZonedDateTime)
//        a1 = a1.isAfter(chronoZonedDateTime)
//
//        a2 = a2.isBefore(chronoZonedDateTime)
//        a2 = a2.isBeforeOrEquals(chronoZonedDateTime)
//        a2 = a2.isAfter(chronoZonedDateTime)
//
//        a3 = a3.isBefore(chronoZonedDateTime)
//        a3 = a3.isBeforeOrEquals(chronoZonedDateTime)
//        a3 = a3.isAfter(chronoZonedDateTime)

//        a4 = a4.isBefore(chronoZonedDateTime)
//        a4 = a4.isBeforeOrEquals(chronoZonedDateTime)
//        a4 = a4.isAfter(chronoZonedDateTime)
    }
}