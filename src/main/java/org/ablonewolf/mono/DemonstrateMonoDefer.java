package org.ablonewolf.mono;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * demonstrate the use of Mono Defer operation to delay the creation of publishers
 */
public class DemonstrateMonoDefer {

    private static final Logger logger = LoggerFactory.getLogger(DemonstrateMonoDefer.class);

    public static void main(String[] args) {
        // this will delay the creation of publisher
        var publisherMono = Mono.defer(DemonstrateMonoDefer::createPublisher);

        // the publisher will be created now if the code is uncommented since something is subscribing to it.
        // publisherMono.subscribe(Util.subscriber("Integer Subscriber"));
    }

    /**
     * creates a publisher of Mono of Integer type
     * @return a Mono of Integer
     */
    private static Mono<Integer> createPublisher() {
        logger.info("Creating a publisher of Integer.");
        var integerList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Util.sleepSeconds(2L);
        return Mono.fromSupplier(() -> findSum(integerList));
    }

    /**
     * method to find the sum of a List of Integers
     * @param numbers list of Integers
     * @return the sum of those Integers
     */
    private static Integer findSum(List<Integer> numbers) {
        logger.info("Finding the sum of this list of numbers {}.", numbers);

        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
