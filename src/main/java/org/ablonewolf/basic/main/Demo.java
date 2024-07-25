package org.ablonewolf.basic.main;

import org.ablonewolf.basic.publisher.PublisherImpl;
import org.ablonewolf.basic.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * 1. publisher does not produce data unless subscriber requests for it
 * 2. publisher will only produce subscriber requested items, publisher can also produce zero number of items
 * 3. subscriber can cancel the subscription; publisher should stop at that moment as subscriber is no longer
 * interested in consuming the data
 * 4. publisher can send the error signal to indicate something is wrong
 */
public class Demo {
    private static SubscriberImpl subscriber;
    public static final Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        try {
            demonstrateNormalSubscriptionProcess();

            demonstrateWhenSubscriberCancelsTheOperation();

            demonstrateWhenPublisherThrowsAnError();
        } catch (InterruptedException e) {
            logger.error("The main thread has been interrupted due to this error: {}", e.getMessage());
        }

        demonstrateWhenSubscriberAsksForZeroItems();
    }

    /**
     * method to initiate the publisher and subscriber instances
     * and initiate the subscription between the two
     */
    private static void initiateSubscription() {
        PublisherImpl publisher = new PublisherImpl();
        subscriber = new SubscriberImpl();

        publisher.subscribe(subscriber);
    }

    /**
     * method to demonstrate a normal subscription between publisher and subscriber
     */
    private static void demonstrateNormalSubscriptionProcess() throws InterruptedException {
        initiateSubscription();

        logger.info("Inside the method where there will be a normal subscription process");
        // getting subscription and requesting items through the subscription
        // also Invoking the sleep method to invoke an interval
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
    }

    private static void demonstrateWhenSubscriberAsksForZeroItems() {
        initiateSubscription();
        logger.info("Inside the method where the subscriber is asking for zero items.");
        subscriber.getSubscription().request(0);
    }

    /**
     * method to demonstrate when subscriber cancels the subscription process
     */
    private static void demonstrateWhenSubscriberCancelsTheOperation() throws InterruptedException {
        initiateSubscription();

        logger.info("Inside the method where subscriber will cancel the subscription.");
        // getting subscription and requesting items through the subscription
        // also Invoking the sleep method to invoke an interval
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
        // canceling the whole request process
        subscriber.getSubscription().cancel();
        Thread.sleep(Duration.ofSeconds(3));
        // this code block won't be reached
        subscriber.getSubscription().request(3);
        subscriber.getSubscription().request(3);
    }

    /**
     * method to demonstrate when the publisher throws an error
     */
    private static void demonstrateWhenPublisherThrowsAnError() throws InterruptedException {
        initiateSubscription();

        logger.info("Inside the method where publisher will throw an error.");
        // getting subscription and requesting items through the subscription
        // also Invoking the sleep method to invoke an interval
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
        subscriber.getSubscription().request(12);
        Thread.sleep(Duration.ofSeconds(3));

        subscriber.getSubscription().request(4);
    }
}
