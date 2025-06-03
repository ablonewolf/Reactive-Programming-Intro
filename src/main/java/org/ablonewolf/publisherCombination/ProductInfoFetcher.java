package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.ProductInfo;
import org.ablonewolf.services.impl.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * The ProductInfoFetcher class demonstrates the process of retrieving and logging product information
 * using reactive streams. This class utilizes Project Reactor's API for handling asynchronous operations.
 * <p>
 * The main responsibilities of this class include:<br>
 * - Establishing a connection with an {@link ExternalServiceClient} to interact with an external API.<br>
 * - Fetching product information for a range of product IDs by leveraging reactive streams.<br>
 * - Logging the retrieved product information in the application logs.
 * <p>
 * The class fetches product information in a non-blocking manner by using a flat-mapping approach
 * to retrieve information for multiple products concurrently. Logs are generated to provide details
 * about the fetched product information.
 */
public class ProductInfoFetcher {

	private static final Logger log = LoggerFactory.getLogger(ProductInfoFetcher.class);

	public static void main(String[] args) {
		var serviceClient = new ExternalServiceClient();

		fetchProductInfoUsingFlatmap(serviceClient);

		Util.sleepSeconds(2L);
	}

	private static void fetchProductInfoUsingFlatmap(ExternalServiceClient serviceClient) {
		Flux.range(1, 10)
				.flatMap(serviceClient::getProductInfo)
				.subscribe(ProductInfoFetcher::logProductInfo);
	}

	private static void logProductInfo(ProductInfo productInfo) {
		log.info("Product info for product {}", productInfo);
	}
}
