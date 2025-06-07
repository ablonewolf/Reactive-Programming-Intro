package org.ablonewolf.operators;

import org.ablonewolf.common.NameGenerator;
import org.ablonewolf.common.Util;

import java.time.Duration;

/**
 * The DemonstrateRepeatWhenOperator class demonstrates the usage of the {@code repeatWhen} operator
 * from Project Reactor to re-subscribe to a publisher with a controlled delay between each resubscription.
 * It highlights how the {@code repeatWhen} operator can be effectively used to introduce a delay
 * before re-subscribing, which is particularly useful in scenarios such as retrying operations
 * after failures or polling at fixed intervals.
 * <p>
 * Unlike the basic {@code repeat} operator, which immediately re-subscribes upon completion,
 * the {@code repeatWhen} allows customizing the timing and conditions of resubscription by
 * transforming the signal stream. In this example, it delays each re-subscription by a specified duration.
 * <p>
 * Key demonstrations include:<br>
 * 1. Re-subscribing to a publisher repeatedly using {@code repeatWhen} with a fixed delay between attempts.<br>
 * 2. Combining {@code repeatWhen} with the {@code takeUntil} operator to continue resubscribing until
 * a specific condition is met (e.g., fetching a desired value like "Germany").
 * <p>
 * Dependencies:<br>
 * - The class relies on the {@link NameGenerator} class to generate a Flux or Mono publisher
 * emitting country names.<br>
 * - The {@link Util} class is utilized for handling subscriber creation, logging, and simulating thread sleep.<br>
 * - Uses {@link Duration} to define the delay interval between resubscriptions.
 * <p>
 */
public class DemonstrateRepeatWhenOperator {

	public static void main(String[] args) {
		var singleCountryPublisher = NameGenerator.getSingleCountryName();
		var countrySubscriber = Util.subscriber("Country Subscriber");

		// In the following pipeline, the "repeatWhen" operator is used to resubscribe to the country publisher
		// with a finite amount of delay. It acts like the "repeat" operator; however, the only difference is that the
		// repeat of the subscription is delayed by a certain duration.
		singleCountryPublisher
				.repeatWhen(countryFlux -> countryFlux.delayElements(Duration.ofMillis(50)))
				.takeUntil(country -> country.equalsIgnoreCase("Germany"))
				.subscribe(countrySubscriber);

		Util.sleepSeconds(10L);
	}
}
