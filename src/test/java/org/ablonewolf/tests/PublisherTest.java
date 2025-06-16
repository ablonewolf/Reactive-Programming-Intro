package org.ablonewolf.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * The PublisherTest class demonstrates how to test reactive pipelines built using Reactor's {@link Flux} and
 * transformation operators, with the help of a controlled publisher created via {@link TestPublisher}.
 * <p>
 * It showcases how to encapsulate common transformation logic in a reusable {@link UnaryOperator}, and verify its
 * behavior
 * in a test environment by emitting known values and asserting expected outputs.
 * <p>
 * Key demonstrations include:<br>
 * 1. Defining a reusable transformation pipeline using a {@code UnaryOperator<Flux<String>} that applies a sequence
 * of operations:
 * trim, filter blank strings, convert to uppercase, and format with length.<br>
 * 2. Creating a testable publisher using {@link TestPublisher#create()} to control emissions during testing.<br>
 * 3. Applying transformation logic with {@link Flux#transform(Function function)}.<br>
 * 4. Emitting test data manually using {@link TestPublisher#emit(Object...)} within verification steps.<br>
 * 5. Using {@link StepVerifier} to assert exact output values after transformations are applied.<br>
 * 6. Ensuring complete verification with {@code verify()} after all expectations are defined.
 * <p>
 * Dependencies:<br>
 * - Relies on JUnit Jupiter API for defining and running test cases.<br>
 * - Uses Reactor's {@link Flux}, {@link TestPublisher}, and {@link StepVerifier} to model and test reactive
 * pipelines in a synchronous and deterministic way.<br>
 * - Leverages functional interfaces like {@code UnaryOperator} to compose and reuse transformation logic.
 * <p>
 */
public class PublisherTest {

	private UnaryOperator<Flux<String>> stringProcessor() {
		return flux -> flux
				.map(String::trim)
				.filter(string -> !string.isBlank())
				.map(String::toUpperCase)
				.map(string -> String.format("%s:%s", string, string.length()));
	}

	@Test
	public void test_whetherPublisherEmitsItemsProperly() {
		var publisher = TestPublisher.<String>create();
		var flux = publisher.flux();

		StepVerifier.create(flux.transform(stringProcessor()))
				.then(() -> publisher.emit("hello", "hi"))
				.expectNext("HELLO:5")
				.expectNext("HI:2")
				.expectComplete()
				.verify();
	}
}
