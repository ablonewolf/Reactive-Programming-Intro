package org.ablonewolf.hotPublishers;

import org.ablonewolf.common.Util;

/**
 * Demonstrates the behavior of a hot publisher using a shared movie stream.
 * <p>
 * This class simulates a scenario where multiple subscribers join a single
 * movie stream, but at different times, showing how a hot publisher shares
 * emission among subscribers without replaying missed events for late subscribers.
 * <p>
 * Features:<br>
 * - Utilizes the "MovieTheatre.getMovieStream()" method to create a Flux of movie scenes.<br>
 * - Converts the cold stream into a hot stream using the "share()" operator, enabling
 *   multiple subscribers to observe emissions in real-time.<br>
 * - Demonstrates late subscription where a new subscriber does not receive earlier events.<br>
 * <p>
 * Usage Details:<br>
 * - The first subscriber ("First Watcher") subscribes to the movie stream as it begins.<br>
 * - After 5 seconds, a second subscriber ("Second Watcher") joins, demonstrating that
 *   it only receives the events being emitted from the time of its subscription.
 * - The program then continues the simulation for 22 seconds in total.
 */
public class DemonstrateHotPublisher {

	public static void main(String[] args) {
		var movieStream = MovieTheatre.getMovieStream().share();

		movieStream.subscribe(Util.subscriber("First Watcher"));

		Util.sleepSeconds(15L);

		movieStream.subscribe(Util.subscriber("Second Watcher"));

		Util.sleepSeconds(10L);
	}

}
