package org.ablonewolf.mono;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * demonstrate the use of mono from runnable and its utility
 */
public class DemonstrateMonoFromRunnable {

    private static final Logger logger = LoggerFactory.getLogger(DemonstrateMonoFromRunnable.class);

    public static void main(String[] args) {

        getProductName(1)
                .subscribe(Util.subscriber("Product 1"));

        getProductName(2)
                .subscribe(Util.subscriber("Product 2"));
    }

    /**
     * method to return a mono of product name from its product id
     * @param productId the ID of the product
     * @return the mono of the product name otherwise notifies business about missing product
     */
    private static Mono<String> getProductName(Integer productId) {
        if (Objects.equals(productId, 1)) {
            return Mono.fromSupplier(() -> Util.getFaker().commerce().productName());
        }
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    /**
     * method to notify business about the unavailable product
     * @param productId the id of desired product
     */
    private static void notifyBusiness(Integer productId) {
        logger.info("Notifying business about the unavailable product with id: {}", productId);
    }
}
