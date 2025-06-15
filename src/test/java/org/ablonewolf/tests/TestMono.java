package org.ablonewolf.tests;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The TestMono class demonstrates how to write unit tests for Reactor's {@link Mono} publishers using the
 * {@link StepVerifier}.
 * It includes a method that returns a Mono with conditional behavior based on input, along with multiple test methods
 * to verify different outcomes: success, empty result, error type, and error message.
 * <p>
 * This class focuses on testing reactive pipelines in a declarative and predictable way. Each test case verifies a
 * specific signal sequence (value, completion, or error) emitted by the publisher.
 * <p>
 * Key demonstrations include:<br>
 * 1. Implementing a conditional Mono factory method using a switch expression to return different result types.<br>
 * 2. Testing successful emission of a value using {@link StepVerifier.Step#expectNext(Object value)}.<br>
 * 3. Verifying an empty result using {@link StepVerifier.LastStep#expectComplete()}.<br>
 * 4. Asserting an error type with {@link StepVerifier.LastStep#expectError(Class throwable)}.<br>
 * 5. Validating error messages using {@link StepVerifier.LastStep#expectErrorMessage(String message)}.<br>
 * 6. Using descriptive test method names to clearly indicate the scenario being tested.
 * <p>
 * Dependencies:<br>
 * - Relies on JUnit Jupiter API for defining and running test cases.<br>
 * - Leverages Reactor's {@link StepVerifier} and {@link Publisher} API to test reactive sequences synchronously.
 * <p>
 */
public class TestMono {

	private Mono<String> getProduct(Integer id) {
		return switch (id) {
			case 1 -> Mono.defer(() -> Mono.fromSupplier(() -> "product-" + id));
			case 2 -> Mono.empty();
			default -> Mono.error(new IllegalArgumentException("invalid id"));
		};
	}

	@Test
	public void testProductWithValidId() {
		StepVerifier.create(getProduct(1))
				.expectNext("product-1")
				.expectComplete()
				.verify(); // similar to the subscribe method
	}

	@Test
	public void testProductWithEmptyOutput() {
		StepVerifier.create(getProduct(2))
				.expectComplete()
				.verify();
	}

	@Test
	public void testErrorForInvalidId() {
		StepVerifier.create(getProduct(3))
				.expectError(IllegalArgumentException.class)
				.verify();
	}

	@Test
	public void testErrorMessageForInvalidId() {
		StepVerifier.create(getProduct(4))
				.expectErrorMessage("invalid id")
				.verify();
	}
}
