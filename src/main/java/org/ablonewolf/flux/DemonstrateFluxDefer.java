package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * demonstrating how to use the defer function of Flux to delay the execution of that flux
 */
public class DemonstrateFluxDefer {

	private static final Logger logger = LoggerFactory.getLogger(DemonstrateFluxDefer.class);

	public static void main(String[] args) {

		// using defer delays the creation and execution of the Flux. It will only be created when it's subscribed.
		var publisher = Flux.defer(DemonstrateFluxDefer::getFluxOfNumbers);

		// if the following line is uncommented, the publisher will be created and executed.
		// publisher.subscribe(Util.subscriber("Integer Subscriber"));


	}

	private static Flux<Integer> getFluxOfNumbers() {
		logger.info("Creating a flux of numbers");
		var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Util.sleepSeconds(2L);
		return Flux.fromIterable(numbers);
	}
}
