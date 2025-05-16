package org.ablonewolf.services.impl;

import org.ablonewolf.services.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * A {@link FileReaderService} implementation that uses Reactor's {@link Flux#generate} to read a file line by line.
 * This implementation opens a file, reads it line-by-line reactively, and ensures proper resource management,
 * including closing the file after reading completes or an error occurs.
 *
 * <p>Uses SLF4J logging to trace the file reading process, errors, and resource handling.</p>
 */
public class FileReaderServiceUsingFluxGenerate implements FileReaderService {

	private static final Logger log = LoggerFactory.getLogger(FileReaderServiceUsingFluxGenerate.class);

	/**
	 * Reads the content of a file at the given path as a reactive stream of lines.
	 * Uses Reactor's {@link Flux#generate} to manage the lifecycle of opening, reading, and closing the file.
	 *
	 * @param path The path to the file to be read.
	 * @return A {@link Flux} emitting lines from the file one by one, completing when the end of the file is reached.
	 */
	@Override
	public Flux<String> read(Path path) {
		return Flux.generate(() -> openFile(path), this::readFile, this::closeFile);
	}

	/**
	 * Opens the file at the specified path for reading.
	 * Creates and returns a new {@link BufferedReader} for the file.
	 *
	 * @param path The path to the file to be opened.
	 * @return A {@link BufferedReader} ready to read the file.
	 * @throws IOException If an I/O error occurs while opening the file.
	 */
	protected BufferedReader openFile(Path path) throws IOException {
		log.info("Opening file from path: {}", path);
		return Files.newBufferedReader(path);
	}

	/**
	 * Reads a single line from the file using the provided reader and emits it via the sink.
	 * If the end of the file is reached, signals completion. In case of an error, signals an error event.
	 *
	 * @param reader The bufferedReader used to read the file.
	 * @param sink   The sink used to emit the next item, complete, or signal an error.
	 * @return The same reader to be used in subsequent read operations.
	 */
	protected BufferedReader readFile(BufferedReader reader, SynchronousSink<String> sink) {
		String line;
		try {
			line = reader.readLine();
			log.info("Reading line: {}", line);

			if (Objects.isNull(line)) {
				sink.complete();
			} else {
				sink.next(line);
			}
		} catch (IOException e) {
			log.error("An error occurred, details: {}", e.getMessage());
			sink.error(e);
		}
		return reader;
	}

	/**
	 * Closes the provided {@link BufferedReader}, releasing any system resources associated with it.
	 * Logs a warning if an error occurs during closing.
	 *
	 * @param reader The reader to close.
	 */
	private void closeFile(BufferedReader reader) {
		try {
			reader.close();
		} catch (IOException e) {
			log.warn("Failed to close file", e);
		}
	}
}