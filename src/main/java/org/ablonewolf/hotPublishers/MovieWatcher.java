package org.ablonewolf.hotPublishers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class MovieWatcher implements Subscriber<String> {

	private static final Logger log = LoggerFactory.getLogger(MovieWatcher.class);

	private final String name;

	@Getter
	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		log.info("Subscribed by {}", name);
	}

	@Override
	public void onNext(String scene) {
		log.info("{} received {}", name, scene);
	}

	@Override
	public void onError(Throwable throwable) {
		log.error("{} faced an error while watching, details are: {}", name, throwable.getMessage());
	}

	@Override
	public void onComplete() {
		log.info("{} completed watching the movie.", name);
	}
}
