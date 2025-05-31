package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
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
				.transform(Util.getFluxLogger("Large Number Stream", log))
				.delayElements(Duration.ofMillis(10));
	}

	public static Flux<Integer> getMiniNumberStream(Logger log) {
		return Flux.just(1, 2)
				.transform(Util.getFluxLogger("Mini Number Stream", log))
				.delayElements(Duration.ofMillis(10));
	}

	public static Flux<Integer> getOddNumberStream(Logger log) {
		return Flux.generate(
						() -> 1,
						(currentNumber, sink) -> {
							while (currentNumber <= 100) {
								if (currentNumber % 2 == 1) {
									sink.next(currentNumber);
									return currentNumber + 1;
								}
								currentNumber++;
							}
							sink.complete();
							return currentNumber;
						})
				.cast(Integer.class)
				.transform(Util.getFluxLogger("Odd Number Stream", log))
				.delayElements(Duration.ofMillis(50));
	}

	public static Flux<Integer> getEvenNumberStream(Logger log) {
		return Flux.generate(
						() -> 1,
						(currentNumber, sink) -> {
							while (currentNumber <= 100) {
								if (currentNumber % 2 == 0) {
									sink.next(currentNumber);
									return currentNumber + 1;
								}
								currentNumber++;
							}
							sink.complete();
							return currentNumber;
						})
				.cast(Integer.class)
				.transform(Util.getFluxLogger("Even Number Stream", log))
				.delayElements(Duration.ofMillis(5));
	}

}
