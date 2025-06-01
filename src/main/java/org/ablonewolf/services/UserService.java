package org.ablonewolf.services;

import org.ablonewolf.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Provides services related to user management and data retrieval.
 * This class includes methods to fetch user details in a reactive manner
 * using Project Reactor's Flux and Mono APIs.
 * <p>
 * The user data is maintained as a static mapping, which acts as
 * an in-memory user database.
 */
public class UserService {

	private static final Map<String, Integer> userTable = Map.of(
			"sam", 1,
			"mike", 2,
			"jake", 3
	);

	public static Flux<User> getAllUsers() {
		return Flux.fromIterable(userTable.entrySet())
				.map(entry -> new User(entry.getValue(), entry.getKey()));
	}

	public static Mono<Integer> getUserId(String username) {
		return Mono.fromSupplier(() -> userTable.get(username));
	}
}
