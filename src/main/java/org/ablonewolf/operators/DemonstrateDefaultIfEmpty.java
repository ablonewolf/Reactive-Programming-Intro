package org.ablonewolf.operators;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Demonstrates the usage of Reactor's "defaultIfEmpty" operator in both Flux and Mono streams.
 * <p>
 * The "defaultIfEmpty" operator provides a default value in case the upstream source is empty.
 * This class includes examples to showcase how "defaultIfEmpty" can be applied to reactive streams
 * to emit a fallback value when no elements are emitted by the initial publisher.
 * <p>
 * Key components:<br>
 * - `Flux.range(start, count)`: Generates a sequence of integers with the specified range.<br>
 * - `filter(Predicate)`: Filters elements in the stream based on the provided condition.<br>
 * - `defaultIfEmpty(value)`: Emits the specified default value if the source does not emit any elements.<br>
 * - `Mono.empty()`: Creates an empty Mono stream.<br>
 * - `subscribe(subscriber)`: Subscribes to the stream using a custom subscriber that processes emitted data.<br>
 * - `Util.subscriber(String)`: A utility method to create a subscriber with a specified name for logging purposes.
 */
public class DemonstrateDefaultIfEmpty {

	public static void main(String[] args) {

		Flux.range(1, 0)
				.filter(num -> num > 12)
				.defaultIfEmpty(12)
				.subscribe(Util.subscriber("Number Subscriber"));

		Mono.empty()
				.defaultIfEmpty("default value")
				.subscribe(Util.subscriber("String Subscriber"));
	}
}
