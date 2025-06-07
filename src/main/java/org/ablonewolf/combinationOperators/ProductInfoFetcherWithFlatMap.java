package org.ablonewolf.combinationOperators;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.ProductInfo;
import org.ablonewolf.services.impl.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * A utility class to fetch and process product information using reactive programming with Project Reactor.
 * <p>
 * This class demonstrates the use of the flatMap operator to asynchronously fetch product information
 * for a range of product IDs. The product information is retrieved using an external service client
 * and logged for each product. The process involves handling multiple asynchronous API calls concurrently,
 * which is well-suited for scenarios with independent data retrieval tasks.
 * <p>
 * The class operates as follows:<br>
 * - A range of product IDs are generated using {@link Flux#range(int, int)}.<br>
 * - Each product ID is processed with the flatMap operator to transform the ID into a reactive stream
 * of {@link ProductInfo}, provided by {@link ExternalServiceClient#getProductInfo(Integer)}.<br>
 * - The resulting product information is logged using a dedicated logging method.
 * <p>
 * The main thread is paused to allow the reactive stream to complete processing.
 */
public class ProductInfoFetcherWithFlatMap {

	private static final Logger log = LoggerFactory.getLogger(ProductInfoFetcherWithFlatMap.class);

	public static void main(String[] args) {
		var serviceClient = new ExternalServiceClient();

		fetchProductInfoUsingFlatmap(serviceClient);

		Util.sleepSeconds(2L);
	}

	private static void fetchProductInfoUsingFlatmap(ExternalServiceClient serviceClient) {
		Flux.range(1, 10)
				.flatMap(serviceClient::getProductInfo)
				.subscribe(ProductInfoFetcherWithFlatMap::logProductInfo);
	}

	private static void logProductInfo(ProductInfo productInfo) {
		log.info("Product info for product {}", productInfo);
	}
}
