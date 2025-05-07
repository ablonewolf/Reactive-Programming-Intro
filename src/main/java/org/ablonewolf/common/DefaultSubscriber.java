package org.ablonewolf.common;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class DefaultSubscriber<T> implements Subscriber<T> {

    public static final Logger logger = LoggerFactory.getLogger(DefaultSubscriber.class);
    private final String name;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T item) {
        logger.info("{} received item {}.", this.name, item);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("{} faced an error, error message is: '{}'.", this.name, throwable.getMessage());
    }

    @Override
    public void onComplete() {
        logger.info("{} completed receiving requests.", this.name);
    }
}
