package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * demonstrated how to use the Flux range function
 */
public class DemonstrateFluxRange {
    public static void main(String[] args) {
        // this will create a flux of numbers from 3 to 12; the first param is starting point, the next param is the
        // count
        var fluxRange = Flux.range(3, 10);

        fluxRange.subscribe(Util.subscriber("Integer Subscriber"));

        // generating 10 random names
        Flux.range(1, 10)
                .map(index -> Util.getFaker().name().firstName())
                .subscribe(Util.subscriber("FirstName Subscriber"));
    }
}
