package org.ablonewolf.combinationOperators;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.ProductInfo;
import org.ablonewolf.services.impl.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * A class that demonstrates sequential processing of product information using Reactor's {@code concatMap}.
 * <p>
 * The class fetches product information for multiple product IDs sequentially using the
 * {@link ExternalServiceClient#getProductInfo(Integer)}method and logs the retrieved information
 * using the {@link #logProductInfo(ProductInfo)} method.
 * <p>
 * The {@code concatMap} operator ensures that for every product ID, the associated product information is
 * fetched and processed one at a time in the same order as the emitted sequence.
 * <p>
 * The main thread is paused to allow the reactive stream to complete processing.
 */
public class ProductInfoFetcherWithConcatMap {

	private static final Logger log = LoggerFactory.getLogger(ProductInfoFetcherWithConcatMap.class);

	public static void main(String[] args) {
		var serviceClient = new ExternalServiceClient();

		// The following pipeline will process the products sequentially.
		Flux.range(1, 10)
				.concatMap(serviceClient::getProductInfo)
				.subscribe(ProductInfoFetcherWithConcatMap::logProductInfo);

		Util.sleepSeconds(12L);
	}

	private static void logProductInfo(ProductInfo productInfo) {
		log.info("Product info for product {}", productInfo);
	}
}
