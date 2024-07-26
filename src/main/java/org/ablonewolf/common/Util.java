package org.ablonewolf.common;

import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

public class Util {

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static void main(String[] args) {
        Mono<Integer> integerMono = Mono.just(1);

        integerMono.subscribe(subscriber("First Integer Subscriber"));
        integerMono.subscribe(subscriber("Second Integer Subscriber"));
    }
}
