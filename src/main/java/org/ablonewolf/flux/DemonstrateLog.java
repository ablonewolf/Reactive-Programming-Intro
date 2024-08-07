package org.ablonewolf.flux;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

/**
 * demonstrated the use of Log; it is a processor; it can act as a subscriber to a publisher and publisher to a
 * subscriber
 */
public class DemonstrateLog {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .log()
                .subscribe(Util.subscriber("Integer Subscriber"));
    }
}
