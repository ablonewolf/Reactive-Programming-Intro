package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.NumberGenerator;
import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * Demonstrates the zipping of two reactive streams using Project Reactor's Flux.zip method.
 * <p>
 * This class retrieves two different streams of numbers:<br>
 * - A stream of odd numbers generated using {@link NumberGenerator#getOddNumberStream(Logger)}.<br>
 * - A stream of even numbers generated using {@link NumberGenerator#getEvenNumberStream(Logger)}.
 * <p>
 * These streams are then zipped together, pairing each element from the odd number stream
 * with an element from the even number stream. The paired elements are subsequently consumed
 * by a subscriber created using {@link Util#subscriber(String)}.
 * <p>
 * The program concludes by pausing the main thread for a specified duration
 * (4 seconds in this case) using {@link Util#sleepSeconds(Long)} to allow the reactive streams
 * to emit and process the elements.
 */
public class DemonstrateZip {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateZip.class);

	public static void main(String[] args) {
		var oddNumberStream = NumberGenerator.getOddNumberStream(log);
		var evenNumberStream = NumberGenerator.getEvenNumberStream(log);

		Flux.zip(oddNumberStream, evenNumberStream)
				.subscribe(Util.subscriber("Number Stream Subscriber"));

		Util.sleepSeconds(4L);
	}
}
