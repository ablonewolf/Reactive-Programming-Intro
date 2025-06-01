package org.ablonewolf.services;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Provides order-related services in a reactive manner using Project Reactor.
 * This service works with a static order table to simulate order storage,
 * which maps user IDs to their respective lists of orders.
 * <p>
 * The primary functionality includes retrieving orders for a specific user,
 * with simulated delays and logging.
 */
public class OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	private static final Map<Integer, List<Order>> orderTable = Map.of(
			1, List.of(
					new Order(1, 1, Util.getFaker().commerce().productName(),
							  Util.getFaker().random().nextInt(10, 100)),
					new Order(2, 1, Util.getFaker().commerce().productName(),
							  Util.getFaker().random().nextInt(10, 100))
			),
			2, List.of(
					new Order(3, 2, Util.getFaker().commerce().productName(),
							  Util.getFaker().random().nextInt(10, 100)),
					new Order(4, 2, Util.getFaker().commerce().productName(),
							  Util.getFaker().random().nextInt(10, 100)),
					new Order(5, 2, Util.getFaker().commerce().productName(),
							  Util.getFaker().random().nextInt(10, 100))
			),
			3, List.of()
	);

	public static Flux<Order> getUserOrders(Integer userId) {
		return Flux.fromIterable(orderTable.get(userId))
				.delayElements(Duration.ofMillis(500))
				.transform(Util.getFluxLogger("order-for-user" + userId, log));
	}
}
