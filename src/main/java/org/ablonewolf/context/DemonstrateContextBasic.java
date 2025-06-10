package org.ablonewolf.context;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * The DemonstrateContextBasic class illustrates the basic usage of Reactor's {@link Context} to pass contextual data,
 * such as user information or request metadata, through a reactive pipeline.
 * It demonstrates how to associate key-value pairs with a reactive sequence and access them downstream during
 * processing.
 * <p>
 * Reactor's context is similar to thread-local storage but designed for use in reactive, non-blocking pipelines.
 * This example shows how to conditionally provide a welcome message based on the presence of a username in the context.
 * <p>
 * Key demonstrations include:<br>
 * 1. Using {@link Mono#deferContextual(java.util.function.Function)} to create a context-aware publisher.<br>
 * 2. Injecting contextual data using {@link Mono#contextWrite(java.util.function.Function)} with a custom key-value
 * pair.<br>
 * 3. Handling cases where the expected context key is missing by returning an error with a descriptive message.<br>
 * 4. Subscribing with and without context to observe different behaviors based on available contextual information.
 * <p>
 * Dependencies:<br>
 * - Uses the {@link Util} class to create and configure subscribers with descriptive labels.<br>
 * - Relies on Reactor's {@link Mono}, {@link reactor.util.context.Context}, and related APIs to manage context
 * propagation.
 * <p>
 */
public class DemonstrateContextBasic {

	public static void main(String[] args) {

		// through context, we can provide some metadata like we share some information
		// in the headers of HTTP requests
		getWelcomeMessage()
				.contextWrite(Context.of("username", "John"))
				.subscribe(Util.subscriber("Message Subscriber"));

		getWelcomeMessage()
				.subscribe(Util.subscriber("Message Subscriber"));
	}

	private static Mono<String> getWelcomeMessage() {
		return Mono.deferContextual(context -> {
			if (context.hasKey("username")) {
				return Mono.defer(() -> Mono.just("Hello " + context.get("username")));
			}
			return Mono.error(new RuntimeException("username required"));
		});
	}
}
