package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Objects;

/**
 * Demonstrates the creation of a Flux stream using the `generate` method.
 * This program showcases how to use a synchronous sink to emit values, ensuring thread-safe
 * and stateful emission of data. The stream generation stops when a specific condition is met.
 * <p>
 * Key Features:
 * - Utilizes the `generate` method of Flux to emit one item at a time through a synchronous sink.
 * - Demonstrates the use of `takeUntil` to terminate the Flux stream upon matching a specified condition.
 * - Logs each invocation of the generation process through the logger for better traceability.
 * - Leverages utility methods for generating random data and handling consumption of the data stream.
 * <p>
 * Usage Scenario:
 * - Suitable for cases where a controlled, synchronous generation of stream data is required,
 * especially when stopping the stream upon a specific condition.
 * <p>
 * Dependencies:
 * - Requires the `Util` class for random name generation and subscription handling.
 * - Uses `Faker` for generating random country names.
 */
public class DemonstrateFluxGenerate {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateFluxGenerate.class);

	public static void main(String[] args) {

		// synchronous sink only emits one item at a time for stateful and thread-safe emission
		Flux.generate(synchronousSink -> {
					log.info("Flux generate method invoked.");
					var countryName = Util.getFaker().country().name();
					synchronousSink.next(countryName);
				})
				.takeUntil(countryName -> Objects.equals(countryName, "Canada"))
				.subscribe(Util.subscriber("Country Subscriber"));
	}
}
