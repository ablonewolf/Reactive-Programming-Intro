package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates the usage of the {@code concatWith} operator provided by Project Reactor.
 * The program combines elements from two reactive streams and subscribes to the resulting stream.
 * <p>
 * This class retrieves two streams of integers using methods from the {@code NumberGenerator} utility class:<br>
 * - A mini number stream containing predefined integers.<br>
 * - A large number stream containing a range of integers starting from 3.
 * <p>
 * The {@code concatWith} operator is used to concatenate the mini number stream with the large number stream.
 * The combined stream is then subscribed to through a default subscriber implementation.
 * <p>
 * A logger is used to log subscription events for both streams and monitor the execution.
 */
public class DemonstrateConcatWith {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateConcatWith.class);

	public static void main(String[] args) {
		var numberStream = NumberGenerator.getNumberStream(log);

		var miniNumberStream = NumberGenerator.getMiniNumberStream(log);

		miniNumberStream.concatWith(numberStream)
				.subscribe(Util.subscriber("Number Stream Subscriber"));

		Util.sleepSeconds(2L);
	}
}
