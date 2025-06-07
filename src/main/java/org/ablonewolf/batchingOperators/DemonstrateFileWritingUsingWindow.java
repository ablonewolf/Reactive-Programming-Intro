package org.ablonewolf.batchingOperators;

import org.ablonewolf.common.Util;
import org.ablonewolf.common.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates file writing using a reactive approach in a fixed time window.
 * <p>
 * This class uses reactive programming to generate country names dynamically,
 * groups them into time-based windows, and writes each group to a separate file.
 * Each operation is executed reactively, ensuring asynchronous, non-blocking behavior.
 * <p>
 * Behavior:<br>
 * - Dynamically generates a finite number of country names.<br>
 * - Groups the generated country names into fixed-duration time windows.<br>
 * - Writes the country names from each window to individual files sequentially.<br>
 * - Logs events such as window creation and file writing operations.
 * <p>
 * Workflow:<br>
 * - Generates 150 country names using a time-delayed reactive flux stream.<br>
 * - Applies a time-window of 4 seconds to group country names.<br>
 * - For each time window, writes the grouped names into a unique file whose
 * name is incrementally generated based on a predefined naming format.
 * <p>
 * Dependencies:<br>
 * - Requires {@link NameGenerator#getFiniteCountryNames(Integer count)} to produce finite country name streams.<br>
 * - Utilizes {@link FileWriter#createAndWriteToFile(Flux content, Path path)}
 * to handle file creation and writing reactively.<br>
 * - Delays execution of the thread using {@link Util#sleepSeconds(Long seconds)} for demonstration.<br>
 * - Uses {@link Logger} for logging various events and operations.
 */
public class DemonstrateFileWritingUsingWindow {

	private static final Logger log = LoggerFactory.getLogger(DemonstrateFileWritingUsingWindow.class);

	public static void main(String[] args) {

		AtomicInteger counter = new AtomicInteger();
		String fileNameFormat = "src/main/resources/country/country_names_%d.txt";

		NameGenerator.getFiniteCountryNames(150)
				.window(Duration.ofSeconds(4))
				.doOnNext(window -> log.info("New window created"))
				.flatMap(countryNames -> {
					Path path = Path.of(fileNameFormat.formatted(counter.getAndIncrement()));
					return FileWriter.createAndWriteToFile(countryNames, path);
				})
				.subscribe();

		Util.sleepSeconds(35L);
	}
}
