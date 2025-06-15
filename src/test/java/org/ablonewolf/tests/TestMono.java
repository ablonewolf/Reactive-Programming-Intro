package org.ablonewolf.tests;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The TestMono class demonstrates how to write unit tests for Reactor's {@link Mono} publishers using the
 * {@link StepVerifier}.
 * It includes a simple method that returns a Mono and a corresponding test case to verify its behavior under
 * expected conditions.
 * <p>
 * This class focuses on testing reactive streams in a non-blocking and declarative manner. It uses logging to
 * observe side effects
 * such as when the Mono is subscribed to or executed.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a testable {@link Mono<String>} using {@link Mono#fromSupplier(java.util.function.Supplier supplier)}
 * .<br>
 * 2. Using {@link Mono#doFirst(Runnable runnable)} to log when the Mono is invoked.<br>
 * 3. Writing a test method annotated with {@link Test} to verify that the Mono emits the expected value.<br>
 * 4. Using {@link StepVerifier#create(Publisher publisher)} to declaratively assert the expected signal sequence from
 * the
 * publisher.<br>
 * 5. Calling {@code verify()} to trigger subscription and perform assertions.
 * <p>
 * Dependencies:<br>
 * - Uses SLF4J's {@link Logger} for logging internal execution steps during test runs.<br>
 * - Relies on JUnit Jupiter API for defining and running test cases.<br>
 * - Leverages Reactor's {@link StepVerifier} for testing reactive sequences in a synchronous and predictable way.
 * <p>
 */
public class TestMono {

	private static final Logger log = LoggerFactory.getLogger(TestMono.class);

	private Mono<String> getProduct(Integer id) {
		return Mono.fromSupplier(() -> "product-" + id)
				.doFirst(() -> log.info("Method got invoked."));
	}

	@Test
	public void testProduct_When_Id_provided() {
		StepVerifier.create(getProduct(1))
				.expectNext("product-1")
				.expectComplete()
				.verify(); // similar to the subscribe method
	}
}
