package org.ablonewolf.sinks;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * The DemonstrateSinkReplay class demonstrates the usage of a replay {@link Sinks.Many} from Project Reactor,
 * illustrating how to emit items that are stored and later replayed to both current and future subscribers.
 * It highlights the behavior of replay sinks, which maintain a history of emitted items and deliver them to any new
 * subscriber.
 * <p>
 * A replay sink is useful in scenarios where late subscribers must receive previously emitted data.
 * This class demonstrates two configurations: one with unlimited replay (all items), and another with limited replay
 * (only the latest item).
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating an unbounded replay sink using:
 * {@link Sinks#many()}.{@link Sinks.ManySpec#replay()}.{@link Sinks.MulticastReplaySpec#all()})
 * .<br>
 * 2. Emitting items before and after the subscription to show that all subscribers — even late ones — receive the full
 * sequence.<br>
 * 3. Subscribing multiple consumers (Sam, John, Bob) and showing that each receives the entire stream of emissions.<br>
 * 4. Creating a bounded replay sink using:
 * {@link Sinks#many()}.{@link Sinks.ManySpec#replay()}.{@link Sinks.MulticastReplaySpec#limit(int historySize)}).<br>
 * 5. Demonstrating that when a replay is limited to one, only the most recent item is available to late subscribers.
 * <p>
 * Dependencies:<br>
 * - Uses the {@link Util} class to create and configure subscribers with descriptive labels and custom logging
 * behavior.<br>
 * - Relies on Reactor's {@link Flux} and {@link Sinks} API to model the reactive stream and manage manual emissions.
 * <p>
 */
public class DemonstrateSinkReplay {

	public static void main(String[] args) {
		// When we use the replay operator, we will be storing the messages for all the present and the future
		// subscribers inside an unbounded queue
		Sinks.Many<String> messageSink = Sinks.many().replay().all();

		Flux<String> messagePublisher = messageSink.asFlux();
		messageSink.tryEmitNext("Is there anyone out there?");

		// all the following subscribers will receive all messages no matter when they subscribed to the publisher
		messagePublisher.subscribe(Util.subscriber("Sam", "message"));
		messageSink.tryEmitNext("Hello");

		messagePublisher.subscribe(Util.subscriber("John", "message"));
		messageSink.tryEmitNext("How's it going?");

		Util.sleepSeconds(2L);

		messagePublisher.subscribe(Util.subscriber("Bob", "message"));

		messageSink.tryEmitNext("Goodbye");
		messageSink.tryEmitComplete();

		// through the following way, we can define at most how many items the late subscribers can receive
		messageSink = Sinks.many().replay().limit(1);

		messagePublisher = messageSink.asFlux();
		messageSink.tryEmitNext("Is there anyone out there?");


		messagePublisher.subscribe(Util.subscriber("Sam", "message"));
		messageSink.tryEmitNext("Hello");

		messagePublisher.subscribe(Util.subscriber("John", "message"));
		messageSink.tryEmitNext("How's it going?");

		Util.sleepSeconds(2L);

		// this subscriber would get to see only the latest message from history since we have specified the
		// limit count to be one.
		messagePublisher.subscribe(Util.subscriber("Bob", "message"));

		messageSink.tryEmitNext("Goodbye");
		messageSink.tryEmitComplete();
	}
}
