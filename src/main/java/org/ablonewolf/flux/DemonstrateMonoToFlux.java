package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * demonstrating how to convert a Mono to a Flux
 */
public class DemonstrateMonoToFlux {

	public static void main(String[] args) {

		var username = getUsername(2);
		Flux.from(username)
				.subscribe(Util.subscriber("Username Subscriber"));
	}

	private static Mono<String> getUsername(Integer userId) {
		return switch (userId) {
			case 1 -> Mono.just("John");
			case 2 -> Mono.just("Jane");
			case 3 -> Mono.empty();
			default -> Mono.error(new RuntimeException("Invalid User ID: " + userId));
		};
	}

}
