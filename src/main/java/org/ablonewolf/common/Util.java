package org.ablonewolf.common;

import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

public class Util {

    /**
     * method for returning a new instance of default subscriber
     *
     * @param name: the name of the subscriber assigned by the publisher
     * @return default subscriber
     */
    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static void main(String[] args) {
        Mono<Integer> integerMono = Mono.just(1);

        integerMono.subscribe(subscriber("First Integer Subscriber"));
        integerMono.subscribe(subscriber("Second Integer Subscriber"));
    }
}
