package org.ablonewolf.hotPublishers;

import org.ablonewolf.common.Util;

/**
 * DemonstrateHotPublisher showcases the usage of a hot publisher in a reactive streams context.
 * This implementation demonstrates subscribers consuming data at different times
 * from a shared, connectable Flux stream using Reactor.
 * <p>
 * Key Features:<br>
 * - A simulated movie streaming service (provided by MovieTheatre) emits a finite series of movie scenes.
 * - The stream is converted into a hot publisher by calling "publish()" and "autoConnect(0)".<br>
 * - Two subscribers, Sam and John, subscribe to the stream at different times,
 * illustrating late subscription to a hot publisher.<br>
 * - Demonstrates backpressure handling by using "onBackpressureDrop()"
 * and explicit requests for a limited number of elements from the subscription.<br>
 * <p>
 * Execution Behavior:<br>
 * - The first subscriber (Sam) subscribes after a delay and requests 20 elements.<br>
 * - The second subscriber (John) subscribes later and requests 15 elements.<br>
 * - The program introduces delays between subscriptions and uses "sleepSeconds"
 * to simulate real-time streaming behavior.
 * <p>
 * This class is utilized to understand how hot publishers work with late subscribers
 * and how shared sequences are consumed in a reactive streams' ecosystem.
 */
public class DemonstrateHotPublisher {

	public static void main(String[] args) {
		var movieStream = MovieTheatre.getMovieStream().take(51).publish().autoConnect(0);

		var sam = new MovieWatcher("Sam");
		var john = new MovieWatcher("John");

		Util.sleepSeconds(2L);

		movieStream.onBackpressureDrop()
				.log()
				.subscribe(sam);

		sam.getSubscription().request(20L);

		Util.sleepSeconds(15L);

		movieStream.onBackpressureDrop()
				.log()
				.subscribe(john);

		john.getSubscription().request(15L);

		Util.sleepSeconds(10L);
	}

}
