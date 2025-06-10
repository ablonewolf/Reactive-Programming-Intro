package org.ablonewolf.sinks;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * The DemonstrateSinkMulticast class demonstrates the usage of a multicast {@link Sinks.Many} from Project Reactor,
 * illustrating how to emit items to multiple subscribers in a shared fashion.
 * It highlights the behavior of multicast sinks, which allow any number of subscribers to receive emitted items,
 * but only after they have subscribed — late subscribers do not receive previously emitted data.
 * <p>
 * A multicast sink buffers items until at least one subscriber is connected. Unlike unicast sinks, it supports multiple
 *  subscribers but does not replay past emissions to new subscribers. This makes it suitable for use cases like
 * event broadcasting.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a multicast sink using the chained method call:
 * {@link Sinks#many()}.{@link Sinks.ManySpec#multicast()}.{@link Sinks.MulticastSpec#onBackpressureBuffer()}.<br>
 * 2. Emitting items before and after subscription to observe buffering and delivery behavior.<br>
 * 3. Subscribing multiple consumers (Sam, John, Bob) and showing that late subscribers miss earlier emissions(Except
 * for the first subscriber).<br>
 * 4. Completing the sink gracefully with {@link Sinks.Many#tryEmitComplete()} after all desired items are emitted.
 * <p>
 * Dependencies:<br>
 * - Uses the {@link Util} class to create and configure subscribers with descriptive labels.<br>
 * - Relies on Reactor's {@link Flux} and {@link Sinks} API to model the reactive stream and manage manual emissions.
 * <p>
 */
public class DemonstrateSinkMulticast {

	public static void main(String[] args) {
		// this sink is a medium through which we will emit items
		// unlike unicast, this will be a bounded queue with a fixed size
		Sinks.Many<String> messageSink = Sinks.many().multicast().onBackpressureBuffer();

		Flux<String> messageFlux = messageSink.asFlux();
		messageSink.tryEmitNext("Is there anyone out there?");

		// Since Sam is the first subscriber, he will receive the first message even though this message has been
		// emitted already.
		// As we used "onBackpressureBuffer", all messages will be stored into the buffer if there is no subscriber.
		messageFlux.subscribe(Util.subscriber("Sam", "message"));
		messageSink.tryEmitNext("Hello");

		// John will not receive the above message since he subscribed to the sink after the message is already emitted
		messageFlux.subscribe(Util.subscriber("John", "message"));
		messageSink.tryEmitNext("How's it going?");

		Util.sleepSeconds(2L);

		messageFlux.subscribe(Util.subscriber("Bob", "message"));
		// bob will only receive the following message since he subscribed to the sink later; after those messages
		// were emitted already.
		messageSink.tryEmitNext("Goodbye");
		messageSink.tryEmitComplete();
	}
}
