package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * Demonstrates the usage of the `Flux.generate` method with a state parameter.
 * The program generates a Flux of country names and processes the emissions until a specific condition is met.
 * <p>
 * The key objective of the class is to illustrate how a stateful generator can be used with
 * the `Flux.generate` method to emit a sequence of items and terminate based on custom conditions.
 * <p>
 * Key Features:
 * - Utilizes `Flux.generate` for creating a Flux sequence of country names.
 * - Demonstrates how to manage a state variable (`counter`) within the generator logic.
 * - Emits country names using a random name generator until either a certain number
 * of items are emitted (50) or a specific condition is met (country name "Canada").
 * - Showcases the use of the `synchronousSink` to emit items or signal completion during flux generation.
 * - Utilize a custom subscriber provided by the `Util` class for consuming the generated items.
 */
public class DemonstrateFluxGenerateWithState {

	public static void main(String[] args) {

		Flux.generate(() -> 0,
						(counter, synchronousSink) -> {
							var countryName = Util.getFaker().country().name();
							synchronousSink.next(countryName);
							++counter;
							if (counter == 50 || countryName.equalsIgnoreCase("canada")) {
								synchronousSink.complete();
							}
							return counter;
						})
				.subscribe(Util.subscriber("Country Subscriber"));
	}
}
