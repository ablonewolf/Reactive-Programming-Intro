package org.ablonewolf.mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * demonstrate the subscribe method of Mono and its arguments
 */
public class DemonstrateMonoSubscribe {

    public static final Logger logger = LoggerFactory.getLogger(DemonstrateMonoSubscribe.class);

    public static void main(String[] args) {
        var mono = Mono.just("A demo string");
        Runnable completeMessage = () -> logger.info("completed");
        mono.subscribe(item -> logger.info("received item: {}", item),
                error -> logger.error("error occurred, details is: {}", error.getMessage()),
                completeMessage);

        Mono<Integer> integerMono = Mono.just(10)
                .map(item -> item / 0);

        integerMono.subscribe(item -> logger.info("received item {}", item),
                error -> logger.error("error occurred, details are: {}", error.getLocalizedMessage()),
                completeMessage);
    }
}
