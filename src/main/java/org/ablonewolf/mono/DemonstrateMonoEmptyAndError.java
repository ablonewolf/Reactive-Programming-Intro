package org.ablonewolf.mono;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Mono;

/**
 * class to demonstrate Mono Empty and Mono Error
 */
public class DemonstrateMonoEmptyAndError {

    public static void main(String[] args) {
        getUserName(1)
                .subscribe(Util.subscriber("first user"));

        getUserName(2)
                .subscribe(Util.subscriber("second user"));

        getUserName(3)
                .subscribe(Util.subscriber("third user"));
    }

    /**
     * method to return the username by userId
     *
     * @param userId: a variable of integer type
     * @return mono of username
     */
    private static Mono<String> getUserName(Integer userId) {
        return switch (userId) {
            case 1 -> Mono.just("first user");
            case 2 -> Mono.empty(); // equivalent to null in traditional programming
            default -> Mono.error(new IllegalArgumentException("invalid user id: " + userId));
        };
    }
}
