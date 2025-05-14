package org.ablonewolf.flux;

import org.ablonewolf.basic.subscriber.StringSubscriber;
import org.ablonewolf.services.FileReaderService;
import org.ablonewolf.services.impl.FileReaderServiceImpl;

import java.nio.file.Path;

public class DemonstrateFileReadingWithFluxCreate {

	private static final Path PATH = Path.of("src/main/resources/");

	public static void main(String[] args) {
		var stringSubscriber = new StringSubscriber();
		FileReaderService fileReaderService = new FileReaderServiceImpl();

		fileReaderService.read(PATH.resolve("sample_file.txt"))
				.subscribe(stringSubscriber);

		stringSubscriber.getSubscription().request(10);
		stringSubscriber.getSubscription().cancel();
	}
}
