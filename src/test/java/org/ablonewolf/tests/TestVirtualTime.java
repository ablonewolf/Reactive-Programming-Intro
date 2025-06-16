package org.ablonewolf.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

import java.time.Duration;

/**
 * The TestVirtualTime class demonstrates how to test time-based reactive sequences using Reactor's virtual time
 * support, with enhanced readability and traceability through scenario naming and labeled assertions.
 * <p>
 * It shows how to control and verify the timing of emissions from a {@link Flux} that emits items with fixed delays,
 * using virtual time to simulate long-running operations without blocking real execution time.
 * <p>
 * Virtual time allows tests to simulate the passage of time deterministically. This class further improves test
 * clarity by:
 * - Assigning meaningful scenario names via {@link StepVerifierOptions#create()}<br>
 * - Annotating expectation steps with descriptive messages using the {@link StepVerifier.Step#as(String)} method<br>
 * These additions make it easier to understand and debug test failures.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a delayed flux using {@link Flux#range(int, int)} combined with {@link Flux#delayElements(Duration)}.<br>
 * 2. Configuring test scenarios with custom names using {@link StepVerifierOptions}.<br>
 * 3. Using virtual time via {@link StepVerifier#withVirtualTime(java.util.function.Supplier, StepVerifierOptions)}.<br>
 * 4. Labeling individual verification steps with the {@code as(...)} method for better diagnostic output.<br>
 * 5. Verifying event ordering and timing precision using methods like
 * {@link StepVerifier.Step#expectNoEvent(Duration)} and
 * {@link StepVerifier.Step#thenAwait(Duration)}.<br>
 * 6. Confirming the subscription signal before any data is emitted.<br>
 * 7. Ensuring complete verification with {@code verify()} after all expectations are set.
 * <p>
 * Dependencies:<br>
 * - Relies on JUnit Jupiter API for defining and running test cases.<br>
 * - Uses Reactor's {@link StepVerifier}, {@link Flux}, and virtual time utilities to test time-based reactive
 * pipelines.<br>
 * - Leverages {@link StepVerifierOptions} to customize test behavior and improve diagnostics.
 * <p>
 */
public class TestVirtualTime {

	private Flux<Integer> getItemsWithFixedDelay() {
		return Flux.range(1, 10)
				.delayElements(Duration.ofSeconds(10));
	}

	@Test
	public void test_ifItemsArriveWithinTimeFrame() {
		var options = StepVerifierOptions.create()
				.scenarioName("Testing whether emitted items arrive within time frame");

		StepVerifier.withVirtualTime(this::getItemsWithFixedDelay, options)
				.thenAwait(Duration.ofSeconds(100))
				.expectNext(1)
				.as("First item should be emitted within 100 seconds and its value should be 1")
				.expectNextCount(9)
				.as("Remaining item count should be nine")
				.verifyComplete();
	}

	@Test
	public void test_ifEventsTakePlaceInOrder() {
		var options = StepVerifierOptions.create()
				.scenarioName("Testing whether events taking place in order");

		StepVerifier.withVirtualTime(this::getItemsWithFixedDelay, options)
				.expectSubscription()
				.expectNoEvent(Duration.ofSeconds(9))
				.thenAwait(Duration.ofSeconds(1))
				.expectNext(1)
				.thenAwait(Duration.ofSeconds(90))
				.expectNextCount(9)
				.expectComplete()
				.verify();
	}
}
