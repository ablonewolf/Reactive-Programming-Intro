package org.ablonewolf.combinationOperators;

import org.ablonewolf.common.NumberGenerator;
import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates the usage of the `startWith` operator provided by Project Reactor.
 * The program combines elements from two reactive streams and subscribes to the resulting stream.
 * <p>
 * This class retrieves two streams of numbers using methods from the {@code NumberGenerator} utility class:<br>
 * - A mini number stream containing predefined integers.<br>
 * - A large number stream containing a range of integers starting from 3.
 * <p>
 * The {@code startWith} operator is used to prepend the mini number stream to the large number stream.
 * The combined stream is then subscribed to through a default subscriber implementation.
 * <p>
 * A logger is used to log the subscription events for both streams and to monitor the execution.
 */
public class DemonstrateStartWith {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateStartWith.class);

	public static void main(String[] args) {

		var numberStream = NumberGenerator.getNumberStream(log);
		var miniNumberStream = NumberGenerator.getMiniNumberStream(log);

		numberStream.startWith(miniNumberStream)
				.subscribe(Util.subscriber("Number Stream Subscriber"));

		Util.sleepSeconds(2L);

	}
}
