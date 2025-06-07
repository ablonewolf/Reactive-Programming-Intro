package org.ablonewolf.batchingOperators;

import org.ablonewolf.common.NumberGenerator;
import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Demonstrates the usage of grouping operations provided by Reactor's Flux API.
 * <p>
 * This class creates a finite stream of numbers and groups them based on a condition (e.g., even or odd).
 * Each group is processed separately in a reactive manner to log the numbers and their classification.
 * <p>
 * Key operations include:<br>
 * - Generating a finite stream of numbers using
 * {@link  NumberGenerator#getFiniteNumberOfStream(Logger log, Integer maxNumber)}<br>
 * - Delaying elements in the stream to simulate asynchronous data flow.<br>
 * - Grouping the numbers based on a key (e.g., even or odd).<br>
 * - Processing each group using {@code processNumbers} method, which logs the classification of numbers in each group.
 * <p>
 * The application simulates a reactive application and ensures all operations are logged for demonstration purposes.
 */
public class DemonstrateGroupedFlux {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateGroupedFlux.class);

	public static void main(String[] args) {
		var numberFlux = NumberGenerator.getFiniteNumberOfStream(log, 100);

		numberFlux.delayElements(Duration.ofMillis(200))
				.groupBy(number -> number % 2)
				.flatMap(DemonstrateGroupedFlux::processNumbers)
				.subscribe();

		Util.sleepSeconds(25L);
	}

	private static Mono<Void> processNumbers(GroupedFlux<Integer, Integer> groupedFlux) {
		Boolean isEven = groupedFlux.key() % 2 == 0;
		String indicator = isEven ? "even" : "odd";
		log.info("Flux has been created for {} numbers", indicator);
		return groupedFlux.doOnNext(number -> log.info("{} is {}", number, indicator))
				.then();
	}
}
