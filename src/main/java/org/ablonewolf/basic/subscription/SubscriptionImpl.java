package org.ablonewolf.basic.subscription;

import lombok.AllArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class SubscriptionImpl implements Subscription {

    public static final Logger logger = LoggerFactory.getLogger(SubscriptionImpl.class);
    private Subscriber<? super String> subscriber;

    @Override
    public void request(long l) {

    }

    @Override
    public void cancel() {

    }
}
