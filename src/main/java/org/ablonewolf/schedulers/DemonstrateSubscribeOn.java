package org.ablonewolf.schedulers;

import org.ablonewolf.common.Util;

/**
 * Demonstrates the usage of reactive streams and the behavior of `subscribeOn` in a multithreaded context.
 * <p>
 * This class utilizes the `NumberFlux` utility to create a reactive stream of integers and subscribes
 * multiple subscribers to the same stream using independent tasks that are executed on separate threads.
 * The `subscribeOn` operator in `NumberFlux` ensures that the subscription process runs asynchronously
 * on a bounded elastic thread pool.
 * <p>
 * Key functionalities:<br>
 * - Creates two subscribers for the same Flux, showing thread-specific behavior.<br>
 * - Uses {@code Thread.ofPlatform()} to run subscription tasks on separate threads for concurrent execution.<br>
 * - Pauses the main thread execution for a specified duration to allow the subscriptions to process.<br>
 * - Logs subscriber-specific events such as item reception, completion, and errors (via {@code Util.subscriber}
 * and {@code DefaultSubscriber}).
 * <p>
 * This class serves as an example to understand and demonstrate asynchronous execution, multithreading,
 * and the `subscribeOn` operator in reactive programming with Project Reactor.
 */
public class DemonstrateSubscribeOn {

	public static void main(String[] args) {
		var numberFlux = NumberFlux.getNumberFluxSubscribedOn();

		Runnable firstTask = () -> numberFlux.subscribe(Util.subscriber("First Number Subscriber"));
		Runnable secondTask = () -> numberFlux.subscribe(Util.subscriber("Second Number Subscriber"));

		Thread.ofPlatform().start(firstTask);
		Thread.ofPlatform().start(secondTask);

		Util.sleepSeconds(2L);
	}
}
