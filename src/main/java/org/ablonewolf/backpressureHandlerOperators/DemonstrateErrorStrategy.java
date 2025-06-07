package org.ablonewolf.backpressureHandlerOperators;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Demonstrates the use of error-handling strategies in a reactive programming context,
 * specifically using Project Reactor's backpressure handling operators.
 * This class provides a complete example pipeline to observe how different operators
 * collaborate to handle backpressure scenarios while maintaining reactive flow.
 * <p>
 * The pipeline performs the following steps:<br>
 * - Creates a stream of integer values using a custom number generator.<br>
 * - Applies the {@code onBackpressureError} operator to handle scenarios
 * where the downstream is slower than the upstream producer.<br>
 * - Limits the number of requests using the {@code limitRate} operator.<br>
 * - Schedules the processing on a bounded elastic scheduler.<br>
 * - Simulates a time-intensive transformation on each emitted item.<br>
 * - Subscribes to the stream to initiate processing.
 * <p>
 * The implementation illustrates:<br>
 * - Backpressure handling with {@code onBackpressureError} to throw an error when buffering is overwhelmed.<br>
 * - Controlled consumption of elements (using {@code limitRate}).<br>
 * - Processing delays using a method that simulates a time-consuming task.
 * <p>
 * The execution leverages the utility methods from the {@code Util} class
 * to emulate delays and simulate real-world, time-intensive processing.
 * This ensures that the pipeline realistically demonstrates backpressure scenarios.
 * <p>
 * The {@code NumberGenerator.getNumberProducerFromFluxCreate} method is used
 * as the source, generating values on a parallel scheduler.
 */
public class DemonstrateErrorStrategy {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateErrorStrategy.class);

	public static void main(String[] args) {

		var numberProducer = NumberGenerator.getNumberProducerFromFluxCreate(log);

		numberProducer
				.onBackpressureError()
				.limitRate(1)
				.publishOn(Schedulers.boundedElastic())
				.map(DemonstrateErrorStrategy::timeConsumingTask)
				.subscribe();

		Util.sleepSeconds(5L);

	}

	private static Integer timeConsumingTask(Integer i) {
		log.info("Received item: {}", i);
		Util.sleep(Duration.ofMillis(500));
		return i;
	}
}
