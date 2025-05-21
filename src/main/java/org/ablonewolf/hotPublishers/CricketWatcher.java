package org.ablonewolf.hotPublishers;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CricketWatcher is a reactive streams subscriber implementation designed to consume updates
 * about a cricket match's current score. This class logs relevant information during the lifecycle
 * of the subscription as well as when score updates are received.
 * <p>
 * Key Features:<br>
 * - Subscribes to a publisher providing cricket match scores.<br>
 * - Logs the beginning of the subscription, updates for the current score, and the completion of the match.<br>
 * - Handles errors during the subscription, logging the error details.
 * <p>
 * Class Responsibilities:<br>
 * - Handles subscription management through the onSubscribe method.<br>
 * - Processes and logs score updates using the onNext method.<br>
 * - Handles and logs errors encountered during the subscription via the onError method.<br>
 * - Executes final actions and logs when the subscription completes using the onComplete method.
 */
@RequiredArgsConstructor
public class CricketWatcher implements Subscriber<Integer> {

	private static final Logger log = LoggerFactory.getLogger(CricketWatcher.class);

	private final String name;

	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		log.info("{} started watching the match", name);
		this.subscription.request(Long.MAX_VALUE);
	}

	@Override
	public void onNext(Integer currentScore) {
		log.info("{} saw current score score {}", name, currentScore);
	}

	@Override
	public void onError(Throwable throwable) {
		log.error("{} faced an error while watching, details are: {}", name, throwable.getMessage());
	}

	@Override
	public void onComplete() {
		log.info("{} completed watching the match.", name);
	}
}
