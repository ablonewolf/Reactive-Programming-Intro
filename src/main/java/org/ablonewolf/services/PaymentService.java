package org.ablonewolf.services;

import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Provides a service to manage user payment information, specifically user balances.
 * This class is designed to access and retrieve the balance associated with a
 * given user ID in a reactive and non-blocking manner using Project Reactor's Mono API.
 * <p>
 * The user balance data is maintained as a static in-memory mapping to simulate user
 * balance storage. This implementation is useful in scenarios where reactive programming
 * is required, enabling asynchronous and efficient data retrieval.
 */
public class PaymentService {

	private static final Map<Integer, Integer> userBalanceTable = Map.of(
			1, 100,
			2, 200,
			3, 300
	);

	public static Mono<Integer> getUserBalance(Integer userId) {
		return Mono.fromSupplier(() -> userBalanceTable.get(userId));
	}

}
