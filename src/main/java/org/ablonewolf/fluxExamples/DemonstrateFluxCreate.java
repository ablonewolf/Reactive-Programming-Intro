package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import org.ablonewolf.fluxExamples.helper.CountryNameGenerator;
import reactor.core.publisher.Flux;

/**
 * The DemonstrateFluxCreate class showcases the creation and usage of a Flux
 * using the {@code Flux.create()} method, which allows for programmatically emitting items via a {@code FluxSink}.
 * This example demonstrates the integration of a custom {@code CountryNameGenerator} that generates
 * and streams random country names to a subscriber.
 * <p>
 * Features of this implementation include:<br>
 * - Creating a Flux instance using the {@code Flux.create()} method.<br>
 * - Using a {@code CountryNameGenerator} as a provider of country names, which emits items
 * to the Flux until a specified condition is met (emission of the name "Canada").<br>
 * - Subscribing to the Flux for consuming the stream of emitted country names using a
 * custom subscriber obtained from the {@code Util.subscriber()} method.<br>
 * - Introducing delay in the main thread execution using the {@code Util.sleepSeconds()} method to
 * allow the Flux to emit items before program termination.
 * <p>
 * Key concepts covered:<br>
 * - Manual emitting of data in a Flux using {@code FluxSink}.<br>
 * - Reactive Streams programming with data producers and consumers.<br>
 * - The role of backpressure in controlling the flow of data between a Flux publisher
 * and its subscribers.
 */
public class DemonstrateFluxCreate {

	public static void main(String[] args) {

		var countryNameGenerator = new CountryNameGenerator();
		var countryFlux = Flux.create(countryNameGenerator);

		countryFlux.subscribe(Util.subscriber("Country Subscriber"));

		countryNameGenerator.generate();

		Util.sleepSeconds(2L);

	}
}
