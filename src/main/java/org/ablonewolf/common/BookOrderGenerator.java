package org.ablonewolf.common;

import com.github.javafaker.Book;
import org.ablonewolf.model.BookOrder;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * A utility class that generates a stream of book orders.
 * <p>
 * This class provides an API for producing a finite stream of {@link BookOrder} instances
 * using reactive programming constructs. Book orders are synthesized using random data
 * from a Faker library.
 * <p>
 * The class is final to prevent inheritance and cannot be instantiated.
 */
public final class BookOrderGenerator {

	private BookOrderGenerator() {

	}

	public static Flux<BookOrder> getFiniteAmountOfBookOrders(Integer amount) {
		return Flux.generate(synchronousSink -> {
					var book = Util.getFaker().book();
					synchronousSink.next(book);
				})
				.cast(Book.class)
				.map(BookOrder::create)
				.delayElements(Duration.ofMillis(50))
				.take(amount);
	}

}
