package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * demonstrating how to create an empty flux and a flux with a custom error
 */
public class DemonstrateFluxEmptyAndError {

	public static void main(String[] args) {

		Flux.empty()
				.subscribe(Util.subscriber("Empty Subscriber"));

		Flux.error(new RuntimeException("Custom Error"))
				.subscribe(Util.subscriber("Sample Subscriber"));

		Util.sleepSeconds(2L);
	}
}
