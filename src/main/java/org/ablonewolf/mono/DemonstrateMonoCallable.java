package org.ablonewolf.mono;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * demonstrate the use of mono callable to see that they accept methods with checked exceptions
 * which is not accepted in supplier interfaces
 */
public class DemonstrateMonoCallable {

    private static final Logger logger = LoggerFactory.getLogger(DemonstrateMonoCallable.class);

    public static void main(String[] args) {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // this will work as Callable interfaces do accept methods with checked exceptions
        Mono.fromCallable(() -> findSum(numbers))
                .subscribe(Util.subscriber("Finding sum of numbers"));

        /* the following commented code won't be executed as supplier don't accept methods with checked exceptions
            Mono.fromSupplier(() -> findSum(numbers))
                .subscribe(Util.subscriber("Finding sum of numbers"));*/
    }

    private static Integer findSum(List<Integer> numbers) throws Exception {
        if (Objects.isNull(numbers)) {
            throw new Exception("numbers is null");
        }
        logger.info("Finding the sum of this list of numbers {}.", numbers);

        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
