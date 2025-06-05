package org.ablonewolf.batching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class for writing content to a file located at a specified file path in a reactive programming
 * context. The class also handles the creation and closure of the file during the write operation.
 * <p>
 * Functionality:<br>
 * - Creates the file at the specified path before writing any content.<br>
 * - Writes a stream of text content to the file while ensuring that each entry is appended on a new line.<br>
 * - Flushes content to the file after each write operation ensuring consistency.<br>
 * - Safely closes the file after all operations are complete.
 * <p>
 * Thread Safety:
 * The FileWriter is not thread-safe and should not be shared across multiple threads without external synchronization.
 * <p>
 * Usage:
 * The provided {@link FileWriter#createAndWriteToFile} method can be utilized to handle the entire writing operation
 * reactively. This method ensures the creation, writing, and proper cleanup (i.e., closing the file) of the resources.
 */
public class FileWriter {

	private static final Logger log = LoggerFactory.getLogger(FileWriter.class);
	private final Path path;
	private BufferedWriter writer;
	private final Set<String> items = new HashSet<>();

	private FileWriter(Path path) {
		this.path = path;
	}

	public static Mono<Void> createAndWriteToFile(Flux<String> content, Path path) {
		var writer = new FileWriter(path);
		return content
				.filter(item -> !writer.items.contains(item))
				.doOnNext(item -> {
					writer.items.add(item);
					writer.write(item);
				})
				.doFirst(writer::createFile)
				.doFinally(signalType -> writer.closeFile())
				.then();
	}

	private void createFile() {
		try {
			this.writer = Files.newBufferedWriter(path);
		} catch (IOException e) {
			log.error("Failed to create file at path {} due to the following error: {}", path, e.getMessage());
		}
	}

	private void closeFile() {
		try {
			this.writer.close();
		} catch (IOException e) {
			log.error("Failed to close file due to the error: {}", e.getMessage());
		}
	}

	private void write(String content) {
		try {
			this.writer.write(content);
			this.writer.newLine();
			this.writer.flush();
		} catch (IOException e) {
			log.error("An error occurred while writing content to the file, details: {}", e.getMessage());
		}
	}
}
