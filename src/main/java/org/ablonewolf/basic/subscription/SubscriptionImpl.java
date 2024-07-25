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
    private Boolean isCancelled = false;
    private Integer count = 0;

    @Override
    public void request(long requested) {
        if (isCancelled) {
            return;
        }

        if (requested > 0 && requested <= MAX_ITEMS) {
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
        } else {
            if (requested > MAX_ITEMS) {
                this.subscriber.onError(new RuntimeException("Number of requested items exceeds maximum allowed."));
                this.isCancelled = true;
            } else if (requested < 0) {
                this.subscriber.onError(new RuntimeException("Number of requested items cannot be negative."));
                this.isCancelled = true;
            } else {
                logger.info("Subscriber has asked for zero items. Therefore completing the subscription");
                this.subscriber.onComplete();
                this.isCancelled = true;
            }
        }

    }

    @Override
    public void cancel() {
        logger.info("Subscriber has cancelled");
        this.isCancelled = true;
    }
}
