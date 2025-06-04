package org.ablonewolf.model;

import com.github.javafaker.Book;
import org.ablonewolf.common.Util;

/**
 * Represents an order for a book, encapsulating details about the book's genre,
 * title, and price.
 * <p>
 * This record serves as a simple immutable data container for book orders.
 * A utility method is provided to create an instance of BookOrder using a
 * {@code com.github.javafaker.Book} object.
 * <p>
 * Methods:<br>
 * - {@link #genre()}: Returns the genre of the book in the order.<br>
 * - {@link #title()}: Returns the title of the book in the order.<br>
 * - {@link #price()}: Returns the price of the book in the order.<br>
 * - {@link #create(Book)}: Static factory method to
 * generate a BookOrder instance randomly assigning a price between 100 and 1000.
 */
public record BookOrder(
		String genre,
		String title,
		Integer price
) {
	public static BookOrder create(Book book) {
		var genre = book.genre();
		var title = book.title();
		var price = Util.getFaker().random().nextInt(100, 1000);
		return new BookOrder(genre, title, price);
	}
}
