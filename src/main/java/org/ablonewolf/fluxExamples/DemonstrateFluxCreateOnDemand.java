package org.ablonewolf.fluxExamples;

import org.ablonewolf.basic.subscriber.StringSubscriber;
import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * Demonstrates the use of Flux.create to generate a dynamic stream of data on demand.
 * The class showcases how to leverage backpressure and manual control in the data stream
 * generation process.
 * <p>
 * Key Features:
 * - Uses a custom subscriber, {@code StringSubscriber}, to consume the flux stream.
 * - Generates random country names dynamically based on subscription requests.
 * - Handles backpressure by generating a specific amount of items on demand.
 * - Provides cancellation handling to stop data emission upon subscriber cancellation.
 * - Utilizes {@code Util} for utilities such as thread sleeping and data generation support.
 * <p>
 * Primary use case:
 * This class is best used to understand and demonstrate how Flux.create integrates with reactive
 * systems for dynamic data generation while respecting reactive streams specifications like
 * backpressure and cancellation, especially in cases where the subscriber requests for items
 * as Flux.create function publishes items eagerly without they are being explicitly requested.
 * <p>
 * Methods:
 * - {@code main}: Sets up the subscriber, links it to the Flux publisher, and orchestrates the
 * request-response cycle between the subscriber and the publisher.
 * - {@code getCountryNamePublisher}: Returns a Flux publisher that generates country names
 * dynamically when the subscriber requests data.
 */
public class DemonstrateFluxCreateOnDemand {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateFluxCreateOnDemand.class);

	public static void main(String[] args) {
		var countryNameSubscriber = new StringSubscriber();

		var countryNamePublisher = getCountryNamePublisher();

		countryNamePublisher.subscribe(countryNameSubscriber);

		Util.sleepSeconds(2L);
		countryNameSubscriber.getSubscription().request(3);

		Util.sleepSeconds(2L);
		countryNameSubscriber.getSubscription().request(3);

		Util.sleepSeconds(2L);
		countryNameSubscriber.getSubscription().cancel();
		// the following line won't be executed since we already canceled the subscription
		countryNameSubscriber.getSubscription().request(3);

	}

	private static Flux<String> getCountryNamePublisher() {
		return Flux.create(fluxSink -> fluxSink.onRequest(request -> {
			for (int i = 0; i < request && !fluxSink.isCancelled(); i++) {
				var name = Util.getFaker().country().name();
				log.info("Generating Country Name: {}", name);
				fluxSink.next(name);
			}
			if (fluxSink.isCancelled()) {
				fluxSink.complete();
			}
		}));
	}
}
