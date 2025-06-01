package org.ablonewolf.model;

public record Order(Integer orderId,
					Integer userId,
					String productName,
					Integer price) {
}
