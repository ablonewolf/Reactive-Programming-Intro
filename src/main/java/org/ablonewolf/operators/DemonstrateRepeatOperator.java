package org.ablonewolf.operators;

import org.ablonewolf.common.NameGenerator;
import org.ablonewolf.common.Util;

/**
 * The DemonstrateRepeatOperator class demonstrates the usage of the {@code repeat} operator
 * from Project Reactor to re-subscribe to a publisher under various scenarios.
 * It highlights how the {@code repeat} operator can be effectively utilized to control
 * resubscription behavior and avoid concurrent subscriptions.
 * <p>
 * The {@code repeat} operator ensures re-subscription to the publisher only after the
 * completion of the previous subscription. This is useful in scenarios where
 * controlled, sequential re-subscription is required instead of creating
 * multiple concurrent subscriptions using loops.
 * <p>
 * Key demonstrations include:<br>
 * 1. Re-subscribing to a publisher for a fixed number of times to process emitted values.<br>
 * 2. Using {@code repeat} in combination with the {@code takeUntil} operator to continue re-subscribing until
 * a specific condition is met (e.g., fetching a desired value).
 * <p>
 * Dependencies:<br>
 * - The class relies on the {@link NameGenerator} class to generate a Flux or Mono publisher
 * emitting country names.<br>
 * - The {@link Util} class is utilized for handling subscriber creation and simulating thread sleep.
 * <p>
 */
public class DemonstrateRepeatOperator {

	public static void main(String[] args) {

		var singleCountryPublisher = NameGenerator.getSingleCountryName();
		var countrySubscriber = Util.subscriber("Country Subscriber");

		// here, the repeat operator will resubscribe to the publisher only when the previous subscription is complete
		// we can achieve the same using for loop; however, doing so will create 10 concurrent subscriptions, that is
		// not what we want
		singleCountryPublisher.repeat(10)
				.subscribe(countrySubscriber);

		System.out.println();
		System.out.println();

		// here, we will be keep resubscribing to the publisher until we get a desired value.
		// likewise, this can be done using a loop; however, doing so will create several concurrent subscriptions
		// until the desired value is fetched.
		singleCountryPublisher.repeat()
				.takeUntil(country -> country.equalsIgnoreCase("Canada"))
				.subscribe(countrySubscriber);

		Util.sleepSeconds(2L);
	}
}
