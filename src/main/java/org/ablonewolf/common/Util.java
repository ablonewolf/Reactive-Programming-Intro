package org.ablonewolf.common;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.reactivestreams.Subscriber;

public class Util {

    /**
     * using a Faker instance to generate random fake names for various items
     */
    @Getter
    private static final Faker faker = Faker.instance();
    /**
     * method for returning a new instance of default subscriber
     *
     * @param name: the name of the subscriber assigned by the publisher
     * @return default subscriber
     */
    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

}
