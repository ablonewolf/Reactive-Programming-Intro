package org.ablonewolf.services;

import reactor.core.publisher.Flux;

import java.nio.file.Path;

public interface FileReaderService {

	Flux<String> read(Path path);
}
