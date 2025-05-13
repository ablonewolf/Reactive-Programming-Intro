package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class DemonstrateFluxCreate {

	public static void main(String[] args) {

		var countryNameGenerator = new CountryNameGenerator();
		var countryFlux = Flux.create(countryNameGenerator);

		countryFlux.subscribe(Util.subscriber("Country Subscriber"));

		countryNameGenerator.generate();
	}

	private static class CountryNameGenerator implements Consumer<FluxSink<String>> {

		private FluxSink<String> fluxSink;

		@Override
		public void accept(FluxSink<String> fluxSink) {
			this.fluxSink = fluxSink;
		}

		public void generate() {
			String countryName;
			do {
				countryName = Util.getFaker().country().name();
				this.fluxSink.next(countryName);
			} while (!countryName.equalsIgnoreCase("canada"));

			this.fluxSink.complete();
		}
	}
}
