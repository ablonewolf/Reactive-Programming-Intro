package org.ablonewolf.services.impl;

import org.ablonewolf.common.AbstractHttpClient;
import org.ablonewolf.model.ProductInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * an external service client to communicate with the external service and consume its APIs
 */
public class ExternalServiceClient extends AbstractHttpClient {

	public Mono<String> getProductName(Integer productId) {
		String uri = String.format("/demo01/product/%d", productId);
		return this.getSingleAPIResponse(uri);
	}

	public Flux<String> getStreamOfNames() {
		String uri = "/demo02/name/stream";

		return this.getStreamOfAPIResponses(uri)
				.publishOn(Schedulers.boundedElastic());
	}

	public Flux<Integer> getPriceChanges() {
		String uri = "/demo02/price/stream";

		return this.getStreamOfAPIResponses(uri)
				.map(Integer::parseInt)
				.publishOn(Schedulers.boundedElastic());
	}

	private Mono<String> getProductPrice(Integer productId) {
		String uri = String.format("/demo05/price/%d", productId);
		return this.getSingleAPIResponse(uri);
	}

	private Mono<String> getProductReview(Integer productId) {
		String uri = String.format("/demo05/review/%d", productId);
		return this.getSingleAPIResponse(uri);
	}

	/**
	 * Retrieves product information for the given product ID, including the product's name, price, and review.
	 * Combines the results from different sources into a single ProductInfo object.
	 *
	 * @param productId the unique identifier of the product whose information is to be fetched
	 * @return a {@code Mono<ProductInfo>} containing the combined product information (name, price, review)
	 */
	public Mono<ProductInfo> getProductInfo(Integer productId) {
		return Mono.zip(this.getProductName(productId), this.getProductPrice(productId),
						this.getProductReview(productId))
				.map(response ->
							 new ProductInfo(response.getT1(), response.getT2(), response.getT3()));
	}

	private Mono<String> getSingleAPIResponse(String path) {
		return this.httpClient.get()
				.uri(path)
				.responseContent()
				.asString()
				.next()
				.publishOn(Schedulers.boundedElastic());
	}

	private Flux<String> getStreamOfAPIResponses(String path) {
		return this.httpClient.get()
				.uri(path)
				.responseContent()
				.asString();
	}
}
