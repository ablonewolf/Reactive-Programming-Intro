package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

public class DemonstrateFluxCreate {

	public static void main(String[] args) {

		Flux.create(fluxSink -> {
					String country;
					do {
						country = Util.getFaker().country().name();
						fluxSink.next(country);
					} while (!country.equalsIgnoreCase("canada"));
					fluxSink.complete();
				})
				.subscribe(Util.subscriber("Country Subscriber"));
	}
}
