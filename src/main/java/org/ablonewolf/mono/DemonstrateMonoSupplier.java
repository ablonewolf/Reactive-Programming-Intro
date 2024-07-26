package org.ablonewolf.mono;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * demonstrate the use of mono supplier to delay the execution.
 * in other words, use the lazy approach that's already built in reactive streams
 */
public class DemonstrateMonoSupplier {

    private static final Logger logger = LoggerFactory.getLogger(DemonstrateMonoSupplier.class);

    public static void main(String[] args) {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Mono.fromSupplier(() -> findSum(numbers))
                .subscribe(Util.subscriber("Finding sum of numbers"));
    }

    private static Integer findSum(List<Integer> numbers) {
        logger.info("Finding the sum of this list of numbers {}.", numbers);

        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
