package org.ablonewolf.sinks;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

import java.time.Duration;

/**
 * The DemonstrateSinkMulticastDirectBestEffort class demonstrates the usage of a multicast {@link Sinks.Many} with
 * the {@code directBestEffort} backpressure strategy from Project Reactor.
 * It highlights how this strategy handles emissions when no subscriber is available or when subscribers are slow,
 * by failing fast instead of buffering items indefinitely.
 * <p>
 * The {@code directBestEffort} strategy is useful in scenarios where memory usage must be controlled, and it's
 * acceptable
 * to drop items if consumers cannot keep up with the emission rate. This makes it suitable for high-throughput systems
 * where best-effort delivery is preferred over unbounded buffering.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a multicast sink using:
 * {@link Sinks#many()}.{@link Sinks.ManySpec#multicast()}.{@link Sinks.MulticastSpec#directBestEffort()}.<br>
 * 2. Subscribing a fast consumer (Sam) that receives all emitted items without delay.<br>
 * 3. Subscribing a slow consumer (Jane) that processes items with a delay, demonstrating how it may miss items due
 * to lack of buffering.<br>
 * 4. Demonstrating a buffered version of the same slow consumer (Bob) that uses {@link Flux#onBackpressureBuffer()}
 * to avoid missing items.<br>
 * 5. Emitting a sequence of numbers and logging the result of each emission using
 * {@link Sinks.Many#tryEmitNext(Object)}.<br>
 * 6. Configuring system property {@code reactor.bufferSize.small} to control buffer size for better demonstration of
 * the behavior.
 * <p>
 * Dependencies:<br>
 * - Uses the {@link Util} class to create and configure subscribers with descriptive labels and custom logging.<br>
 * - Relies on SLF4J's {@link Logger} for logging emission results during execution.<br>
 * - Uses Java's {@link Duration} to simulate delayed processing for slow subscribers.<br>
 * - Leverages Reactor's {@link Flux} and {@link Sinks} API to model the reactive stream and manual emissions.
 * <p>
 */
public class DemonstrateSinkMulticastDirectBestEffort {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateSinkMulticastDirectBestEffort.class);

	static {
		System.setProperty("reactor.bufferSize.small", "24");
	}

	public static void main(String[] args) {
		// Unlike "onBackpressureBuffer", the directBestEffort will focus on the fast subscriber and will fail fast
		// on the tryEmitNext if no subscribers available. This is ideal for cases where we have a limited buffer
		// size and some of our subscribers are slow at their receiving end.
		Sinks.Many<Integer> numberSink = Sinks.many().multicast().directBestEffort();

		Flux<Integer> numberFlux = numberSink.asFlux();

		numberFlux.subscribe(Util.subscriber("Sam", "number"));

		// we are deliberately delaying the emission of the items for this subscriber to visualize the slow processing
		// of a subscriber.
		// since this subscriber is slow, it may miss out most of the emitted items.
		numberFlux.delayElements(Duration.ofMillis(100))
				.subscribe(Util.subscriber("Jane", "number"));

		// If we don't want the slow subscriber to miss out any items that were emitted fast, we can adopt the
		// backpressure strategy and notify the publisher to buffer the items when the subscriber is slow at
		// receiving the items. The following pipeline demonstrates such one case.
		numberFlux.onBackpressureBuffer()
				.delayElements(Duration.ofMillis(100))
				.subscribe(Util.subscriber("Bob", "number"));

		for (int i = 1; i <= 50; i++) {
			EmitResult result = numberSink.tryEmitNext(i);
			log.info("Emitted number {} with result {}", i, result);
		}

		Util.sleepSeconds(10L);
	}
}
