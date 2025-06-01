package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.ProductInfo;
import org.ablonewolf.services.impl.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * The ProductInfoFetcher class demonstrates the use of Reactive Streams to fetch
 * and combine product information from an external service using zipping.
 * <p>
 * It retrieves product data, including the name, price, and review, for a range of product IDs.
 * The product data is fetched in a non-blocking reactive manner by zipping together the streams
 * for each field (name, price, and review). The resulting product information is encapsulated into
 * ProductInfo instances, which are then processed by a defined subscriber.
 * <p>
 * The class utilizes an external service client {@link ExternalServiceClient} to fetch the data
 * and makes use of utility methods from the {@link Util} class to manage delays.
 * <p>
 * This implementation is intended to be an example of how to combine multiple reactive streams
 * using the Project Reactor library's {@link Mono#zip(Mono, Mono, Mono)} method.
 * <p>
 * Main Responsibilities:<br>
 * - Fetch product names, prices, and reviews from an external API for a range of product IDs.<br>
 * - Combine the fetched data (name, price, and review) into a {@link ProductInfo} record.<br>
 * - Process the combined product information through a reactive stream subscriber.
 * <p>
 * Dependencies:<br>
 * - Requires an {@link ExternalServiceClient} instance to perform the API calls for fetching data.<br>
 * - Requires utility method{@link Util#sleepSeconds(Long)} for putting the main thread to sleep.
 * <p>
 */
public class ProductInfoFetcher {

	private static final Logger log = LoggerFactory.getLogger(ProductInfoFetcher.class);

	public static void main(String[] args) {
		var serviceClient = new ExternalServiceClient();

		for (int i = 1; i <= 10; i++) {
			final int id = i;
			Mono.zip(serviceClient.getProductName(id), serviceClient.getProductPrice(id),
					 serviceClient.getProductReview(id))
					.map(response ->
								 new ProductInfo(response.getT1(), response.getT2(), response.getT3()))
					.subscribe(productInfo -> log.info("Product info for product with id {}: {}",
													   id, productInfo));
		}

		Util.sleepSeconds(2L);
	}
}
