package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import org.ablonewolf.common.CountryNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the thread-safety of using a FluxSink in a multithreaded environment.
 * <p>
 * The example creates a Flux based on a custom {@code CountryNameGenerator} that emits country names
 * using a FluxSink. The main thread and multiple worker threads interact with shared data structures
 * to explore thread-safety aspects.
 * <p>
 * Key concepts illustrated:<br>
 * - Reactive Streams with Flux and FluxSink for controlled data generation.<br>
 * - Thread-safety concerns of shared, non-synchronized collections like {@code ArrayList}.<br>
 * - Single-threaded consistency of FluxSink despite being accessed from multiple threads.
 * <p>
 * Implementation details include:<br>
 * - A Flux is created using {@code Flux.create()} and is linked to {@code CountryNameGenerator}.<br>
 * - The application spawns multiple threads that invoke methods on {@code CountryNameGenerator}
 * to generate country names and modify a shared {@code ArrayList}.<br>
 * - Thread-safety of the FluxSink-backed list is contrasted with the non-thread-safe {@code ArrayList}.
 * <p>
 * Observations:<br>
 * - The generated country name count in the FluxSink-backed list is consistent and deterministic,
 * demonstrating its thread-safe behavior.<br>
 * - The size of a non-synchronized {@code ArrayList} shared across multiple threads is inconsistent
 * due to thread-safety issues.
 * <p>
 * Utility components:<br>
 * - {@code CountryNameGenerator} is the generator emitting country names via FluxSink.<br>
 * - {@code Util.sleepSeconds()} is used to delay the main thread, allowing worker threads to execute.
 * <p>
 * This class showcases the benefits of using reactive programming principles for handling data generation
 * and processing in concurrent or multithreaded environments.
 */
public class DemonstrateThreadSafetyOfFluxSink {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateThreadSafetyOfFluxSink.class);

	public static void main(String[] args) {

		var countryNameGenerator = new CountryNameGenerator();
		var countryFlux = Flux.create(countryNameGenerator);

		List<String> countryNames = new ArrayList<>();
		List<Integer> numbers = new ArrayList<>();

		countryFlux.subscribe(countryNames::add);

		Runnable runnable = () -> {
			for (int i = 1; i <= 1000; i++) {
				countryNameGenerator.generateSingleName();
				numbers.add(i * 10);
			}
		};

		for (int i = 0; i < 10; i++) {
			Thread.ofPlatform().start(runnable);
		}

		Util.sleepSeconds(1L);

		// the country name size will be 10,000 since we used FluxSink to generate and add names to the list
		log.info("Country name size: {}", countryNames.size());

		// the size of the numbers' list won't be 10,000 since ArrayList itself isn't thread safe
		log.info("Numbers size: {}", numbers.size());
	}
}
