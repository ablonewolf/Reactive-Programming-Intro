package org.ablonewolf.backpressureHandlerOperators;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Utility class for generating a reactive stream of increasing integer values.
 * This class provides a custom implementation of a number generator
 * using Reactor's {@code Flux.generate} method for creating a sequence of numbers.
 * <p>
 * The generated numbers are produced on a parallel scheduler to ensure
 * asynchronous and concurrent processing.
 * The method also supports custom logging
 * for monitoring the values being emitted during generation.
 * <p>
 * Features:<br>
 * - The sequence starts with an initial state of 1.<br>
 * - Each emitted value is incremented sequentially.<br>
 * - Each generation step logs the generated value through the provided {@code Logger}.<br>
 * - The stream is configured to use a parallel scheduler for producing values.
 * <p>
 * Usage of this class often involves integration with backpressure handling mechanisms
 * or other reactive operators provided by Project Reactor.
 * <p>
 * Thread-safety:<br>
 * - This class is thread-safe due to its reliance on Reactor's reactive streams
 * and schedulers, which inherently manage concurrency.
 * <p>
 * Design:<br>
 * - This is a utility class and cannot be instantiated.
 */
public final class NumberGenerator {

	public static Flux<Integer> generateNumberProducer(Logger log) {
		return Flux.generate(
						() -> 1,
						(state, sink) -> {
							log.info("Generating value {}", state);
							sink.next(state);
							return ++state;
						})
				.cast(Integer.class)
				.subscribeOn(Schedulers.parallel());
	}

	public static Flux<Integer> getNumberProducerFromFluxCreate(Logger log) {
		return Flux.create(fluxSink -> {
					for (int i = 0; i < 500 && !fluxSink.isCancelled(); i++) {
						log.info("Generating value {}", i);
						fluxSink.next(i);
						Util.sleep(Duration.ofMillis(50));
					}
					fluxSink.complete();
				})
				.cast(Integer.class)
				.subscribeOn(Schedulers.parallel());
	}

	private NumberGenerator() {
	}

}
