package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
 * - Defines a custom implementation of `Consumer<FluxSink<String>>` called `CountryNameGenerator`
 * that emits country names to a `Flux` stream.
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

	/**
	 * This class is responsible for generating a stream of country names using a Reactive Streams paradigm.
	 * It implements the Consumer interface to accept a FluxSink instance for emitting items to a Flux.
	 */
	private static class CountryNameGenerator implements Consumer<FluxSink<String>> {

		private FluxSink<String> fluxSink;

		@Override
		public void accept(FluxSink<String> fluxSink) {
			this.fluxSink = fluxSink;
		}

		/**
		 * Generates a stream of random country names and emits them to the associated fluxSink.
		 * The generation continues until the name "Canada" (case-insensitive) is generated,
		 * at which point the fluxSink completes.
		 * <p>
		 * This method uses the `Util.getFaker()` utility to generate random country names.
		 * Each generated country name is passed to the FluxSink instance via its `next` method.
		 * Upon emitting "Canada", the `complete` method of the fluxSink is invoked to
		 * signal the completion of the stream.
		 */
		public void generate() {
			String countryName;
			do {
				countryName = Util.getFaker().country().name();
				this.fluxSink.next(countryName);
			} while (!countryName.equalsIgnoreCase("canada"));

			this.fluxSink.complete();
		}

		/**
		 * Generates a single country name using the `Util.getFaker()` utility and emits it
		 * to the associated FluxSink.
		 * The generated country name is sent via the `next` method
		 * of the FluxSink.
		 * <p>
		 * This method is useful for emitting a single country name to a Flux stream created
		 * using the Reactive Streams paradigm.
		 */
		public void generateSingleName() {
			String countryName = Util.getFaker().country().name();
			this.fluxSink.next(countryName);
		}
	}
}
