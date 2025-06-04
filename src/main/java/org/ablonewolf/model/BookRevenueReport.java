package org.ablonewolf.model;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a revenue report for book orders, including the time of generation
 * and a breakdown of revenue by book genre.
 * <p>
 * This record is an immutable data structure for capturing and returning a snapshot
 * of revenue data for book orders. The revenue data is aggregated by book genre
 * and calculated based on the pricing of individual book orders. A utility method
 * is provided for generating a report from a list of book orders.
 * <p>
 * The static method `generateReport` can be used to create an instance of this report
 * by processing a list of book orders, grouping them by genre, and summing up the revenue for each genre.
 * <p>
 * Methods:<br>
 * - {@link BookRevenueReport#generateReport(List)}: Generates a revenue report
 * using the provided list of {@link BookOrder} instances.
 */
public record BookRevenueReport(
		LocalTime time,
		Map<String, Integer> revenueReport
) {
	public static BookRevenueReport generateReport(List<BookOrder> bookOrders) {
		var revenue = bookOrders.stream()
				.collect(Collectors.groupingBy(
						BookOrder::genre,
						Collectors.summingInt(BookOrder::price))
				);
		return new BookRevenueReport(LocalTime.now(), revenue);
	}
}
