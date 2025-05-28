package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import org.ablonewolf.flux.helper.CountryNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the creation of a Flux stream using the `create` method.
 * The program emits a stream of country names, handles subscriptions to the Flux,
 * and generates country names until a specific condition is reached.
 * <p>
 * The main purpose of the class is to showcase Flux creation with a custom implementation of the
 * Reactive Streams' `Consumer` for handling the emission of data points.
 * The example also demonstrates concurrent execution and subscription handling in a
 * Reactive Streams context using FluxSink.
 * <p>
 * Key Features:
 * - Utilizes the `Util` class for utility methods like generating random data, providing a subscriber,
 * and managing thread sleep operations.
 * - Demonstrates concurrent operations using threads for generating additional data.
 * - Logs the number of country names processed during program execution.
 */
public class DemonstrateFluxCreate {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateFluxCreate.class);

	public static void main(String[] args) {

		var countryNameGenerator = new CountryNameGenerator();
		var countryFlux = Flux.create(countryNameGenerator);

		countryFlux.subscribe(Util.subscriber("Country Subscriber"));

		countryNameGenerator.generate();

		List<String> countryNames = new ArrayList<>();

		countryFlux.subscribe(countryNames::add);

		Runnable runnable = () -> {
			for (int i = 0; i < 1000; i++) {
				countryNameGenerator.generateSingleName();
			}
		};

		for (int i = 0; i < 10; i++) {
			Thread.ofPlatform().start(runnable);
		}

		Util.sleepSeconds(5L);

		log.info("Country name size: {}", countryNames.size());

	}
}
