package org.ablonewolf.sinks;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * The DemonstrateSinkUnicast class demonstrates the usage of a unicast {@link Sinks.Many} from Project Reactor,
 * illustrating how to manually emit items to a single subscriber in a controlled manner.
 * It highlights the characteristics and limitations of unicast sinks, which are designed to support exactly one subscriber.
 * <p>
 * A unicast sink buffers all emitted items until a subscriber attaches to it. Once a subscriber connects,
 * it receives the entire buffered sequence. This type of sink is useful when you want to ensure that a single consumer
 * receives all previously emitted data from the beginning.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a unicast sink using the chained method call:
 * {@link Sinks#many()}.{@link Sinks.ManySpec#unicast()}.{@link Sinks.UnicastSpec#onBackpressureBuffer()}.<br>
 * 2. Emitting multiple items before subscription using {@link Sinks.Many#tryEmitNext(Object)}.<br>
 * 3. Subscribing a single consumer to observe the buffered sequence.<br>
 * 4. Demonstrating the restriction that unicast sinks allow only one active subscriber at a time — attempting to add
 * a second subscriber results in an error.
 * <p>
 * Dependencies:<br>
 * - Uses {@link Util} to create and configure subscribers with custom logging behavior.<br>
 * - Relies on Reactor's {@link Flux} and {@link Sinks} to model the reactive data stream and manual emissions.
 * <p>
 */
public class DemonstrateSinkUnicast {

	public static void main(String[] args) {

		// this sink is a medium through which we will emit items
		Sinks.Many<String> messageSink = Sinks.many().unicast().onBackpressureBuffer();

		Flux<String> messageFlux = messageSink.asFlux();

		messageSink.tryEmitNext("Hi?");
		messageSink.tryEmitNext("How's it going?");
		messageSink.tryEmitComplete();

		messageFlux.subscribe(Util.subscriber("Sam", "message"));

		// this will create an error since in Unicast mode, only one subscriber is allowed.
		messageFlux.subscribe(Util.subscriber("John", "message"));

	}
}
