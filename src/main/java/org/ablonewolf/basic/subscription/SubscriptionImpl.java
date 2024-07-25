package org.ablonewolf.basic.subscription;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@RequiredArgsConstructor
public class SubscriptionImpl implements Subscription {

    public static final Logger logger = LoggerFactory.getLogger(SubscriptionImpl.class);
    public static final Integer MAX_ITEMS = 10;
    private final Subscriber<? super String> subscriber;
    private final Faker faker;
    private Boolean isCancelled;
    private Integer count = 0;

    @Override
    public void request(long requested) {
        if (isCancelled) {
            return;
        }
        logger.info("Subscriber has requested {} items", requested);
        for (int index = 0; index < requested && count < MAX_ITEMS; index++) {
            this.subscriber.onNext(this.faker.internet().emailAddress());
            ++count;
        }

        if (Objects.equals(count, MAX_ITEMS)) {
            logger.info("No more email address to produce");
            this.subscriber.onComplete();
            this.isCancelled = true;
        }
    }

    @Override
    public void cancel() {
        logger.info("Subscriber has cancelled");
        this.isCancelled = true;
    }
}
