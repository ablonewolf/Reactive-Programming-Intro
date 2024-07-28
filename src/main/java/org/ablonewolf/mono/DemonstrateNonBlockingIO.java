package org.ablonewolf.mono;

import org.ablonewolf.clients.ExternalServiceClient;
import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * demonstrated the non-blocking IO operation using the external service client
 */
public class DemonstrateNonBlockingIO {

    private static final Logger logger = LoggerFactory.getLogger(DemonstrateNonBlockingIO.class);

    public static void main(String[] args) {

        var client = new ExternalServiceClient();

        logger.info("Starting the Non-BlockingIO operation.");

        for (Integer index = 1; index <= 20; index++) {
            client.getProductName(index)
                    .subscribe(Util.subscriber("Product Consuming subscriber"));
        }

        Util.sleepSeconds(2L);

        logger.info("Completed the Non-BlockingIO operation.");
    }
}
