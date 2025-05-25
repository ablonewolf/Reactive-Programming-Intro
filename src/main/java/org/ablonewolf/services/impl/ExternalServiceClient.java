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
		return this.httpClient.get()
				.uri(String.format("/demo01/product/%d", productId))
				.responseContent()
				.asString()
				.next()
				.publishOn(Schedulers.boundedElastic());
	}

	public Flux<String> getStreamOfNames() {
		return this.httpClient.get()
				.uri("/demo02/name/stream")
				.responseContent()
				.asString()
				.publishOn(Schedulers.boundedElastic());
	}

	public Flux<Integer> getPriceChanges() {
		return this.httpClient.get()
				.uri("/demo02/stock/stream")
				.responseContent()
				.asString()
				.map(Integer::parseInt)
				.publishOn(Schedulers.boundedElastic());
	}
}
