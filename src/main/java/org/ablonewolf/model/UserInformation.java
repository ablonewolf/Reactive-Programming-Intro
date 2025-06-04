package org.ablonewolf.model;

import java.util.List;

public record UserInformation(
		Integer userId,
		String username,
		Integer balance,
		List<Order> orders
) {
}
