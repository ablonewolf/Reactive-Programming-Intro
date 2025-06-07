package org.ablonewolf.common;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
public class DefaultSubscriber<T> implements Subscriber<T> {

	public static final Logger logger = LoggerFactory.getLogger(DefaultSubscriber.class);
	private final String name;

	private boolean receivedItem = false;
	private boolean completed = false;

	@Override
	public void onSubscribe(Subscription subscription) {
		subscription.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(T item) {
		receivedItem = true;

		if (item instanceof List && ((List<?>) item).isEmpty()) {
			logger.info("{} received an empty list.", this.name);
		} else {
			logger.info("{} received item: {}.", this.name, item);
		}
	}

	@Override
	public void onError(Throwable throwable) {
		receivedItem = true;
		logger.error("{} faced an error. Error message: {}", this.name, throwable.toString());
	}

	@Override
	public void onComplete() {
		if (!receivedItem && !completed) {
			logger.info("{} did not receive any item.", this.name);
		} else {
			logger.info("{} completed receiving requests.", this.name);
		}
		completed = true;
	}
}