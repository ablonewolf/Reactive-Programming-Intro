package org.ablonewolf.batchingOperators;

import org.ablonewolf.common.Util;
import org.ablonewolf.common.NameGenerator;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Demonstrates the use of buffering mechanisms in a reactive programming context
 * by consuming a finite stream of country names and applying buffering techniques
 * to group emitted items over time or based on count.
 * <p>
 * The demonstration includes:<br>
 * - Generating a finite stream of country names using the {@link NameGenerator#getFiniteCountryNames} method.<br>
 * - Applying two levels of buffering:<br>
 * 1. Collecting items over a specified buffering time window using {@link  Flux#buffer(Duration)}}.<br>
 * 2. Collecting items into groups with max sizes using {@link Flux#buffer(int)}.<br>
 * - Subscribing to the stream with a named subscriber to process and observe emissions.<br>
 * - Introducing a delay at the end of processing using {@link Util#sleepSeconds(Long)}} to ensure
 * emissions are completed and observable during execution.
 */
public class DemonstrateBuffer {

	public static void main(String[] args) {
		NameGenerator.getFiniteCountryNames(150)
				.buffer(Duration.ofMillis(300))
				.buffer(10)
				.subscribe(Util.subscriber("Country Subscriber"));

		Util.sleepSeconds(30L);
	}
}