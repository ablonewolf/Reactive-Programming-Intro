package org.ablonewolf.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * The TestVirtualTime class demonstrates how to test time-based reactive sequences using Reactor's virtual time
 * support.
 * It shows how to control and verify the timing of emissions from a {@link Flux} that emits items with fixed delays.
 * <p>
 * Virtual time allows tests to simulate the passage of time without relying on real-time execution, making it ideal
 * for verifying time-dependent logic in a fast and deterministic way.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a delayed flux using {@link Flux#range(int, int)} combined with {@link Flux#delayElements(Duration)}.<br>
 * 2. Using {@link StepVerifier#withVirtualTime(java.util.function.Supplier)} to wrap time-bound publishers.<br>
 * 3. Testing successful emission of all items after waiting for the total expected duration using
 * {@link StepVerifier.Step#thenAwait(Duration)}.<br>
 * 4. Verifying event ordering and timing precision by asserting no events occur before expected, using
 * {@link StepVerifier.Step#expectNoEvent(Duration)}.<br>
 * 5. Confirming the subscription signal before any data is emitted.<br>
 * 6. Ensuring complete verification with {@code verify()} after all expectations are set.
 * <p>
 * Dependencies:<br>
 * - Relies on JUnit Jupiter API for defining and running test cases.<br>
 * - Uses Reactor's {@link StepVerifier}, {@link Flux}, and virtual time utilities to test time-based reactive
 * pipelines.
 * <p>
 */
public class TestVirtualTime {

	private Flux<Integer> getItemsWithFixedDelay() {
		return Flux.range(1, 10)
				.delayElements(Duration.ofSeconds(10));
	}

	@Test
	public void test_ifItemsArriveWithinTimeFrame() {
		StepVerifier.withVirtualTime(this::getItemsWithFixedDelay)
				.thenAwait(Duration.ofSeconds(100))
				.expectNext(1)
				.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	public void test_ifEventsTakePlaceInOrder() {
		StepVerifier.withVirtualTime(this::getItemsWithFixedDelay)
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
