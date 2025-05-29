package org.ablonewolf.common;

import com.github.javafaker.Faker;
import lombok.Getter;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

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

	private static void printThreadInterruptedMessage(InterruptedException e) {
		logger.error("Thread was interrupted due to this reason: {}", e.getMessage());
	}

}
