package org.ablonewolf.common;

import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

/**
 * The CountryNameGenerator class is responsible for generating and emitting random country names
 * using a FluxSink as the downstream data consumer in a Reactive Streams setup.
 * This generator can emit either a continuous stream of names culminating upon generating "Canada"
 * or a single name depending on the invoked method.
 * It integrates the {@code Util.getFaker()} utility for random country name generation.
 */
public class CountryNameGenerator implements Consumer<FluxSink<String>> {

	private FluxSink<String> fluxSink;

	@Override
	public void accept(FluxSink<String> fluxSink) {
		this.fluxSink = fluxSink;
	}

	/**
	 * Generates a stream of random country names and emits them to the associated fluxSink.
	 * The generation continues until the name "Canada" (case-insensitive) is generated,
	 * at which point the fluxSink completes.
	 * <p>
	 * This method uses the `Util.getFaker()` utility to generate random country names.
	 * Each generated country name is passed to the FluxSink instance via its `next` method.
	 * Upon emitting "Canada", the `complete` method of the fluxSink is invoked to
	 * signal the completion of the stream.
	 */
	public void generate() {
		String countryName;
		do {
			countryName = Util.getFaker().country().name();
			this.fluxSink.next(countryName);
		} while (!countryName.equalsIgnoreCase("canada"));

		this.fluxSink.complete();
	}

	/**
	 * Generates a single country name using the `Util.getFaker()` utility and emits it
	 * to the associated FluxSink.
	 * The generated country name is sent via the `next` method
	 * of the FluxSink.
	 * <p>
	 * This method is useful for emitting a single country name to a Flux stream created
	 * using the Reactive Streams paradigm.
	 */
	public void generateSingleName() {
		String countryName = Util.getFaker().country().name();
		this.fluxSink.next(countryName);
	}
}
