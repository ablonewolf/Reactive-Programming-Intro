package org.ablonewolf.batching;

import org.ablonewolf.common.Util;
import org.ablonewolf.fluxExamples.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The DemonstrateWindow class showcases the use of the {@link Flux#window(int maxSize)} operator
 * in a reactive programming context.
 * The program processes a finite stream of country names and demonstrates batching the emitted items into
 * windows of a specific size for further processing.
 * <p>
 * Core functionality includes:<br>
 * - Generating a finite stream of country names using the
 * {@link NameGenerator#getFiniteCountryNames(Integer count)}} method.<br>
 * - Splitting emitted items into smaller windows of a specified size using the {@link Flux#window(int maxSize)}
 * operator.<br>
 * - Logging the creation of each new window as items are batched into it.<br>
 * - Delaying the processing of each window's emission to demonstrate asynchronous behavior using
 * {@link Flux#delayElements(Duration delay)}.<br>
 * - Flattening the windows into individual items for further transformations, such as converting country names to
 * uppercase.<br>
 * - Logging the total number of items processed when each window completes.<br>
 * - Subscribing to the processed stream using a
 * custom subscriber created via {@link Util#subscriber(String name)}}.<br>
 * - Introducing a delay at the end of the main thread using {@link Util#sleepSeconds(Long seconds)} to ensure all
 * asynchronous operations are processed and observable.
 */
public class DemonstrateWindow {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateWindow.class);

	public static void main(String[] args) {

		NameGenerator.getFiniteCountryNames(150)
				.window(20)
				.doOnNext(window -> log.info("New window created"))
				.delayElements(Duration.ofSeconds(2))
				.flatMap(DemonstrateWindow::processCountryNames)
				.subscribe(Util.subscriber("Country Subscriber"));

		Util.sleepSeconds(20L);
	}

	private static Flux<String> processCountryNames(Flux<String> countryNames) {
		AtomicInteger counter = new AtomicInteger();
		return countryNames
				.doOnNext(item -> counter.incrementAndGet())
				.doOnComplete(() -> log.info("Window completed with {} items", counter.get()))
				.flatMap(DemonstrateWindow::convertToUpperCaseCountryName);
	}

	private static Mono<String> convertToUpperCaseCountryName(String countryName) {
		return Mono.defer(() -> Mono.just(countryName.toUpperCase()));
	}
}
