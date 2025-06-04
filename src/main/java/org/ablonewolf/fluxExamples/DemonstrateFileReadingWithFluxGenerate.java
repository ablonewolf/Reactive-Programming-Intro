package org.ablonewolf.fluxExamples;

import org.ablonewolf.common.Util;
import org.ablonewolf.services.impl.FileReaderServiceUsingFluxGenerate;

import java.nio.file.Path;

/**
 * Demonstrates how to use Reactor's Flux.generate method to read a file reactively.
 * <p>
 * The class utilizes {@link FileReaderServiceUsingFluxGenerate} to read the content of a file line-by-line
 * as a reactive stream and process it with a subscriber. It performs the following steps:
 * - Resolves the path to the file.
 * - Reads the file content as a reactive stream.
 * - Limits the emitted lines to the first 50 using the `take` operator.
 * - Subscribes to the stream using a custom subscriber for processing.
 * <p>
 * This serves as a demonstration of integrating non-blocking file reading and reactive streams
 * via Reactor's Flux API.
 */
public class DemonstrateFileReadingWithFluxGenerate {

	private static final Path PATH = Path.of("src/main/resources/");

	public static void main(String[] args) {
		var fileReaderService = new FileReaderServiceUsingFluxGenerate();

		fileReaderService.read(PATH.resolve("sample_file.txt"))
				.take(50)
				.subscribe(Util.subscriber("File Reader Subscriber"));
	}
}
