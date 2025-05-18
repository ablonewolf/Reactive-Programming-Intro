package org.ablonewolf.operators;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the usage of Reactor's "switchIfEmpty" operator in a reactive stream.
 * <p>
 * The main functionality involves creating a Flux stream of integers, applying a filter to selectively
 * allow elements that meet a specified condition, and providing a fallback Flux in case the original
 * stream becomes empty after the filtering operation.
 * <p>
 * Key components:<br>
 * - "Flux.range(int, int)": Generates a range of integers for processing in the stream.<br>
 * - "filter(Predicate)": Filters elements from the stream based on a specified condition.<br>
 * - "switchIfEmpty(Publisher)": Subscribes to an alternative Publisher when the current stream is empty.<br>
 * - "getFallbackValue()": A private method that provides the fallback Flux stream.<br>
 * - "Util.subscriber(String)": Subscribes to the stream using a custom subscriber that logs received elements.<br>
 */
public class DemonstrateSwitchIfEmpty {

	public static void main(String[] args) {

		Flux.range(1, 20)
				.filter(num -> num > 20)
				.switchIfEmpty(getFallbackValue())
				.subscribe(Util.subscriber("Number Subscriber"));
	}

	private static Flux<Integer> getFallbackValue() {
		return Flux.range(21, 20);
	}
}
