package org.ablonewolf.batchingOperators;

import org.ablonewolf.common.Util;
import org.ablonewolf.common.NameGenerator;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Demonstrates the bufferTimeout operator in a reactive programming context.
 * The program generates and processes a finite stream of country names using buffering techniques,
 * showcasing the ability to group emitted items over a specific period or item count.
 * <p>
 * This class demonstrates:<br>
 * - Generating a finite stream of country names using {@link NameGenerator#getFiniteCountryNames(Integer)}.<br>
 * - Applying the {@link Flux#bufferTimeout(int, Duration)} operator to collect emitted items either by count (9
 * items) or within a specified time window (900 milliseconds), whichever condition is met first.<br>
 * - Subscribing to the resulting buffered stream using a subscriber created with {@link Util#subscriber(String)}.<br>
 * - Introducing a delay with {@link Util#sleepSeconds(Long)} to ensure the main thread observes the completion
 * of emissions from the stream.
 */
public class DemonstrateBufferTimeout {

	public static void main(String[] args) {
		NameGenerator.getFiniteCountryNames(100)
				.bufferTimeout(9, Duration.ofMillis(900))
				.subscribe(Util.subscriber("Country Subscriber"));

		Util.sleepSeconds(11L);
	}
}
