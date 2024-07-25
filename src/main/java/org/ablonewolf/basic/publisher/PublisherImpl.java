package org.ablonewolf.basic.publisher;

import com.github.javafaker.Faker;
import org.ablonewolf.basic.subscription.SubscriptionImpl;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class PublisherImpl implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        Subscription subscription = new SubscriptionImpl(subscriber, Faker.instance());
        subscriber.onSubscribe(subscription);
    }
}
