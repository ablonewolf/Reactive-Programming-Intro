package org.ablonewolf.hotPublishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * The MovieTheatre class is a utility class for simulating a streaming service
 * that emits a sequence of movie scenes with a specified delay.
 * It represents a finite stream of scenes with a predefined number of elements.
 * The output is intended for use in reactive stream demonstrations and experiments.
 */
public final class MovieTheatre {

	private static final Logger log = LoggerFactory.getLogger(MovieTheatre.class);

	/**
	 * Generates a stream of movie scenes with a delay between each scene.
	 * The stream is limited to 50 scenes, with each scene represented as a string.
	 *
	 * @return a Flux of strings, where each string represents a movie scene
	 */
	public static Flux<String> getMovieStream() {
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

	private MovieTheatre() {

	}
}
