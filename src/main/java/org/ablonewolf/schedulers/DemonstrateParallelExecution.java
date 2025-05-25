package org.ablonewolf.schedulers;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Demonstrates the usage of parallel execution with Project Reactor's Flux.
 * This class processes a stream of integers in parallel using the {@code parallel()}
 * and {@code runOn} operators, followed by applying a transformation to each element
 * via the {@code map} operator. The results are consumed by a subscriber to exhibit
 * the behavior of a parallelized reactive stream.
 * <p>
 * Key functionalities:<br>
 * - Generates a range of integers from 1 to 20.<br>
 * - Splits the processing into parallel streams using the {@code parallel()} operator.<br>
 * - Executes the operations on a parallel thread pool specified by {@code Schedulers.parallel()}.<br>
 * - Applies a transformation to each integer by doubling its value in a background thread.<br>
 * - Subscribes to the parallelized and transformed stream using a custom subscriber implementation.<br>
 * - Introduces a delay to allow the asynchronous processing to complete.
 */
public class DemonstrateParallelExecution {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateParallelExecution.class);

	public static void main(String[] args) {
		Flux.range(1, 20)
				.parallel()
				.runOn(Schedulers.parallel())
				.map(DemonstrateParallelExecution::process)
				.subscribe(Util.subscriber("Number Subscriber"));

		Util.sleepSeconds(15L);
	}

	private static Integer process(Integer number) {
		log.info("Processing number {}", number);
		Util.sleepSeconds(2L);
		return number * 2;
	}
}
