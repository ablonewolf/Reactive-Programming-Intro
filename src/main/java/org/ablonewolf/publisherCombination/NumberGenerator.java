package org.ablonewolf.publisherCombination;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Utility class for generating streams of integer numbers as reactive sequences.
 * The class provides static methods to generate different types of number streams.
 * This class is final and cannot be instantiated.
 */
public final class NumberGenerator {

	private NumberGenerator() {
	}

	public static Flux<Integer> getNumberStream(Logger log) {
		return Flux.create(sink -> {
					for (int i = 3; i <= 100; i++) {
						sink.next(i);
					}
					sink.complete();
				})
				.cast(Integer.class)
				.doOnSubscribe(subscription -> log.info("Subscribing to Large number stream"))
				.delayElements(Duration.ofMillis(10));
	}

	public static Flux<Integer> getMiniNumberStream(Logger log) {
		return Flux.just(1, 2)
				.doOnSubscribe(subscription -> log.info("Subscribing to Mini number stream"))
				.delayElements(Duration.ofMillis(10));
	}
}
