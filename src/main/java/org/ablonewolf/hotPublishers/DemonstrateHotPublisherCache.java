package org.ablonewolf.hotPublishers;

import org.ablonewolf.common.Util;

/**
 * DemonstrateHotPublisherCache is a demonstration class that illustrates the usage of
 * a hot publisher with caching capabilities using Project Reactor.
 * <p>
 * Key Highlights:<br>
 * - It showcases how a hot publisher (replaying cache) can broadcast events to multiple subscribers.<br>
 * - The example uses a cricket game as the data source, emitting scores periodically.<br>
 * - Subscriptions to the hot publisher cause immediate reception of events for new subscribers.
 * <p>
 * Features:<br>
 * - A replay cache is utilized to allow new subscribers to access previously emitted events.<br>
 * - Introduces subscription delays to help illustrate the behavior of a hot publisher with caching.<br>
 * - Utilizes the "CricketGame" and "CricketWatcher" classes for simulating a real-world scenario.
 * <p>
 * Usage:<br>
 * - A replay cache is created from the "CricketGame" class's score stream.<br>
 * - Subscriptions from different cricket watchers are demonstrated at staggered intervals.<br>
 * - Shows how new subscribers can seamlessly join an ongoing data stream and benefit from the cached events.
 * <p>
 * Note:<br>
 * - The delay in subscriptions and actions is implemented using the "Util.sleepSeconds" utility method.<br>
 * - This class is intended to highlight reactive stream concepts, specifically hot publishers with caching.
 */
public class DemonstrateHotPublisherCache {

	public static void main(String[] args) {
		var cricketGame = CricketGame.getCurrentScore().replay().autoConnect(0);

		var sam = new CricketWatcher("Sam");
		var john = new CricketWatcher("John");

		Util.sleepSeconds(5L);

		cricketGame.subscribe(sam);

		Util.sleepSeconds(3L);
		cricketGame.subscribe(john);

		Util.sleepSeconds(10L);
	}
}
