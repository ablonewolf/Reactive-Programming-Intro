package org.ablonewolf.flux.operators;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Class DemonstrateErrorHandling provides multiple methods to showcase error handling
 * strategies using Project Reactor's reactive programming libraries. These methods demonstrate
 * different operators for managing and handling errors, offering custom reactions or fallback
 * logic when encountering exceptions in asynchronous reactive streams.
 * <p>
 * Core functionalities include:<br>
 * - Handling errors gracefully by providing fallback values using "onErrorReturn".<br>
 * - Dynamically resolving alternative streams or values with "onErrorResume".<br>
 * - Suppressing errors and completing streams silently with "onErrorComplete".<br>
 * - Ignoring errors while processing the remaining elements in the stream using "onErrorContinue".
 * <p>
 * Each demonstration uses a custom number-emitting stream to simulate errors and recovery logic,
 * where the results are consumed and logged by a defined subscriber.
 */
public class DemonstrateErrorHandling {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateErrorHandling.class);

	public static void main(String[] args) {

		demonstrateOnErrorReturn();
		demonstrateOnErrorResume();
		demonstrateOnErrorComplete();
		demonstrateOnErrorContinue();
	}

	/**
	 * Demonstrates error handling in a reactive stream using the `onErrorReturn` operator.
	 * This method subscribes to a number-emitting stream and provides fallback logic for
	 * specific exceptions or any other error occurring in the stream.
	 * <p>
	 * The order of error handling is as follows:<br>
	 * - If an IllegalArgumentException is caught, the stream emits a value of 0.<br>
	 * - If an ArithmeticException is caught, the stream emits a value of -1.<br>
	 * - For any other error, the stream emits a value of -2.<br>
	 * <p>
	 * The results, including any emitted fallback values, are consumed and logged by a custom
	 * subscriber named "Number Subscriber".
	 */
	private static void demonstrateOnErrorReturn() {
		getNumberStream()
				.onErrorReturn(IllegalArgumentException.class, 0)
				.onErrorReturn(ArithmeticException.class, -1)
				.onErrorReturn(-2)
				.subscribe(Util.subscriber("Number Subscriber"));
	}

	/**
	 * Demonstrates error handling in a reactive stream using the `onErrorResume` operator.
	 * This method handles errors emitted by the stream by providing fallback logic:<br>
	 * - If the error is an "ArithmeticException", a fallback value is fetched using the `getFallbackValue` method.<br>
	 * - For any other error type, a default fallback value of `-5` is emitted.
	 * <p>
	 * The results, including successfully emitted values and fallback values, are consumed
	 * and logged by a custom subscriber named "Number Subscriber".
	 */
	private static void demonstrateOnErrorResume() {
		getNumberStream()
				.onErrorResume(ArithmeticException.class, ex -> getFallbackValue())
				.onErrorResume(ex -> Mono.just(-5))
				.subscribe(Util.subscriber("Number Subscriber"));
	}

	/**
	 * Demonstrates error handling in a reactive stream using the "onErrorComplete" operator.
	 * <p>
	 * This method subscribes to a number-emitting stream and uses the `onErrorComplete` operator
	 * to suppress errors and complete the stream gracefully. If any error is encountered during
	 * the processing of the stream, the stream is terminated without propagating the error downstream.
	 * <p>
	 * The processed results, excluding any errors, are consumed and logged by a custom subscriber
	 * named "Number Subscriber".
	 */
	private static void demonstrateOnErrorComplete() {
		getNumberStream()
				.onErrorComplete()
				.subscribe(Util.subscriber("Number Subscriber"));
	}

	/**
	 * Demonstrates error handling in a reactive stream using the `onErrorContinue` operator.
	 * This method processes a number-emitting stream and specifies a continuation strategy for
	 * errors occurring during the processing of stream elements.
	 * <p>
	 * The `onErrorContinue` operator allows ignoring the error and continuing with the remaining
	 * elements in the stream. When an error occurs, it invokes a handler with the exception and
	 * the affected item, enabling custom error logging or handling logic.
	 * <p>
	 * In this implementation:<br>
	 * - Upon encountering an error, a custom error handler logs the error details along with the
	 * affected item.<br>
	 * - The reactive stream continues processing the remaining items unaffected by the error.
	 * <p>
	 * The results, including successfully processed items, are consumed and logged by a custom
	 * subscriber named "Number Subscriber".
	 */
	private static void demonstrateOnErrorContinue() {
		getNumberStream()
				.onErrorContinue((ex, item) -> {
					String errorMessage = (Objects.nonNull(ex)) ? ex.getMessage() : "Unknown error";
					log.error("Error occurred while processing item: {}, Error details: {}", item, errorMessage);
				})
				.subscribe(Util.subscriber("Number Subscriber"));
	}

	/**
	 * Generates a reactive stream of integers ranging from 1 to 54 and applies custom handling
	 * logic to each emitted value. The handling logic modifies the output based on the value's
	 * modulo operation with 6. Specific cases intentionally introduce errors or modifications:<br>
	 * - Case 0: Triggers an arithmetic exception by dividing by zero.<br>
	 * - Case 1 to 5: Transforms the value based on multiplication factors.<br>
	 * - Default: Complete the stream.<br>
	 *
	 * @return a Flux emitting transformed integers based on the described handling logic.
	 */
	private static Flux<Integer> getNumberStream() {
		return Flux.range(1, 54)
				.handle((item, sink) -> {
					switch (item % 6) {
						case 0 -> sink.next(item / 0); // intentionally throwing error
						case 1 -> sink.next(item);
						case 2 -> sink.next(item * 2);
						case 3 -> sink.next(item * 3);
						case 4 -> sink.next(item * 4);
						case 5 -> sink.next(item * 5);
						default -> sink.complete();
					}
				});
	}

	/**
	 * Provides a fallback integer value wrapped in a reactive Mono.
	 * The value is generated randomly between 10 and 100 using the Faker utility.
	 *
	 * @return a Mono emitting a randomly generated integer value between 10 and 100.
	 */
	private static Mono<Integer> getFallbackValue() {
		return Mono.fromSupplier(() -> Util.getFaker().random().nextInt(10, 100));
	}
}
