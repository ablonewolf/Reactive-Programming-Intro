package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

/**
 * demonstrate how to create a flux from stream
 * a stream can only be operated once; upon operation; it will be closed.
 * therefore, a flux of stream can only be subscribed only once.
 */
public class DemonstrateFluxFromStream {

    public static void main(String[] args) {

        var stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        var integerStreamFlux = Flux.fromStream(stream);
        integerStreamFlux.subscribe(Util.subscriber("Integer Stream Flux Subscriber 1"));

        // this will cause an error; stream can be operated only once; therefore, this subscription won't work
        integerStreamFlux.subscribe(Util.subscriber("Integer Stream Flux Subscriber 2"));
    }
}
