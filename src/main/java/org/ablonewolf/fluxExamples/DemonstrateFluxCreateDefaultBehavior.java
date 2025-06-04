package org.ablonewolf.fluxExamples;

import org.ablonewolf.basic.subscriber.StringSubscriber;
import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * DemonstrateFluxCreateDefaultBehavior showcases the utilization of the `Flux.create` method to create a Flux
 * that emits a sequence of random first names, demonstrating the default behavior when interacting with a subscriber.
 * <p>
 * The created Flux will emit 10 first names synchronously, followed by a completion signal. A custom subscriber
 * (`StringSubscriber`) is used to showcase handling of the emitted items, and backpressure is demonstrated by
 * requesting items in chunks after a delay.
 * <p>
 * When using the create function, the Flux will publish the items without the items being demanded by a subscriber.
 * It will remain in the queue until a subscriber has requested for the items or has canceled the subscription.
 * <p>
 * Key features demonstrated:
 * - Synchronous emission of data using `Flux.create`.
 * - Implementation of custom subscriber with the `Subscriber` interface.
 * - Use of backpressure by requesting a specific number of items at a time.
 * - Subscription lifecycle, including subscription, item request, and cancellation.
 * <p>
 * Logs emitted data and tracks subscriber interactions via SLF4J.
 */
public class DemonstrateFluxCreateDefaultBehavior {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateFluxCreateDefaultBehavior.class);

	public static void main(String[] args) {

		var nameSubscriber = new StringSubscriber();

		Flux.<String>create(fluxSink -> {
			for (int i = 0; i < 10; i++) {
				var name = Util.getFaker().name().firstName();
				log.info("Emitting name: {}", name);
				fluxSink.next(name);
			}
			fluxSink.complete();
		}).subscribe(nameSubscriber);

		Util.sleepSeconds(2L);
		nameSubscriber.getSubscription().request(3);
		Util.sleepSeconds(2L);
		nameSubscriber.getSubscription().request(3);
		Util.sleepSeconds(2L);
		nameSubscriber.getSubscription().cancel();


	}
}
