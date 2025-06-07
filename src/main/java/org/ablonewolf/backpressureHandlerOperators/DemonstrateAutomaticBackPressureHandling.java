package org.ablonewolf.backpressureHandlerOperators;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.scheduler.Schedulers;

/**
 * Demonstrates handling of backpressure in a reactive programming paradigm using Project Reactor.
 * This class sets up a reactive stream with a producer generating increasing integers and a
 * consumer processing these values with a time-consuming task.
 * <p>
 * Key features of this class:<br>
 * - Uses a generator to emit a sequence of integers.<br>
 * - Applies backpressure strategies by adjusting the scheduler and buffer sizes.<br>
 * - Executes the time-consuming processing of emitted items on a separate bounded elastic scheduler.<br>
 * - Logs the flow of data through the stream and demonstrates backpressure handling.
 * <p>
 * Functionality overview:<br>
 * - Configure the buffer size to a small value.<br>
 * - Generate integer values in a parallel scheduler.<br>
 * - Process these values with a delay (artificial task) on a bounded elastic scheduler.<br>
 * - A subscriber is used to consume and log the processed values.<br>
 * - Simulates the main thread sleeping to allow the subscriber to process data.
 * <p>
 * Note:
 * - The reactive stream involves utilizing Flux for data emission, mapping for processing,
 * and subscribing for final consumption.<br>
 * - The backpressure is indirectly managed through the small buffer size and bounded scheduler.<br>
 */
public class DemonstrateAutomaticBackPressureHandling {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateAutomaticBackPressureHandling.class);

	static {
		System.setProperty("reactor.bufferSize.small", "24");
	}

	public static void main(String[] args) {

		var producer = NumberGenerator.generateNumberProducer(log);

		// producer will publish items into the queue until the queue is at least 75% empty
		producer
				.publishOn(Schedulers.boundedElastic())
				.map(DemonstrateAutomaticBackPressureHandling::timeConsumingTask)
				.subscribe(Util.subscriber("Time consuming subscriber"));

		Util.sleepSeconds(50L);
	}

	private static Integer timeConsumingTask(Integer i) {
		Util.sleepSeconds(1L);
		return i;
	}
}
