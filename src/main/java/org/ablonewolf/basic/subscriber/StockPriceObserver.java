package org.ablonewolf.basic.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * a custom subscriber to observe the stock price changes in a remote publisher
 */
public class StockPriceObserver implements Subscriber<Integer> {

	private static final Logger log = LoggerFactory.getLogger(StockPriceObserver.class);
	private Integer quantity = 0;
	private Integer balance = 1000;
	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		subscription.request(Long.MAX_VALUE);
		this.subscription = subscription;
	}

	@Override
	public void onNext(Integer price) {
		if (price < 90 && balance >= price) {
			++quantity;
			balance -= price;
			log.info("Bought a stock of price {}, current quantity is {}, current balance is {}",
					price, quantity, balance);
		} else if (price > 110 && quantity > 0) {
			log.info("Selling {} quantities at the price {}", quantity, price);
			balance = balance + (quantity * price);
			quantity = 0;
			subscription.cancel();
			log.info("Attained profit is {}", balance - 1000);
		} else {
			if (balance == 0) {
				if (price < 90) {
					log.info("Insufficient balance to buy stocks");
				} else if (!(price > 110)) {
					log.info("Price is not appropriate for selling");
				}
			}
		}
	}

	@Override
	public void onError(Throwable throwable) {
		log.error("An error occurred: {}", throwable.getMessage());
	}

	@Override
	public void onComplete() {
		log.info("Stock price observing is completed.");
	}
}
