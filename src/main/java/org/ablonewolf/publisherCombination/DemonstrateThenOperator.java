package org.ablonewolf.publisherCombination;

import org.ablonewolf.common.Util;
import org.ablonewolf.model.ProductInfo;
import org.ablonewolf.services.impl.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the usage of the "then" operator in Project Reactor to chain reactive sequences.
 * <p>
 * This class uses an {@code ExternalServiceClient} to fetch product information for a series
 * of product IDs and stores the retrieved information in a list. Once all the product information
 * is collected, notifications are sent for the gathered product data. The "then" operator
 * enables transitioning from the data-fetching process to the notification-sending process.
 * </p>
 * <p>
 * The reactive sequence in this class consists of the following operations:
 * <ul>
 *     <li>A range of product IDs is generated using {@code Flux.range}.</li>
 *     <li>Product information is fetched for each ID using {@link ExternalServiceClient#getProductInfo(Integer)}.</li>
 *     <li>Each {@code ProductInfo} object fetched is added to a list for later processing.</li>
 *     <li>After all {@code ProductInfo} objects are collected, notifications are sent
 *         using the {@link #sendNotifications(List)} method.</li>
 *     <li>A subscriber subscribes to the sequence to initiate the series of operations.</li>
 * </ul>
 * </p>
 * <p>
 * The {@link Util#sleepSeconds(Long)} method is used to keep the program running long enough
 * for the reactive sequence to complete.
 * </p>
 */
public class DemonstrateThenOperator {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateThenOperator.class);

	public static void main(String[] args) {

		var serviceClient = new ExternalServiceClient();
		List<ProductInfo> productInfos = new ArrayList<>();


		Flux.range(1, 3)
				.concatMap(serviceClient::getProductInfo)
				.doOnNext(productInfos::add)
				.then(sendNotifications(productInfos)) // we now have the data and can send notifications for all of
				// them
				.subscribe();

		Util.sleepSeconds(5L);
	}

	private static Mono<Void> sendNotifications(List<ProductInfo> productInfos) {
		return Mono.fromRunnable(() -> log.info("Sent notifications for {} productInfos", productInfos.size()));
	}
}
