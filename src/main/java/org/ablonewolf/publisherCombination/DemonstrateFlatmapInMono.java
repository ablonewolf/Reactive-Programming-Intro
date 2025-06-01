package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.ablonewolf.services.OrderService;
import org.ablonewolf.services.PaymentService;
import org.ablonewolf.services.UserService;

/**
 * Demonstrates the use of Project Reactor's `flatMap` method with `Mono` to compose
 * and transform reactive streams.
 * <p>
 * This class illustrates the following reactive scenarios:<br>
 * - Retrieving a user's ID based on their username using {@link UserService#getUserId(String)}.<br>
 * - Using the `flatMap` operator to fetch the user's balance asynchronously via
 * {@link PaymentService#getUserBalance(Integer)}.<br>
 * - Using the `flatMapMany` operator to retrieve multiple orders for a user via
 * {@link OrderService#getUserOrders(Integer)}.<br>
 * - Subscribing to the reactive streams created during these processes with a custom subscriber from
 * {@link Util#subscriber(String)}.
 * <p>
 * The main thread is paused for 2 seconds using {@link Util#sleepSeconds(Long)} to allow asynchronous operations to
 * complete.
 */
public class DemonstrateFlatmapInMono {

	public static void main(String[] args) {

		UserService.getUserId("sam")
				.flatMap(PaymentService::getUserBalance) // flatmap is used to flatten the inner subscriber
				.subscribe(Util.subscriber("User Balance Subscriber"));

		UserService.getUserId("mike")
				.flatMapMany(OrderService::getUserOrders)
				.subscribe(Util.subscriber("Order Subscriber"));

		Util.sleepSeconds(2L);
	}
}
