package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import org.ablonewolf.services.impl.ExternalServiceClient;

/**
 * demonstrated how to consume a flux of items from an external service
 */
public class DemonstrateNonBlockingIO {

	public static void main(String[] args) {

		var client = new ExternalServiceClient();

		client.getStreamOfNames()
				.subscribe(Util.subscriber("Random Name Subscriber"));

		Util.sleepSeconds(8L);
	}
}
