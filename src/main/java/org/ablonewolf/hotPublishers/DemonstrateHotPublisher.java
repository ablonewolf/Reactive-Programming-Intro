package org.ablonewolf.hotPublishers;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Demonstrates the concept of a hot publisher in project reactor or reactive programming.
 * A hot publisher allows multiple subscribers to share the same stream of data,
 * emitting items continuously irrespective of whether subscribers are actively listening.
 * <p>
 * This class uses a simulated movie streaming example where the movie scenes are generated
 * as a reactive Flux stream. The stream is shared among subscribers to simulate a scenario
 * where multiple viewers (subscribers) watch the same content at potentially different
 * starting points.
 * <p>
 * Key functionalities include:<br>
 * - Sharing a continuous reactive data stream among multiple subscribers.<br>
 * - Delayed scene generation to mimic real-time streaming.<br>
 * - Demonstrating late subscription by allowing subscribers to join with a delay.
 * <p>
 * The movie stream, when shared, preserves its continuous emission flow and does not restart
 * for subsequent subscribers. Each subscriber consumes the stream from the current emitted
 * point in time.
 */
public class DemonstrateHotPublisher {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateHotPublisher.class);

	public static void main(String[] args) {
		var movieStream = getMovieStream().share();

		movieStream.subscribe(Util.subscriber("First Watcher"));

		Util.sleepSeconds(5L);

		movieStream.subscribe(Util.subscriber("Second Watcher"));

		Util.sleepSeconds(22L);
	}

	/**
	 * Generates a stream of movie scenes with a delay between each scene.
	 * The stream is limited to 50 scenes, with each scene represented as a string.
	 *
	 * @return a Flux of strings, where each string represents a movie scene
	 */
	private static Flux<String> getMovieStream() {
		return Flux.generate(() -> {
							log.info("Received the request to watch the movie.");
							return 1;
						},
						(state, sink) -> {
							var scene = "Movie Scene " + state;
							log.info("Playing {}", scene);
							sink.next(scene);
							return ++state;
						})
				.take(50)
				.delayElements(Duration.ofMillis(500))
				.cast(String.class);
	}
}
