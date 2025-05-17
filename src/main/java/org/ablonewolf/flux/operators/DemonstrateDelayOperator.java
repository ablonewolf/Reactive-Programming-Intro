package org.ablonewolf.flux.operators;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * This class demonstrates the usage of Reactor's "delayElements" operator to delay the emission
 * of elements in a reactive stream by a specified duration.
 * <p>
 * The functionality involves creating a Flux stream of integers, applying a delay to each
 * emitted item, and logging the elements as they are processed using a custom subscriber.
 * <p>
 * Key components:<br>
 * - `Flux.range()`: Generates a range of integers to be processed.<br>
 * - `delayElements(Duration)`: Introduces a delay for each element in the sequence.<br>
 * - `log()`: Logs the events occurring within the Flux stream for debugging or monitoring purposes.<br>
 * - `Util.subscriber(String)`: Subscribes to the stream with a custom subscriber that logs received data.<br>
 * - `Util.sleepSeconds(Long)`: Halts the main thread to ensure the program waits for the asynchronous
 * processing to complete.<br>
 */
public class DemonstrateDelayOperator {

	public static void main(String[] args) {

		Flux.range(1, 10)
				.log()
				.delayElements(Duration.ofMillis(100))
				.subscribe(Util.subscriber("Integer Subscriber"));

		Util.sleepSeconds(12L);
	}
}
