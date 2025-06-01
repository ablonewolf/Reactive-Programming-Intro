package org.ablonewolf.model;

/**
 * Represents a record containing information about a product.
 * This record includes the product's name, price, and review.
 * <p>
 * It is designed to encapsulate data retrieved from external sources
 * or APIs, such as product catalogs or online marketplaces.
 * <p>
 *
 * @param productName: name of the product
 * @param review: review of the product
 * @param price: price of the product
 */
public record ProductInfo(
		String productName,
		String price,
		String review
) {
}
