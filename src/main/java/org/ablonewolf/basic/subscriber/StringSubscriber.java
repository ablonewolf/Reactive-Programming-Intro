package org.ablonewolf.basic.subscriber;

import lombok.Getter;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class StringSubscriber implements Subscriber<String> {

    public static final Logger logger = LoggerFactory.getLogger(StringSubscriber.class);
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(String email) {
        logger.info("received: {}", email);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("error: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        logger.info("Completed");
    }
}
