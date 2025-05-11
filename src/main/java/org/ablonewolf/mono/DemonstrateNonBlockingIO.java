package org.ablonewolf.mono;

import org.ablonewolf.services.impl.ExternalServiceClient;
import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * demonstrated the non-blocking IO operation using the external service client
 */
public class DemonstrateNonBlockingIO {

	private static final Logger logger = LoggerFactory.getLogger(DemonstrateNonBlockingIO.class);

	public static void main(String[] args) {

		var client = new ExternalServiceClient();

		logger.info("Starting the Non-BlockingIO operation.");
		var startTime = LocalDateTime.now();

		for (int index = 1; index <= 100; index++) {
			client.getProductName(index)
					.subscribe(Util.subscriber("Product Consuming subscriber"));
		}
		var endTime = LocalDateTime.now();

		Util.sleepSeconds(2L);
		logger.info("Time taken to complete the IO operation: {} milliseconds",
				Duration.between(startTime, endTime).toMillis());
		logger.info("Completed the Non-BlockingIO operation.");
	}
}
