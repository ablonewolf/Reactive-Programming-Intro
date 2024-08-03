package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

public class DemonstrateMultipleSubscribers {

    public static void main(String[] args) {
        var integerFlux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        integerFlux.subscribe(Util.subscriber("Normal Integer Subscriber"));

        integerFlux.filter(num -> num % 2 != 0)
                .map(num -> num * num)
                .subscribe(Util.subscriber("Odd Number Subscriber"));

        integerFlux.filter(num -> num % 2 == 0)
                .map(num -> num * num * num)
                .subscribe(Util.subscriber("Even Number Subscriber"));
    }
}
