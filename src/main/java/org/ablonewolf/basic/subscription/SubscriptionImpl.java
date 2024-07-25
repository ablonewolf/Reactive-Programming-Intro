package org.ablonewolf.basic.subscription;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    public static final Logger logger = LoggerFactory.getLogger(SubscriptionImpl.class);

    @Override
    public void request(long l) {

    }

    @Override
    public void cancel() {

    }
}
