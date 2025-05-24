package org.ablonewolf.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * A utility class to provide a reactive stream of integers using Project Reactor's Flux.
 * <p>
 * The {@code NumberFlux} class demonstrates the usage of a Flux that emits a range of integers
 * and applies a variety of reactive operations, including scheduling, logging, and event hooks.
 * It facilitates understanding thread behavior, event order, and publisher-subscriber interactions
 * within a reactive programming context.
 * <p>
 * Features:<br>
 * - Emission of integers from 0 to 9 using {@code Flux.create}.<br>
 * - Logs emitted values and timestamps at various stages of the flux creation and processing pipeline.<br>
 * - Uses {@code Schedulers.boundedElastic()} for asynchronous execution of subscription logic.<br>
 * - Includes {@code doFirst} hooks to log messages in the caller thread before subscription and
 * before switching to the specified thread pool.<br>
 * - Supports event-driven callbacks with {@code doOnNext} to handle elements as they are emitted.
 * <p>
 * This class is immutable and serves as a utility; it is not designed to be instantiated.
 */
public final class NumberFlux {

	private static final Logger log = LoggerFactory.getLogger(NumberFlux.class);

	public static Flux<Integer> getNumberFlux() {
		return Flux.create(fluxSink -> {
					for (int i = 0; i < 10; i++) {
						log.info("Emitting number {}", i);
						fluxSink.next(i);
					}
					fluxSink.complete();
				})
				.cast(Integer.class)
				.doOnNext(value -> log.info("Number generated: {}", value))
				.doFirst(() -> log.info("Passing to a different Thread pool."))
				.subscribeOn(Schedulers.boundedElastic())
				.doFirst(() -> log.info("It will be printed inside the caller thread " +
						"as now the subscriber will subscribe to the publisher"));
	}

	private NumberFlux() {
	}
}
