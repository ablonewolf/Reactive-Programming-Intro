package org.ablonewolf.common;

import org.reactivestreams.Subscriber;

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
}
