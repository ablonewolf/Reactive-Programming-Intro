package org.ablonewolf.services.impl;

import org.ablonewolf.common.AbstractHttpClient;
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

	public Mono<String> getProductPrice(Integer productId) {
		String uri = String.format("/demo05/price/%d", productId);
		return this.getSingleAPIResponse(uri);
	}

	public Mono<String> getProductReview(Integer productId) {
		String uri = String.format("/demo05/review/%d", productId);
		return this.getSingleAPIResponse(uri);
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
