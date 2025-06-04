package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.User;
import org.ablonewolf.model.UserInformation;
import org.ablonewolf.services.OrderService;
import org.ablonewolf.services.PaymentService;
import org.ablonewolf.services.UserService;
import reactor.core.publisher.Mono;

/**
 * The UserInfoBuilder class is designed to retrieve and aggregate user information
 * using reactive streams provided by Project Reactor.
 * <p>
 * The class demonstrates fetching user data, including orders and account balance,
 * for each user in a non-blocking and asynchronous manner. It combines data from
 * multiple services into a consolidated UserInformation object.
 * <p>
 * Key Components:<br>
 * - Retrieves all users via the {@link UserService#getAllUsers()} method, which returns a stream of User objects.<br>
 * - Fetches user-specific orders and balance by invoking {@link OrderService#getUserOrders} and
 * {@link PaymentService#getUserBalance}, respectively.<br>
 * - Combines the user's order list and balance into a {@link UserInformation} record using the Project Reactor
 * {@link Mono#zip} operator.<br>
 * - Utilizes a subscriber created using the {@link Util#subscriber} method to process the aggregated
 * user information stream.<br>
 * - Introduces artificial delays for processing and simulates asynchronous behavior in a reactive pipeline.
 * <p>
 * The main method orchestrates the overall workflow:<br>
 * - It processes the stream of users, transforms user details into their corresponding aggregated
 * information, and subscribes to the final stream to process and display the results.<br>
 * - Pauses the main thread temporarily using {@link Util#sleepSeconds} to allow the reactive stream
 * to complete processing.
 */
public class UserInfoBuilder {

	public static void main(String[] args) {

		UserService.getAllUsers()
				.flatMap(UserInfoBuilder::getUserInfo)
				.subscribe(Util.subscriber("User Information Subscriber"));

		Util.sleepSeconds(2L);
	}

	private static Mono<UserInformation> getUserInfo(User user) {
		return Mono.zip(OrderService.getUserOrders(user.id()).collectList(), PaymentService.getUserBalance(user.id()))
				.map(tuple -> new UserInformation(user.id(),
												  user.username(), tuple.getT2(), tuple.getT1()));
	}

}
