package org.ablonewolf.schedulers;

import org.ablonewolf.common.Util;

/**
 * Demonstrates the usage of the {@code publishOn} operator in Project Reactor's Flux.
 * This class uses {@code NumberFlux} to create a reactive stream of integers that transfers
 * the process of the later operators to a different thread pool using the `publishOn` operator.
 * <p>
 * The main method executes the following:<br>
 * - Retrieves a Flux generated with the {@code publishOn} operator from {@code NumberFlux}.<br>
 * - Subscribes two separate subscribers to the Flux and processes its emissions concurrently
 * by running each subscription in a separate platform thread.<br>
 * - Introduces a delay to allow the subscriptions to complete.
 * <p>
 * This example showcases how {@code publishOn} can be used to influence the threading behavior
 * of a reactive stream and demonstrates the concurrency between multiple subscribers.
 */
public class DemonstratePublishOn {

	public static void main(String[] args) {
		var numberFlux = NumberFlux.getNumberFluxPublishOn();

		Runnable firstTask = () -> numberFlux.subscribe(Util.subscriber("First Number Subscriber"));
		Runnable secondTask = () -> numberFlux.subscribe(Util.subscriber("Second Number Subscriber"));

		Thread.ofPlatform().start(firstTask);
		Thread.ofPlatform().start(secondTask);

		Util.sleepSeconds(2L);
	}
}
