package org.ablonewolf.batching;

import org.ablonewolf.common.CountryData;
import org.ablonewolf.common.Util;
import org.ablonewolf.common.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.util.function.Function;

/**
 * Demonstrates writing country data into files grouped by continent.
 * <p>
 * The class leverages asynchronous data streaming using Project Reactor's Flux
 * to generate a finite number of country names. These names are then grouped by continent
 * and written to separate files, one for each continent.
 * <p>
 * File names are derived based on a predefined format and the continent name
 * (e.g., `src/main/resources/country/{continent}.txt`).
 * <p>
 * Execution Details:<br>
 * - Uses the method {@link  NameGenerator#getFiniteCountryNames(Integer count)}}
 * to generate a stream of country names.<br>
 * - Groups countries by their continent using {@link Flux#groupBy(Function)}.<br>
 * - For each group, creates and writes to a file asynchronously.<br>
 * - Log lifecycle events such as the creation of a new group, file writing success, etc.<br>
 * - Sleeps for a configured duration to allow the asynchronous operations to complete.
 * <p>
 * Note:<br>
 * - Requires the existence of the directory `src/main/resources/country/` for the file writing operation.<br>
 * - Logs output to provide insight into streaming and file writing progress.<br>
 * - Handles file operations using the custom {@link FileWriter#createAndWriteToFile(Flux content, Path path)} method.
 */
public class DemonstrateFileWritingUsingGroupBy {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateFileWritingUsingGroupBy.class);

	public static void main(String[] args) {

		String fileNameFormat = "src/main/resources/country/%s.txt";

		NameGenerator.getFiniteCountryNames(180)
				.groupBy(CountryData::getContinentForCountry)
				.doOnNext(group -> log.info("New group created for continent: {}",
											group.key()))
				.flatMap(group -> {
					Path path = Path.of(fileNameFormat.formatted(group.key()));
					return FileWriter.createAndWriteToFile(group, path)
							.doOnSuccess(success -> log.info("File written for continent: {}", group.key()));
				})
				.subscribe();

		Util.sleepSeconds(22L);
	}
}
