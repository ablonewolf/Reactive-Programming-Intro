package org.ablonewolf.batching;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.ItemOrder;
import org.ablonewolf.services.OrderProcessingService;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * A class that demonstrates order processing using grouping by categories in a reactive programming paradigm.
 * <p>
 * This class uses Project Reactor to stream a sequence of randomly generated orders, filters them by categories
 * that can be processed, groups them by their categories, and applies custom processing logic for each group
 * based on its category. The processed orders are then subscribed to for further handling.
 * <p>
 * Key Operations:<br>
 * - Streams orders at regular intervals using a reactive Flux.<br>
 * - Filters orders using a predicate that determines whether a given category is processable.<br>
 * - Groups orders by their respective categories.<br>
 * - Applies category-specific processing logic using defined transformations.<br>
 * - Subscribes to handle the processed results.
 * <p>
 * Internal Functionalities:<br>
 * - {@code orderStream()}: Generates a stream of random orders continuously.<br>
 * - Filters and processes orders in categories defined in {@link OrderProcessingService}.<br>
 * - Transforms grouped orders using the processing logic retrieved for each category.<br>
 * - Subscribes to the processed orders with a custom subscriber.
 * <p>
 * Dependencies:<br>
 * - The class relies on the {@link OrderProcessingService} for processing logic and category determination.<br>
 * - The {@link ItemOrder} class is used to model the orders.<br>
 * - Utility methods from {@link Util} are used for subscription and thread sleep handling.
 */
public class OrderProcessingUsingGroupBy {

	public static void main(String[] args) {

		orderStream()
				.filter(OrderProcessingService.canProcess())
				.groupBy(ItemOrder::category)
				.flatMap(groupedFlux ->
								 groupedFlux.transform(OrderProcessingService.getProcessor(groupedFlux.key())))
				.subscribe(Util.subscriber("Order Processing Subscriber"));

		Util.sleepSeconds(30L);
	}

	private static Flux<ItemOrder> orderStream() {
		return Flux.interval(Duration.ofMillis(100))
				.map(i -> ItemOrder.create());
	}
}
