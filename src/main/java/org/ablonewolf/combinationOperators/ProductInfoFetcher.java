package org.ablonewolf.combinationOperators;

import org.ablonewolf.common.Util;
import org.ablonewolf.services.impl.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ProductInfoFetcher class demonstrates the use of reactive programming to fetch product information
 * from an external service for a series of product IDs.
 * It utilizes reactive streams to process asynchronous data and logs the received product information.
 * <p>
 * This class relies on the {@link ExternalServiceClient} for communicating with the external service and
 * retrieving the product details.
 * It also makes use of the {@link  Util} class for utility functions like
 * pausing the main thread to allow asynchronous operations to complete.
 * <p>
 * Features:<br>
 * - Fetches product information for a series of product IDs (1 to 10 in this example).<br>
 * - Logs the retrieved product information for each product.<br>
 * - Demonstrates the use of reactive streams and their integration with logging mechanisms.
 * <p>
 * The main thread is paused to allow the reactive stream to complete processing.
 */
public class ProductInfoFetcher {

	private static final Logger log = LoggerFactory.getLogger(ProductInfoFetcher.class);

	public static void main(String[] args) {

		var serviceClient = new ExternalServiceClient();

		for (int i = 1; i <= 10; i++) {
			final int productId = i;
			serviceClient.getProductInfo(productId)
					.subscribe(productInfo -> log.info("Product info for product {} is {}", productId,
													   productInfo));
		}

		Util.sleepSeconds(2L);
	}
}
