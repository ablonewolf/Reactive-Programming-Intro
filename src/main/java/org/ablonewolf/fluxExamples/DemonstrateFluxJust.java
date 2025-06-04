package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * demonstrate how to create a Flux from the just method
 */
public class DemonstrateFluxJust {

    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
                .subscribe(Util.subscriber("Integer Sequence Subscriber"));
    }
}
