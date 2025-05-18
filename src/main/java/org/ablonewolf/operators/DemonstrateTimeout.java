package org.ablonewolf.operators;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * The DemonstrateTimeout class showcases the handling of reactive streams with timeout operations
 * and fallback strategies. This class includes methods to demonstrate scenarios such as default
 * timeouts, timeouts with fallback publishers, and cascading multiple timeouts.
 * <p>
 * The purpose of this class is to provide examples of robust reactive stream processing
 * by addressing delays and errors in data production through timeout operations and fallback mechanisms.
 */
public class DemonstrateTimeout {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateTimeout.class);

	public static void main(String[] args) {

		demonstrateDefaultTimeout();
		demonstrateTimeoutWithFallback();
		demonstrateMultipleTimeouts();

		Util.sleepSeconds(8L);

	}

	/**
	 * Demonstrates the use of a default timeout for a reactive stream that emits a product name.
	 * <p>
	 * This method performs the following actions:<br>
	 * - Retrieves a product name using the "getProductName" method, which intentionally introduces a delay.<br>
	 * - Applies a timeout of 2 seconds to the stream. If the stream takes longer than the timeout duration,
	 * a fallback default value ("default product name") is returned to handle the timeout scenario gracefully.<br>
	 * - Subscribes to the processed stream using a custom subscriber named "Product Name Subscriber" provided
	 * by the `Util.subscriber` method.
	 * <p>
	 * The primary purpose of this method is to handle scenarios where the source of the data takes too long to respond,
	 * ensuring the program continues execution with a fallback value instead of failing.
	 */
	private static void demonstrateDefaultTimeout() {
		log.info("Inside the method where default timeout is demonstrated");
		getProductName()
				.timeout(Duration.ofSeconds(2L))
				.onErrorReturn("default product name")
				.subscribe(Util.subscriber("Product Name Subscriber"));
	}

	/**
	 * Demonstrates the use of a timeout operator combined with a fallback mechanism in a reactive stream
	 * to handle delays in data production.
	 * <p>
	 * This method performs the following actions:<br>
	 * 1. Retrieves a product name using the "getProductName" method,
	 * which introduces an intentional delay in data emission.<br>
	 * 2. Applies a timeout of 3 seconds to the stream. If the stream takes longer than the specified timeout duration,
	 * the fallback publisher obtained from "getFallbackProductName" is used to emit a fallback product name.<br>
	 * 3. In case of errors or exceptions during the stream processing, a default value ("default product name")
	 * is emitted using the "onErrorReturn" operator.<br>
	 * 4. Subscribes to the processed stream using a custom subscriber named "Product Name Subscriber",
	 * provided by the "Util.subscriber" method.
	 * <p>
	 * This method ensures resilient and fault-tolerant stream processing by handling scenarios where the source data
	 * takes too long to respond or encounters an issue during production.
	 */
	private static void demonstrateTimeoutWithFallback() {
		log.info("Inside the method where timeout with fallback is demonstrated");
		getProductName()
				.timeout(Duration.ofSeconds(3L), getFallbackProductName())
				.onErrorReturn("default product name")
				.subscribe(Util.subscriber("Product Name Subscriber"));
	}

	/**
	 * Demonstrates the use of multiple timeout operators in a reactive stream.
	 * <p>
	 * This method performs the following operations:<br>
	 * - Retrieves a fallback product name using the "getFallbackProductName" method.<br>
	 * - Applies a timeout of 1 second to the stream. If the stream takes longer to emit, a fallback
	 * publisher is used to emit an alternative value.<br>
	 * - Introduces a second timeout of 300 milliseconds, ensuring that delays beyond this
	 * duration trigger the appropriate error handling.<br>
	 * - Handles errors gracefully using the "onErrorContinue" operator, which logs an error message
	 * while allowing stream processing to continue with subsequent items.<br>
	 * - Subscribes to the processed stream with a custom subscriber named "Product Name Subscriber"
	 * using the "Util.subscriber" utility method.<br>
	 * <p>
	 * This method demonstrates resilient stream processing by applying cascading timeouts and error
	 * handling mechanisms to ensure robustness even in cases of delay or error.
	 */
	private static void demonstrateMultipleTimeouts() {
		log.info("Inside the method where multiple timeouts are demonstrated");
		var productMono = getFallbackProductName()
				.timeout(Duration.ofSeconds(1L), getFallbackProductName());

		productMono
				.timeout(Duration.ofMillis(300L))
				.onErrorContinue(((throwable, item) ->
						log.error("Error occurred while processing the item: {}. Error is: {}",
								item, throwable.getMessage())))
				.subscribe(Util.subscriber("Product Name Subscriber"));
	}

	/**
	 * Provides a fallback product name emitted as a reactive Mono stream after a brief delay.
	 * The fallback product name is generated using a random faker utility.
	 *
	 * @return a {@link Mono} emitting a fallback product name after a delay of 100 milliseconds
	 */
	private static Mono<String> getFallbackProductName() {
		return Mono.fromSupplier(() -> Util.getFaker().commerce().productName())
				.delayElement(Duration.ofMillis(100L));
	}

	/**
	 * Generates and emits a product name as a reactive Mono stream with an intentional delay of 4 seconds.
	 *
	 * @return a {@link Mono} emitting a randomly generated product name after a delay of 4 seconds
	 */
	private static Mono<String> getProductName() {
		return Mono.fromSupplier(() -> Util.getFaker().commerce().productName())
				// intentionally delaying the generation of the name
				.delayElement(Duration.ofSeconds(4L));
	}
}
