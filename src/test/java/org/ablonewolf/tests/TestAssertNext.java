package org.ablonewolf.tests;

import org.ablonewolf.common.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

/**
 * The TestAssertNext class demonstrates how to use the {@link StepVerifier} to perform fine-grained assertions on
 * items emitted by a reactive stream, particularly using the
 * {@link StepVerifier.Step#assertNext(java.util.function.Consumer)}
 * method for validating individual elements.
 * <p>
 * It includes test cases that verify specific properties of a Flux of books, such as the ID of the first item and
 * the total number of collected items. These tests illustrate how to combine declarative assertions with custom logic.
 * <p>
 * Key demonstrations include:<br>
 * 1. Creating a reactive stream of custom objects using {@link Flux#range(int, int)} and mapping to a record-based
 * model.<br>
 * 2. Using {@code assertNext} to validate properties of the first emitted item in the sequence.<br>
 * 3. Consuming remaining items conditionally with
 * {@link StepVerifier.Step#thenConsumeWhile(java.util.function.Predicate)}.<br>
 * 4. Testing collection operators like {@code collectList()} and asserting the size of the resulting list.<br>
 * 5. Combining standard JUnit assertions with Reactor's testing utilities for comprehensive validation.
 * <p>
 * Dependencies:<br>
 * - Uses the immutable record {@code Book} defined locally to represent domain data.<br>
 * - Relies on the {@link Util} class to generate realistic test data via Faker.<br>
 * - Leverages JUnit Jupiter API for defining and executing assertions.<br>
 * - Uses Reactor's {@link StepVerifier} and {@link Flux} API to test reactive sequences in a synchronous and
 * controlled way.
 * <p>
 */
public class TestAssertNext {

	private record Book(Integer id, String author, String title) {

	}

	private Flux<Book> getBooks() {
		return Flux.range(1, 5)
				.map(id -> new Book(id, Util.getFaker().book().author(), Util.getFaker().book().title()));
	}

	@Test
	public void test_ifFirstBookIdMatches() {
		StepVerifier.create(getBooks())
				.assertNext(book -> Assertions.assertEquals(1, book.id()))
				.thenConsumeWhile(book -> Objects.nonNull(book.title))
				.expectComplete()
				.verify();
	}

	@Test
	public void test_ifBookListSizeMatches() {
		StepVerifier.create(getBooks().collectList())
				.assertNext(books -> Assertions.assertEquals(5, books.size()))
				.expectComplete()
				.verify();
	}
}
