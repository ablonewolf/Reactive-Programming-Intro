package org.ablonewolf.backpressureHandling;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public final class NumberGenerator {

	public static Flux<Integer> generateNumberProducer(Logger log) {
		return Flux.generate(
						() -> 1,
						(state, sink) -> {
							log.info("Generating value {}", state);
							sink.next(state);
							return ++state;
						})
				.cast(Integer.class)
				.subscribeOn(Schedulers.parallel());
	}

	private NumberGenerator() {
	}

}
