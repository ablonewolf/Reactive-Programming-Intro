package org.ablonewolf.services.impl;

import org.ablonewolf.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final Path PATH = Path.of("src/main/resources/");

    @Override
    public Mono<String> read(String fileName) {
        return Mono.fromSupplier(() -> {
            try {
                log.info("Reading from file {}", fileName);
                return Files.readString(PATH.resolve(fileName));
            } catch (IOException e) {
                log.error("An error occurred while reading file : {}, details: {}", fileName, e.getMessage());
                return null;
            }
        });
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> {
            try {
                log.info("Writing from file {}", fileName);
                Files.writeString(PATH.resolve(fileName), content);
            } catch (IOException e) {
                log.error("An error occurred while writing file : {}, details: {}", fileName, e.getMessage());
            }
        });
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> {
            try {
                log.info("Deleting file {}", fileName);
                Files.delete(PATH.resolve(fileName));
            } catch (IOException e) {
                log.error("An error occurred while deleting file : {}, details: {}", fileName, e.getMessage());
            }
        });
    }
}
