package org.ablonewolf.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Utility class for creating reactive streams of integers and demonstrating reactive programming concepts
 * such as `subscribeOn` and `publishOn` operators with Project Reactor's Flux.
 * This class provides methods to generate Flux with specific threading behaviors, allowing subscribers
 * to observe different thread execution patterns.
 * <p>
 * The class is designed to emit a predefined sequence of integers and log events during the emission
 * and subscription process, showcasing reactive stream operations with threading models.
 * <p>
 * Key functionalities:<br>
 * - Provides a Flux {@code getNumberFluxSubscribedOn} with the subscription process executed asynchronously
 * using the {@code subscribeOn} operator.<br>
 * - Provides a Flux {@code getNumberFluxPublishOn} where elements are emitted on the caller thread but
 * transferred to a different thread pool for downstream processing using the {@code publishOn} operator.<br>
 * - Emits a predefined maximum set of integer values.<br>
 * - Logs subscriber notifications and thread pool transitions during stream execution.
 * <p>
 * This class is final to ensure that its utility methods and behaviors cannot be modified through inheritance.
 */
public final class NumberFlux {
	private static final Logger log = LoggerFactory.getLogger(NumberFlux.class);
	private static final int MAX_NUMBERS = 10;
	private static final String THREAD_POOL_MESSAGE = "Passing to a different Thread pool.";
	private static final String SUBSCRIBER_MESSAGE =
			"It will be printed inside the caller thread as now the subscriber will subscribe to the publisher";

	public static Flux<Integer> getNumberFluxSubscribedOn() {
		return createBaseNumberFlux()
				.subscribeOn(Schedulers.boundedElastic())
				.doFirst(NumberFlux::printSubscriberNotification);
	}

	public static Flux<Integer> getNumberFluxPublishOn() {
		return createBaseNumberFlux()
				.doFirst(NumberFlux::printSubscriberNotification)
				.publishOn(Schedulers.parallel());
	}

	private static Flux<Integer> createBaseNumberFlux() {
		return Flux.create(fluxSink -> {
					for (int i = 0; i < MAX_NUMBERS; i++) {
						log.info("Emitting number {}", i);
						fluxSink.next(i);
					}
					fluxSink.complete();
				})
				.cast(Integer.class)
				.doOnNext(value -> log.info("Number generated: {}", value))
				.doFirst(() -> log.info(THREAD_POOL_MESSAGE));
	}

	private NumberFlux() {
	}

	private static void printSubscriberNotification() {
		log.info(SUBSCRIBER_MESSAGE);
	}
}