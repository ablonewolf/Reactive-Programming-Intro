package org.ablonewolf.batchingOperators;

import org.ablonewolf.common.BookOrderGenerator;
import org.ablonewolf.common.Util;
import org.ablonewolf.model.BookRevenueReport;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * The BookReportGeneration class demonstrates the process of generating and handling book revenue reports
 * using reactive programming constructs. This program streams book orders, filters them based on specific genres,
 * processes them in time-based batches, and generates revenue reports for each batch.
 * <p>
 * Main functionality:<br>
 * - Generates a finite stream of book orders using {@link BookOrderGenerator#getFiniteAmountOfBookOrders(Integer)}.<br>
 * - Filters book orders to include only specific genres such as Science fiction, Fantasy, and Suspense/Thriller.<br>
 * - Buffers book orders into batches over a fixed time duration using
 * {@link Flux#buffer(Duration)}.<br>
 * - Generates a book revenue report for each batch using {@link BookRevenueReport#generateReport(List)}.<br>
 * - Subscribes to the processed stream using a custom subscriber created via {@link Util#subscriber(String)}.<br>
 * - Ensures the main thread continues to run to observe the stream by invoking {@link Util#sleepSeconds(Long)}.
 */
public class BookReportGeneration {

	public static void main(String[] args) {

		var allowedCategories = Set.of("Science fiction", "Fantasy", "Suspense/Thriller");

		BookOrderGenerator.getFiniteAmountOfBookOrders(500)
				.filter(bookOrder -> allowedCategories.contains(bookOrder.genre()))
				.buffer(Duration.ofSeconds(5))
				.map(BookRevenueReport::generateReport)
				.subscribe(Util.subscriber("Book Revenue Report Subscriber"));

		Util.sleepSeconds(32L);
	}
}
