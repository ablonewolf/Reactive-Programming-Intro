package org.ablonewolf.fluxExamples.helper;

import org.ablonewolf.common.Util;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;
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

	public static Flux<String> getCountryNamesOnDemand(Logger log) {
		return Flux.create(fluxSink -> fluxSink.onRequest(request -> {
			for (int i = 0; i < request && !fluxSink.isCancelled(); i++) {
				var name = Util.getFaker().country().name();
				log.info("Generating Country Name: {}", name);
				fluxSink.next(name);
			}
			if (fluxSink.isCancelled()) {
				fluxSink.complete();
			}
		}));
	}

	public static Flux<String> getFiniteCountryNames(Integer count) {
		return Flux.generate(synchronousSink -> {
					var countryName = Util.getFaker().country().name();
					synchronousSink.next(countryName);
				}).cast(String.class)
				.delayElements(Duration.ofMillis(100L))
				.take(count);
	}

	private static String getName() {
		Util.sleepSeconds(1L);
		return Util.getFaker().name().firstName();
	}
}
