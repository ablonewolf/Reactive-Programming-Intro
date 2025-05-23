package org.ablonewolf.schedulers;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Demonstrates the use of the `subscribeOn` operator in Project Reactor
 * to control which thread pool is responsible for subscribing to a Flux.
 * <p>
 * The program creates a Flux that emits numbers from 0 to 9, logs the emission,
 * and applies several `doFirst` and `doOnNext` operations to showcase the order
 * of execution and thread behavior.
 * <p>
 * Key Features:<br>
 * - Creates a Flux using `Flux.create`, with emitting logic in a loop.<br>
 * - Logs actions during Flux creation, subscription, and item processing using SLF4J Logger.<br>
 * - Uses the `subscribeOn(Schedulers.boundedElastic())` operator to specify
 * a bounded elastic thread pool for subscription.<br>
 * - Implements multiple asynchronous subscribers, each subscribing to the same Flux.<br>
 * - Demonstrates how the use of `subscribeOn` affects the thread on which
 * subscription logic is executed.<br>
 * - Makes the main thread sleep after starting asynchronous tasks to allow
 * adequate time for execution before program termination.<br>
 */
public class DemonstrateSubscribeOn {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateSubscribeOn.class);

	public static void main(String[] args) {
		var numberFlux = Flux.create(fluxSink -> {
					for (int i = 0; i < 10; i++) {
						log.info("Emitting number {}", i);
						fluxSink.next(i);
					}
					fluxSink.complete();
				})
				.doOnNext(value -> log.info("Number generated: {}", value))
				.doFirst(() -> log.info("Passing to a different Thread pool."))
				.subscribeOn(Schedulers.boundedElastic())
				.doFirst(() -> log.info("It will be printed inside the caller thread " +
						"as now the subscriber will subscribe to the publisher"));

		Runnable firstTask = () -> numberFlux.subscribe(Util.subscriber("First Number Subscriber"));
		Runnable secondTask = () -> numberFlux.subscribe(Util.subscriber("Second Number Subscriber"));

		Thread.ofPlatform().start(firstTask);
		Thread.ofPlatform().start(secondTask);

		Util.sleepSeconds(2L);
	}
}
