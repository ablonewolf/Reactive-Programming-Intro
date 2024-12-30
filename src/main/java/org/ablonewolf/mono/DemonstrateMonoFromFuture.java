package org.ablonewolf.mono;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class DemonstrateMonoFromFuture {

    private static final Logger log = LoggerFactory.getLogger(DemonstrateMonoFromFuture.class);

    public static void main(String[] args) {
        Mono.fromFuture(DemonstrateMonoFromFuture::getName)
            .subscribe(Util.subscriber("CompletableFuture Subscriber"));

        Util.sleepSeconds(1L);
    }

    private static CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Generating a name");
            return Util.getFaker().name().fullName();
        });
    }
}
