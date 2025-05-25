package org.ablonewolf.backpressureHandling;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

/**
 * Demonstrates efficient usage of backpressure handling using the {@code limitRate} operator provided
 * by Project Reactor. This class configures a reactive stream to manage the consumption of data
 * based on demand.
 * <p>
 * Key functionalities:<br>
 * - Generates a sequence of integers using a custom reactive Flux-based producer.<br>
 * - Applies the {@code limitRate} operator to regulate the flow of requests and effectively handle backpressure.<br>
 * - Processes data asynchronously using a bounded elastic scheduler.<br>
 * - Maps the emitted elements through a simulated time-consuming processing function.<br>
 * - Subscribes to the stream using a custom subscriber for consuming and logging processed items.
 * <p>
 * Features and Operation:<br>
 * - The {@code limitRate} operator ensures the producer emits items in small manageable batches
 * (configured to 10 items per batch).<br>
 * - The processing function applies a delay to simulate a time-intensive task for each item.<br>
 * - The subscriber logs the received data, ensuring visibility into the flow of the stream.<br>
 * - The main thread sleeps after initiating the stream to allow subscribers to process data.
 * <p>
 * Note:<br>
 * - This class demonstrates the practical application of backpressure handling in scenarios
 * where the consuming process is slower than the rate at which the producer generates events.<br>
 * - Use cases involve scenarios with asynchronous data flow and a need to balance production and consumption rates.
 */
public class DemonstrateLimitRate {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateLimitRate.class);

	public static void main(String[] args) {

		var producer = NumberGenerator.generateNumberProducer(log);

		producer.limitRate(10)
				.publishOn(Schedulers.boundedElastic())
				.map(DemonstrateLimitRate::timeConsumingTask)
				.subscribe(Util.subscriber("Time consuming subscriber"));

		Util.sleepSeconds(50L);
	}

	private static Integer timeConsumingTask(Integer i) {
		Util.sleepSeconds(1L);
		return i;
	}
}
