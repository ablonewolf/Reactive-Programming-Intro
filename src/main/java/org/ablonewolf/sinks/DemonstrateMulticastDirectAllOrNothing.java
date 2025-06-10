package org.ablonewolf.sinks;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

/**
 * The DemonstrateMulticastDirectAllOrNothing class demonstrates the usage of a multicast {@link Sinks.Many} with the
 * {@code directAllOrNothing} backpressure strategy from Project Reactor.
 * It highlights how this strategy ensures that an emitted item is delivered to all subscribers simultaneously — or
 * not at all.
 * If any subscriber cannot accept the item (e.g., due to being slow or full), the emission fails for all subscribers.
 * <p>
 * This behavior makes the {@code directAllOrNothing} strategy suitable for scenarios where consistency across
 * subscribers
 * is critical, and partial delivery is not acceptable. However, it may result in dropped items if any subscriber
 * lags behind.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a multicast sink using:
 * {@link Sinks#many()}.{@link Sinks.ManySpec#multicast()}.{@link Sinks.MulticastSpec#directAllOrNothing()}.<br>
 * 2. Subscribing a fast consumer (Sam) that receives all items when all subscribers are ready.<br>
 * 3. Subscribing a deliberately slow consumer (Jane) that introduces a delay using
 * {@link Flux#delayElements(Duration)},
 * demonstrating how it causes both subscribers to miss emissions.<br>
 * 4. Emitting a sequence of numbers and logging the result of each emission using
 * {@link Sinks.Many#tryEmitNext(Object)}.<br>
 * 5. Configuring system property {@code reactor.bufferSize.small} to control buffer size and better visualize the
 * effects of backpressure.
 * <p>
 * Dependencies:<br>
 * - Uses the {@link Util} class to create and configure subscribers with descriptive labels and custom logging.<br>
 * - Relies on SLF4J's {@link Logger} for logging emission results during execution.<br>
 * - Uses Java's {@link Duration} to simulate delayed processing for slow subscribers.<br>
 * - Leverages Reactor's {@link Flux} and {@link Sinks} API to model the reactive stream and manage manual emissions.
 * <p>
 */
public class DemonstrateMulticastDirectAllOrNothing {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateMulticastDirectAllOrNothing.class);

	static {
		System.setProperty("reactor.bufferSize.small", "24");
	}

	public static void main(String[] args) {
		// This would result in either all subscribers will receive items or neither of the subscribers will receive
		// the emitted item. If any of the subscribers is slow at receiving the emitted item, the other subscribers
		// won't receive the same item either.
		Sinks.Many<Integer> numberSink = Sinks.many().multicast().directAllOrNothing();

		Flux<Integer> numberFlux = numberSink.asFlux();

		numberFlux.subscribe(Util.subscriber("Sam", "number"));

		// we are deliberately delaying the emission of the items for this subscriber to visualize the slow processing
		// of a subscriber.
		// since this subscriber is slow, it may miss out most of the emitted items, and the other subscriber will
		// also miss out the same items.
		numberFlux.delayElements(Duration.ofMillis(100))
				.subscribe(Util.subscriber("Jane", "number"));

		for (int i = 1; i <= 50; i++) {
			Sinks.EmitResult result = numberSink.tryEmitNext(i);
			log.info("Emitted number {} with result {}", i, result);
		}

		Util.sleepSeconds(3L);
	}
}
