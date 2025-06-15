package org.ablonewolf.tests;

import org.ablonewolf.common.Util;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * The TestFlux class demonstrates how to write unit tests for Reactor's {@link Flux} publishers using the
 * {@link StepVerifier}.
 * It includes multiple test methods that verify different aspects of flux emissions such as specific values,
 * element count, and conditional assertions.
 * <p>
 * This class showcases various assertion techniques available in StepVerifier, including expecting specific values,
 * counting elements, matching conditions, and canceling verification early.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a range-based flux using {@link Flux#range(int, int)} and applying logging.<br>
 * 2. Testing the emission of a single expected value with {@link StepVerifier.Step#expectNext(Object)}.<br>
 * 3. Verifying a sequence of known values using chained {@code expectNext} calls.<br>
 * 4. Asserting a group of consecutive items using array-based {@code expectNext}.<br>
 * 5. Validating total element count with {@link StepVerifier.Step#expectNextCount(long)}.<br>
 * 6. Using {@link StepVerifier.Step#expectNextMatches(java.util.function.Predicate)} to assert dynamic value
 * constraints.<br>
 * 7. Consuming all elements conditionally with
 * {@link StepVerifier.Step#thenConsumeWhile(java.util.function.Predicate)}.<br>
 * 8. Canceling verification early or allowing full completion based on test intent.
 * <p>
 * Dependencies:<br>
 * - Uses the {@link Util} class to generate random numbers during transformation.<br>
 * - Relies on JUnit Jupiter API for defining and running test cases.<br>
 * - Leverages Reactor's {@link StepVerifier} and {@link Flux} API to test reactive sequences declaratively.
 * <p>
 */
public class TestFlux {

	private Flux<Integer> getNumbers() {
		return Flux.range(1, 50)
				.log();
	}

	private Flux<Integer> getRandomNumbers() {
		return Flux.range(1, 50)
				.map(num -> num * Util.getFaker().random().nextInt(1, 10));
	}

	@Test
	public void test_ifFirstItemIsOne() {
		StepVerifier.create(getNumbers(), 1)
				.expectNext(1)
				.thenCancel()
				.verify();
	}

	@Test
	public void test_ifFirstThreeItemsMatch() {
		StepVerifier.create(getNumbers(), 3)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.thenCancel()
				.verify();
	}

	@Test
	public void test_ifAllItemsAreEmitted() {
		StepVerifier.create(getNumbers(), 10)
				.expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.thenCancel()
				.verify();
	}

	@Test
	public void test_ifNumberCountMatches() {
		StepVerifier.create(getNumbers(), 50)
				.expectNext(1)
				.expectNextCount(49)
				.expectComplete()
				.verify();
	}

	@Test
	public void test_ifImmediateRandomNumberIsPositiveAndWithinRange() {
		StepVerifier.create(getRandomNumbers())
				.expectNextMatches(num -> num > 0 && num <= 500)
				.thenCancel()
				.verify();
	}

	@Test
	public void test_ifAllRandomNumbersArePositiveAndWithinRange() {
		StepVerifier.create(getRandomNumbers())
				.thenConsumeWhile(num -> num > 0 && num <= 500)
				.expectComplete()
				.verify();
	}
}
