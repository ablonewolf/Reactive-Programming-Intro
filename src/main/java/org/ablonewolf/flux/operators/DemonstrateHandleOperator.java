package org.ablonewolf.flux.operators;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * Demonstrates the usage of the Reactor `handle` operator by applying custom logic
 * to process and emit elements in a reactive stream.
 * The class showcases two main examples:
 * 1. Transforming a range of integers based on conditional logic using the `handle` operator.<br>
 * 2. Generating country names and using the `handle` operator to filter and complete
 *    the stream based on specific criteria.<br>
 * <p>
 * Key components:
 * - `Flux.range()`: Generates a range of integers to be processed.<br>
 * - `Flux.generate()`: Generates synchronous country names for processing.<br>
 * - `handle()`: Allows custom logic to be applied, controlling how items are emitted or filtered.<br>
 * - `cast()`: Casts elements in the stream to a specific type as needed.<br>
 * - `Util.subscriber(String)`: Subscribes to the stream with a named subscriber for monitoring and logging.<br>
 */
public class DemonstrateHandleOperator {

	public static void main(String[] args) {

		Flux.range(1, 50)
				.handle((item, sink) -> {
					switch (item % 2) {
						case 0 -> sink.next(item * item);
						case 1 -> sink.next(item * 3);
						default -> sink.complete();
					}
				})
				.cast(Integer.class)
				.subscribe(Util.subscriber("Integer Subscriber"));

		generateCountryName();
	}

	private static void generateCountryName() {
		Flux.generate(synchronousSink -> {
					var countryName = Util.getFaker().country().name();
					synchronousSink.next(countryName);
				})
				.cast(String.class)
				.handle((item, synchronousSink) -> {
					if (item.equalsIgnoreCase("canada")) {
						synchronousSink.next(item);
						synchronousSink.complete();
					} else {
						synchronousSink.next(item);
					}
				})
				.subscribe(Util.subscriber("Country Subscriber"));
	}
}
