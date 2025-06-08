package org.ablonewolf.sinks;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

/**
 * The DemonstrateSinkOne class illustrates the usage of {@link Sinks.One} from Project Reactor,
 * showcasing how to manually control value emissions in a one-to-one communication scenario.
 * It demonstrates the flexibility and control offered by sinks in managing Mono-based data streams,
 * including emitting values, handling errors, and reacting to emission outcomes.
 * <p>
 * A {@code Sinks.One} represents a sink that can emit at most one value or an error signal to a single subscriber.
 * This makes it ideal for use cases where a result (success or failure) must be programmatically signaled,
 * such as in request-response patterns or deferred completion scenarios.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a Mono from a sink using {@link Sinks.One#asMono()}.<br>
 * 2. Subscribing multiple subscribers to observe the behavior of Mono when only one can receive the emitted value.<br>
 * 3. Emitting a value using {@link Sinks.One#tryEmitValue(Object value)} and observing how late subscribers miss the
 * signal.<br>
 * 4. Using the advanced {@link Sinks.One#emitValue(Object, Sinks.EmitFailureHandler)} method to gain
 * fine-grained control over emission attempts, including logging of signals and results.<br>
 * 5. Demonstrating error signaling with {@link Sinks.One#tryEmitError(Throwable)} to propagate exceptions through
 * the sink.
 * <p>
 * Dependencies:<br>
 * - The {@link Util} class is used for creating configured subscribers and simulating common reactive operations.<br>
 * - The SLF4J {@link Logger} is used for logging emission results and debugging information.
 * <p>
 */
public class DemonstrateSinkOne {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateSinkOne.class);

	public static void main(String[] args) {
		Sinks.One<String> messageSink = Sinks.one();

		var mono = messageSink.asMono();
		mono.subscribe(Util.subscriber("Sam", "message"));
		mono.subscribe(Util.subscriber("John", "message"));

		// this sink will emit value even if there is no subscriber out there to subscribe to this sink
		messageSink.tryEmitValue("Hello World");

		// this will result in an error since the publisher is Mono, and we cannot emit more than one value from a
		// Mono.
		// but the following strategy gives us the better control over the emission of values; we can log the type of
		// signal along with the emitResult; we can also return the state of the emitResult.
		messageSink.emitValue("Hi there", (((signalType, emitResult) -> {
			log.info("Signal :{}, emitResult: {}", signalType.name(), emitResult.name());
			return emitResult.isSuccess();
		})));

		Sinks.One<String> errorSink = Sinks.one();

		mono = errorSink.asMono();
		mono.subscribe(Util.subscriber("Sam", "message"));

		errorSink.tryEmitError(new RuntimeException("Error"));
	}
}
