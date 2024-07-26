package org.ablonewolf.reactiveStreams;

import org.ablonewolf.basic.subscriber.SubscriberImpl;
import reactor.core.publisher.Mono;

/**
 * demonstrating the use of the just method of Mono
 */
public class DemonstrateMonoJust {
    public static void main(String[] args) {
        var mono = Mono.just("A demo string");
        var subscriber = new SubscriberImpl();

        mono.subscribe(subscriber);

        subscriber.getSubscription().request(5);

        // this won't be executed since mono has already completed executing the request. It only emits once.
        subscriber.getSubscription().request(5);
        subscriber.getSubscription().cancel();
    }
}
