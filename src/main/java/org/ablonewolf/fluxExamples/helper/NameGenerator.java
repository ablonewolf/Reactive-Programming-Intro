package org.ablonewolf.fluxExamples.helper;

import org.ablonewolf.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

public class NameGenerator {

    public static List<String> getNameList(Integer count) {
        return IntStream.range(1, count)
                .mapToObj(index -> getName())
                .toList();
    }

    public static Flux<String> getNameFlux(Integer count) {
        return Flux.range(1, count)
                .map(index -> getName());
    }

    private static String getName() {
        Util.sleepSeconds(1L);
        return Util.getFaker().name().firstName();
    }
}
