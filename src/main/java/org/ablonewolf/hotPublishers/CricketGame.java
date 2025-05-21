package org.ablonewolf.hotPublishers;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * The CricketGame class is a utility class designed to simulate the behavior of a cricket game
 * by emitting the current score periodically. It demonstrates reactive stream functionality
 * using Project Reactor's Flux.
 * <p>
 * Key Features:<br>
 * - Generates a stream of cricket scores with updates every 2 seconds.<br>
 * - The scores are randomly generated and incremented for each update.<br>
 * - Publishes updates in a reactive stream that can be consumed by subscribers.<br>
 * - Includes logging to provide insights into the score updates being published.
 * <p>
 * Usage:<br>
 * - Static methods are used to fetch the score stream.<br>
 * - This class is intended for the demonstration of reactive programming concepts.
 * <p>
 * Note:
 * - This class cannot be instantiated as it has a private constructor.
 */
public final class CricketGame {

	private static final Logger log = LoggerFactory.getLogger(CricketGame.class);
	private static int currentScore = 0;

	public static Flux<Integer> getCurrentScore() {
		return Flux.generate((sink) -> {
					int run = Util.getFaker().random().nextInt(1, 6);
					currentScore += run;
					sink.next(currentScore);
				})
				.delayElements(Duration.ofSeconds(2))
				.doOnNext(score -> log.info("Current score is {}", score))
				.cast(Integer.class);
	}

	private CricketGame() {

	}
}
