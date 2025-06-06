package org.ablonewolf.services;

import org.ablonewolf.model.ItemOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Provides services for processing orders categorized by specific domains,
 * such as "Kids" and "Automotive", in a reactive and non-blocking manner.
 * This service utilizes Project Reactor's Flux and Mono APIs for reactive programming.
 * <p>
 * Responsibilities include:<br>
 * - Determining whether an order category can be processed.<br>
 * - Retrieving the processing logic for a given category.<br>
 * - Defining specialized processing logic for each category.
 * <p>
 * The process flow for each category is as follows:<br>
 * - "Kids": Applies promotional logic to add a free order for each order in this category.<br>
 * - "Automotive": Adjusts the price of each order by adding 100 USD.
 */
public class OrderProcessingService {

	private static final Map<String, UnaryOperator<Flux<ItemOrder>>> ORDER_PROCESSING_MAP = Map.of(
			"Kids", processKidOrders(),
			"Automotive", processAutomotiveOrders()
	);

	public static Predicate<ItemOrder> canProcess() {
		return itemOrder -> ORDER_PROCESSING_MAP.containsKey(itemOrder.category());
	}

	public static UnaryOperator<Flux<ItemOrder>> getProcessor(String category) {
		return ORDER_PROCESSING_MAP.get(category);
	}

	private static UnaryOperator<Flux<ItemOrder>> processAutomotiveOrders() {
		return itemOrder ->
				itemOrder.map(order -> new ItemOrder(order.item(), order.category(), order.price() + 100));
	}

	private static UnaryOperator<Flux<ItemOrder>> processKidOrders() {
		return kidOrder -> kidOrder
				.flatMap(order -> getFreeKidsOrder(order).flux().startWith(order));
	}

	private static Mono<ItemOrder> getFreeKidsOrder(ItemOrder order) {
		return Mono.fromSupplier(() -> new ItemOrder(order.item() + "-FREE", order.category(), 0));
	}
}
