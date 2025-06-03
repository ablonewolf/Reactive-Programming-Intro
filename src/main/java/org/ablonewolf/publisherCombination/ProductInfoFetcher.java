package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.ablonewolf.services.impl.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that demonstrates fetching product information for a series of product IDs
 * using an external service client and logging the results.
 * <p>
 * The class uses the {@link ExternalServiceClient} to fetch information about
 * 10 products sequentially. It retrieves the product information using the
 * {@code getProductInfo} method of the service client, which aggregates product
 * name, price, and review data.
 * <p>
 * Logs the fetched product information for each product ID and ensures sufficient
 * time for asynchronous operations to complete by pausing the main thread using
 * the {@link Util#sleepSeconds(Long)} method.
 * <p>
 * Key processing steps:
 * - Loops through product IDs from 1 to 10.
 * - Fetches product information using the service client.
 * - Logs the product information asynchronously.
 * - Pauses the thread to allow reactive completion.
 */
public class ProductInfoFetcher {

	private static final Logger log = LoggerFactory.getLogger(ProductInfoFetcher.class);

	public static void main(String[] args) {
		var serviceClient = new ExternalServiceClient();

		for (int i = 1; i <= 10; i++) {
			final int id = i;
			serviceClient.getProductInfo(id)
					.subscribe(productInfo -> log.info("Product info for product with id {}: {}",
													   id, productInfo));
		}

		Util.sleepSeconds(2L);
	}
}
