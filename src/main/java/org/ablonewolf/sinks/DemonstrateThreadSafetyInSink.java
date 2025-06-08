package org.ablonewolf.sinks;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * The DemonstrateThreadSafetyInSink class demonstrates how to ensure thread-safe emissions using a unicast
 * {@link Sinks.Many} from Project Reactor.
 * It highlights the use of the {@link Sinks.Many#emitNext(Object, Sinks.EmitFailureHandler)} method to safely
 * emit items concurrently from multiple threads without causing race conditions or data loss.
 * <p>
 * A key feature of this demonstration is the use of backpressure-aware buffering and synchronized emission logic,
 * which ensures that all concurrent writes to the sink are serialized properly. This makes it safe to use sinks
 * in multithreaded environments such as asynchronous task execution with {@link CompletableFuture}.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a unicast sink using the chained method call:
 * {@link Sinks#many()}.{@link Sinks.ManySpec#unicast()}.{@link Sinks.UnicastSpec#onBackpressureBuffer()}.<br>
 * 2. Subscribing to the sink's {@link Flux} and collecting emitted values into a shared list.<br>
 * 3. Emitting items concurrently from multiple threads using {@link CompletableFuture#runAsync(Runnable)}.<br>
 * 4. Ensuring thread safety by using the {@code emitNext} method with a retry predicate that handles the
 * * {@link Sinks.EmitResult#FAIL_NON_SERIALIZED} signal.<br>
 * 5. Logging the final count of received items to verify a successful and complete emission.
 * <p>
 * Dependencies:<br>
 * - Uses the {@link Util} class to generate random country names and create a configured subscriber.<br>
 * - Relies on SLF4J's {@link Logger} for logging the final result.<br>
 * - Uses Java's {@link ArrayList} and {@link Objects} utilities to collect and handle emitted data.<br>
 * - Leverages {@link CompletableFuture} to simulate concurrent emissions across multiple threads.
 * <p>
 */
public class DemonstrateThreadSafetyInSink {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateThreadSafetyInSink.class);

	public static void main(String[] args) {

		Sinks.Many<String> countrySink = Sinks.many().unicast().onBackpressureBuffer();
		Flux<String> counterFlux = countrySink.asFlux();

		List<String> nameList = new ArrayList<>();
		counterFlux.subscribe(nameList::add);

		for (int i = 0; i < 300; i++) {
			String countryName = Util.getFaker().country().name();

			CompletableFuture.runAsync(() -> {
				// using emitNext will ensure the synchronization among all threads
				// the EmitResult Fail non-serialized means that the emitResult will fail when the items are not
				// serialized
				// if we use tryEmitNext, it will not ensure the success of the next item emission
				countrySink.emitNext(countryName, ((signalType, emitResult) ->
						Objects.equals(Sinks.EmitResult.FAIL_NON_SERIALIZED, emitResult)));
			});
		}

		Util.sleepSeconds(2L);

		log.info("{} country names received.", nameList.size());
	}
}
