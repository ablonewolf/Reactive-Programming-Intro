package org.ablonewolf.flux.operators;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * Demonstrates the use of the handle operator in Project Reactor's Flux API.
 * <p>
 * The handle operator provides a mechanism to transform or filter the elements in Flux
 * based on a custom business logic defined within the handle method.
 * <p>
 * In this example:
 * - A range of integers from 1 to 50 are emitted by the Flux. <br>
 * - The handle method processes each emitted item, applying transformation logic
 * based on whether the item is even or odd. <br>
 * - If the item is even, its square is emitted downstream. <br>
 * - If the item is odd, its value is multiplied by 3 and emitted downstream. <br>
 * - The sink is also capable of completing the flux or ignoring specific values. <br>
 * - The resulting sequence is cast to an Integer to match type requirements for subscription. <br>
 * - The transformed flux is subscribed to using a custom subscriber implementation
 * defined via the {@code Util.subscriber} method, which displays the processed items
 * in the console.
 */
public class DemonstrateHandleOperator {

	public static void main(String[] args) {

		Flux.range(1, 50)
				.handle((item, sink) -> {
					switch (item % 2) {
						case 0 -> sink.next(item * item);
						case 1 -> sink.next(item * 3);
						default -> sink.complete();
					}
				})
				.cast(Integer.class)
				.subscribe(Util.subscriber("Integer Subscriber"));
	}
}
