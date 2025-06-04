package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * demonstrated how to create a flux from an iterable object.
 */
public class DemonstrateFluxFromIterable {

    public static void main(String[] args) {

        var charFlux = Flux.fromIterable(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

        charFlux.subscribe(Util.subscriber("Character Subscriber"));
    }
}
