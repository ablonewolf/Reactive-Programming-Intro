package org.ablonewolf.services.impl;

import org.ablonewolf.services.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileReaderServiceImpl implements FileReaderService {

	private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);

	@Override
	public Flux<String> read(Path path) {
		return Flux.create(fluxSink -> {
			BufferedReader reader;
			try {
				reader = openFile(path);
			} catch (IOException e) {
				fluxSink.error(e);
				return;
			}

			fluxSink.onRequest(n -> {
				try {
					for (long i = 0; i < n && !fluxSink.isCancelled(); i++) {
						String line = readLine(reader);

						if (Objects.isNull(line) || line.isBlank()) {
							reader.close();
							log.info("File {} closed as no lines left for reading", path);
							fluxSink.complete();
							return;
						}
						fluxSink.next(line);
					}
				} catch (IOException e) {
					try {
						reader.close();
					} catch (IOException ex) {
						fluxSink.error(ex);
						log.warn("Failed to close file {}", path, ex);
					}
					fluxSink.error(e);
				}
			});

			fluxSink.onCancel(() -> {
				try {
					log.info("File closed as cancelled operation triggered by subscriber");
					fluxSink.complete();
					reader.close();
				} catch (IOException e) {
					fluxSink.error(e);
				}
			});

		});
	}

	protected BufferedReader openFile(Path path) throws IOException {
		log.info("Opening file from path: {}", path);
		return Files.newBufferedReader(path);
	}

	protected String readLine(BufferedReader reader) throws IOException {
		return reader.readLine();
	}
}