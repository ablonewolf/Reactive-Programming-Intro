package org.ablonewolf.model;

import org.ablonewolf.common.Util;

/**
 * Represents a purchase booking, encapsulating details about an item,
 * its category, and its price.
 * <p>
 * This record is an immutable data structure for capturing information
 * about a product purchase including the item's name, the category to
 * which it belongs, and the purchase price.
 * <p>
 * Methods:<br>
 * - {@link #item()}: Returns the name of the item being purchased.<br>
 * - {@link #category()}: Returns the category of the item.<br>
 * - {@link #price()}: Returns the price of the item.<br>
 * - {@link #create()}: Static factory method to create an instance of
 * PurchaseBooking with randomly generated item, category, and price.
 */
public record ItemOrder(String item,
						String category,
						Integer price) {

	public static ItemOrder create() {
		var commerce = Util.getFaker().commerce();
		String item = commerce.productName();
		String category = commerce.department();
		Integer price = Util.getFaker().random().nextInt(100, 500);
		return new ItemOrder(item, category, price);
	}
}
