package org.ablonewolf.backpressureHandling;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Demonstrates the usage of backpressure strategies with Project Reactor's reactive streams.
 * <p>
 * This class utilizes the {@code onBackpressureBuffer} method to handle situations where the
 * downstream subscriber cannot keep up with the upstream producer. The main purpose is
 * to showcase how buffering can be applied in a controlled manner to manage backpressure.
 * <p>
 * Features:<br>
 * - Produces a stream of integer numbers from a custom number generator.<br>
 * - Uses backpressure buffer to accumulate items when demand is lower than supply.<br>
 * - Executes a time-consuming task for each item emitted by the producer.<br>
 * - Limits consumption rate to one item at a time using {@code limitRate}.<br>
 * - Handles concurrent processing using a bounded elastic scheduler.<br>
 * - Logs emissions and processing of items for easier tracking.
 * <p>
 * Design:<br>
 * - Employs reactive programming principles alongside Project Reactor.<br>
 * - This is an executable program with a `main` method.<br>
 * - Includes a utility method to simulate time-consuming computation.
 * <p>
 * Use Case:
 * This class is intended to demonstrate and simulate real-world scenarios where producers
 * emit data faster than consumers can process. It highlights strategies to manage and
 * mitigate backpressure without losing emitted items.
 */
public class DemonstrateBufferStrategy {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateBufferStrategy.class);

	public static void main(String[] args) {

		var numberProducer = NumberGenerator.getNumberProducerFromFluxCreate(log);

		numberProducer
				.onBackpressureBuffer()
				.limitRate(1)
				.publishOn(Schedulers.boundedElastic())
				.map(DemonstrateBufferStrategy::timeConsumingTask)
				.subscribe();

		Util.sleepSeconds(50L);

	}

	private static Integer timeConsumingTask(Integer i) {
		log.info("Received item: {}", i);
		Util.sleep(Duration.ofMillis(500));
		return i;
	}
}
