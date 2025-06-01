package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.ablonewolf.services.OrderService;
import org.ablonewolf.services.UserService;

/**
 * Demonstrates the use of `flatMap` in Project Reactor's Flux to process data in a reactive pipeline.
 * <p>
 * This class showcases a multistep reactive chain where:<br>
 * - A stream of users is retrieved from {@link UserService#getAllUsers()}.<br>
 * - Each user's username is mapped to their unique user ID through {@link UserService#getUserId(String)}.<br>
 * - Using the obtained user ID, the orders associated with the user are fetched via
 * {@link OrderService#getUserOrders(Integer)}.
 * <p>
 * The final subscription consumes and logs the resulting orders for each user.
 * The implementation uses a custom subscriber created with {@link Util#subscriber(String)} to observe the emitted data.
 * <p>
 * The program execution is paused for a specified duration using {@link Util#sleepSeconds(Long)} to allow sufficient
 * time for reactive streams to complete their operations.
 */
public class DemonstrateFlatmapInFlux {

	public static void main(String[] args) {

		UserService.getAllUsers()
				.flatMap(user -> UserService.getUserId(user.username()))
				.flatMap(OrderService::getUserOrders)
				.subscribe(Util.subscriber("User Orders Subscriber"));

		Util.sleepSeconds(3L);
	}
}
