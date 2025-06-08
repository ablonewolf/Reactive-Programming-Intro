package org.ablonewolf.common;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.UnaryOperator;

public class Util {

	/**
	 * using a Faker instance to generate random fake names for various items
	 */
	@Getter
	private static final Faker faker = Faker.instance();
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	/**
	 * method for returning a new instance of default subscriber
	 *
	 * @param name: the name of the subscriber assigned by the publisher
	 * @return default subscriber
	 */
	public static <T> Subscriber<T> subscriber(String name) {
		return new DefaultSubscriber<>(name);
	}

	public static <T> Subscriber<T> subscriber(String name, String itemName) {
		return new DefaultSubscriber<>(name, itemName);
	}

	/**
	 * make the main thread sleep for a certain amount of time
	 *
	 * @param seconds passed to the method to make Thread sleep for that duration of time
	 */
	public static void sleepSeconds(Long seconds) {
		try {
			Thread.sleep(Duration.ofSeconds(seconds));
		} catch (InterruptedException e) {
			printThreadInterruptedMessage(e);
		}
	}

	public static void sleep(Duration duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			printThreadInterruptedMessage(e);
		}
	}

	public static <T> UnaryOperator<Flux<T>> getFluxLogger(String fluxName, Logger logger) {
		return flux -> {
			AtomicLong itemCount = new AtomicLong(0);

			return flux
					.doFirst(() -> {
						logger.info("Subscribed to {}", fluxName);
						itemCount.set(0);
					})
					.doOnCancel(() -> logger.info("Cancelling {}.", fluxName))
					.doOnNext(t -> itemCount.incrementAndGet())
					.doOnError(ex -> logger.error("Error in {}, details: {}", fluxName, ex.getMessage()))
					.doOnComplete(() -> {
						long count = itemCount.get();
						if (count == 0) {
							logger.info("{} completed after emitting zero items.", fluxName);
						} else {
							logger.info("{} completed after emitting {} item(s).", fluxName, count);
						}
					});
		};
	}

	public static <T> UnaryOperator<Mono<T>> loggerForMono(String name, Logger log) {
		return publisher -> publisher
				.doOnCancel(() -> log.info("Cancelled {}", name))
				.doOnSubscribe(sub -> log.info("Subscribed to {}", name))
				.doOnError(ex -> log.error("Error in {}, details: {}", name, ex.getMessage()));
	}

	private static void printThreadInterruptedMessage(InterruptedException e) {
		logger.error("Thread was interrupted due to this reason: {}", e.getMessage());
	}

}
