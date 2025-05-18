package org.ablonewolf.operators;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.Customer;
import org.ablonewolf.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.UnaryOperator;

/**
 * Demonstrates the use of the transform operator in a reactive programming context.
 * The class retrieves Flux streams of customers and purchase orders, applies a debugging
 * operator, and then subscribes to these streams using a custom subscriber.
 * <p>
 * This class utilizes the following components:<br>
 * - A stream of customer data, wherein each {@link Customer} has a unique ID and a randomly generated full name.<br>
 * - A stream of purchase orders, wherein each {@link PurchaseOrder} contains a product name, price, and quantity.<br>
 * - A debugging utility that logs emitted items, completion, and errors in the streams.
 * <p>
 * The primary purpose of this class is to demonstrate how the transform operator can
 * be used to modify a Flux stream by injecting additional logic (e.g., debugging) while
 * maintaining a functional programming style.
 */
public class DemonstrateTransformOperator {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateTransformOperator.class);

	public static void main(String[] args) {

		getCustomers()
				.transform(addDebugger())
				.subscribe(Util.subscriber("Customer Subscriber"));

		getPurchaseOrders()
				.transform(addDebugger())
				.subscribe(Util.subscriber("Purchase Order Subscriber"));

	}

	/**
	 * Retrieves a Flux stream of customers, each identified with a unique ID and a randomly
	 * generated full name. The stream emits five customers in total.
	 *
	 * @return a {@link Flux} emitting five {@link Customer} objects, each with a unique ID
	 * and a randomly generated name
	 */
	private static Flux<Customer> getCustomers() {
		return Flux.range(1, 5)
				.map(i -> new Customer(i, Util.getFaker().name().fullName()));
	}

	/**
	 * Retrieves a Flux stream of PurchaseOrder objects. Each purchase order contains a
	 * randomly generated product name, a calculated price, and a calculated quantity.
	 * The stream emits ten purchase orders in total, with data derived from random inputs.
	 *
	 * @return a {@link Flux} emitting ten {@link PurchaseOrder} objects, each containing
	 * a product name, price, and quantity
	 */
	private static Flux<PurchaseOrder> getPurchaseOrders() {
		return Flux.range(1, 10)
				.map(i -> new PurchaseOrder(Util.getFaker().commerce().productName(),
						i * Util.getFaker().random().nextInt(1, 10),
						i * Util.getFaker().random().nextInt(11, 30)));
	}

	/**
	 * Adds debugging logic to a reactive Flux stream. The returned operator applies logging
	 * operations to the stream to capture and log the following events:
	 * <p>
	 * - Every emitted item in the stream along with the item's value.<br>
	 * - Successful completion of the stream.<br>
	 * - Any error occurring during stream processing, along with the error message.
	 * <p>
	 * This method is typically used to assist in tracking and debugging the flow of events
	 * within a Flux stream during development or troubleshooting.
	 *
	 * @param <T> the type of the elements emitted by the Flux
	 * @return a {@link UnaryOperator} that transforms a Flux by adding debugging logic for
	 * logging emitted items, completion, and errors
	 */
	private static <T> UnaryOperator<Flux<T>> addDebugger() {
		return flux -> flux.doOnNext(item -> log.info("Received item: {}", item))
				.doOnComplete(() -> log.info("Completed"))
				.doOnError(error -> log.error("Error occurred: {}", error.getMessage()));
	}
}
