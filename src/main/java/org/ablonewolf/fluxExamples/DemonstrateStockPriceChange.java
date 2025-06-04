package org.ablonewolf.fluxExamples;

import org.ablonewolf.basic.subscriber.StockPriceObserver;
import org.ablonewolf.common.Util;
import org.ablonewolf.services.impl.ExternalServiceClient;

/**
 * demonstrating how we can subscribe to a sample stock price change on a remote service
 * to make this work, we have to run the external service located inside the 'resources' directory.
 */
public class DemonstrateStockPriceChange {

	public static void main(String[] args) {

		var client = new ExternalServiceClient();

		var stockPriceObserver = new StockPriceObserver();

		client.getPriceChanges()
				.subscribe(stockPriceObserver);

		Util.sleepSeconds(20L);
	}
}
