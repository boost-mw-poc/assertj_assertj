/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2025 the original author or authors.
 */
package org.assertj.tests.core.api.recursive.comparison.fields;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.presentation.StandardRepresentation.registerFormatterForType;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.Lists.list;
import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.assertj.tests.core.api.recursive.data.Author.authorsTreeSet;
import static org.assertj.tests.core.util.AssertionsUtil.expectAssertionError;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UnknownFormatConversionException;
import java.util.stream.Stream;

import org.assertj.core.api.recursive.comparison.ComparisonDifference;
import org.assertj.core.groups.Tuple;
import org.assertj.tests.core.api.recursive.data.Author;
import org.assertj.tests.core.api.recursive.data.WithObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RecursiveComparisonAssert_isEqualTo_with_iterables_Test extends WithComparingFieldsIntrospectionStrategyBaseTest {

  @ParameterizedTest(name = "author 1 {0} / author 2 {1}")
  @MethodSource
  void should_pass_when_comparing_same_collection_fields(Collection<Author> authors1, Collection<Author> authors2) {
    // GIVEN
    WithIterable<Author> actual = new WithIterable<>(authors1);
    WithIterable<Author> expected = new WithIterable<>(authors2);
    // THEN
    then(actual).usingRecursiveComparison(recursiveComparisonConfiguration)
                .isEqualTo(expected);
  }

  static Stream<Arguments> should_pass_when_comparing_same_collection_fields() {
    Author pratchett = new Author("Terry Pratchett");
    Author georgeMartin = new Author("George Martin");
    Collection<Author> empty = emptyList();
    return Stream.of(Arguments.of(list(pratchett), list(pratchett)),
                     Arguments.of(list(pratchett, georgeMartin), list(pratchett, georgeMartin)),
                     Arguments.of(list(pratchett, null), list(pratchett, null)),
                     Arguments.of(empty, empty),
                     Arguments.of(authorsTreeSet(pratchett), authorsTreeSet(pratchett)),
                     Arguments.of(authorsTreeSet(pratchett, georgeMartin), authorsTreeSet(pratchett, georgeMartin)),
                     Arguments.of(authorsTreeSet(pratchett, null), authorsTreeSet(pratchett, null)),
                     Arguments.of(authorsTreeSet(), authorsTreeSet()),
                     // ordered vs ordered is ok as long as the ordered elements match
                     Arguments.of(list(pratchett), authorsTreeSet(pratchett)),
                     Arguments.of(list(pratchett), newLinkedHashSet(pratchett)),
                     Arguments.of(list(georgeMartin, pratchett), authorsTreeSet(pratchett, georgeMartin)),
                     Arguments.of(list(pratchett, georgeMartin), newLinkedHashSet(pratchett, georgeMartin)),
                     Arguments.of(newLinkedHashSet(pratchett), list(pratchett)),
                     Arguments.of(newLinkedHashSet(pratchett), authorsTreeSet(pratchett)),
                     Arguments.of(newLinkedHashSet(pratchett, georgeMartin), list(pratchett, georgeMartin)),
                     Arguments.of(newLinkedHashSet(georgeMartin, pratchett), authorsTreeSet(pratchett, georgeMartin)),
                     Arguments.of(authorsTreeSet(pratchett), list(pratchett)),
                     Arguments.of(authorsTreeSet(pratchett), newLinkedHashSet(pratchett)),
                     Arguments.of(authorsTreeSet(pratchett, georgeMartin), list(georgeMartin, pratchett)),
                     Arguments.of(authorsTreeSet(pratchett, georgeMartin), newLinkedHashSet(georgeMartin, pratchett)),
                     Arguments.of(authorsTreeSet(pratchett, null), authorsTreeSet(pratchett, null)),
                     // actual ordered vs expected unordered can be compared but not the other way around
                     Arguments.of(list(pratchett), newHashSet(pratchett)),
                     Arguments.of(newLinkedHashSet(pratchett), newHashSet(pratchett)),
                     Arguments.of(authorsTreeSet(pratchett), newHashSet(pratchett)),
                     Arguments.of(list(pratchett, georgeMartin), newHashSet(pratchett, georgeMartin)),
                     Arguments.of(newLinkedHashSet(georgeMartin, pratchett), newHashSet(pratchett, georgeMartin)),
                     Arguments.of(authorsTreeSet(georgeMartin, pratchett), newHashSet(pratchett, georgeMartin)));
  }

  @ParameterizedTest(name = "authors 1 {0} / authors 2 {1} / difference {2}")
  @MethodSource
  void should_fail_when_comparing_different_collection_fields(Collection<Author> authors1, Collection<Author> authors2,
                                                              ComparisonDifference difference) {
    // GIVEN
    WithIterable<Author> actual = new WithIterable<>(authors1);
    WithIterable<Author> expected = new WithIterable<>(authors2);
    // WHEN/THEN
    compareRecursivelyFailsWithDifferences(actual, expected, difference);
  }

  static Stream<Arguments> should_fail_when_comparing_different_collection_fields() {
    Author pratchett = new Author("Terry Pratchett");
    Author georgeMartin = new Author("George Martin");
    Author none = null;
    Set<Author> pratchettHashSet = newHashSet(pratchett);
    List<Author> pratchettList = list(pratchett);
    return Stream.of(Arguments.of(pratchettList, list(georgeMartin),
                                  javaTypeDiff("group[0].name", "Terry Pratchett", "George Martin"),
                                  Arguments.of(list(pratchett, georgeMartin), pratchettList, "group",
                                               list(pratchett, georgeMartin), pratchettList,
                                               "actual and expected values are collections of different size, actual size=2 when expected size=1"),
                                  Arguments.of(pratchettList, list(none), diff("group[0]", pratchett, null, null)),
                                  Arguments.of(list(none), pratchettList, diff("group[0]", null, pratchett, null)),
                                  // actual non ordered vs expected ordered collections
                                  Arguments.of(pratchettHashSet, pratchettList, "group", pratchettHashSet, pratchettList,
                                               "expected field is an ordered collection but actual field is not (java.util.HashSet), ordered collections are: [java.util.List, java.util.SortedSet, java.util.LinkedHashSet]"),
                                  Arguments.of(authorsTreeSet(pratchett), authorsTreeSet(georgeMartin),
                                               javaTypeDiff("group[0].name", "Terry Pratchett", "George Martin")),
                                  Arguments.of(newHashSet(pratchett, georgeMartin), pratchettHashSet, "group",
                                               newHashSet(pratchett, georgeMartin), pratchettHashSet,
                                               "actual and expected values are collections of different size, actual size=2 when expected size=1"),
                                  // hashSet diff is at the collection level, not the element as in ordered collection where we
                                  // can show the
                                  // pair of different elements, this is why actual and expected are set and not element values.
                                  Arguments.of(pratchettHashSet, newHashSet(none), "group",
                                               pratchettHashSet, newHashSet(none),
                                               "The following expected elements were not matched in the actual HashSet:%n  [null]".formatted()),
                                  Arguments.of(newHashSet(none), pratchettHashSet, "group",
                                               newHashSet(none), pratchettHashSet,
                                               format("The following expected elements were not matched in the actual HashSet:%n"
                                                      + "  [Author [name=Terry Pratchett]]")),
                                  Arguments.of(pratchettHashSet, newHashSet(georgeMartin), "group",
                                               pratchettHashSet, newHashSet(georgeMartin),
                                               format("The following expected elements were not matched in the actual HashSet:%n"
                                                      + "  [Author [name=George Martin]]")),
                                  Arguments.of(authorsTreeSet(pratchett, georgeMartin), authorsTreeSet(pratchett), "group",
                                               authorsTreeSet(pratchett, georgeMartin), authorsTreeSet(pratchett),
                                               "actual and expected values are collections of different size, actual size=2 when expected size=1"),
                                  Arguments.of(authorsTreeSet(pratchett), authorsTreeSet(none),
                                               diff("group[0]", pratchett, null, null)),
                                  Arguments.of(authorsTreeSet(none), authorsTreeSet(pratchett),
                                               diff("group[0]", null, pratchett, null))));
  }

  @ParameterizedTest(name = "authors {0} / object {1} / path {2} / value 1 {3}/ value 2 {4}")
  @MethodSource
  void should_fail_when_comparing_iterable_to_non_iterable(Object actualFieldValue, Collection<Author> expectedFieldValue,
                                                           String path, Object value1, Object value2, String desc) {
    // GIVEN
    WithObject actual = new WithObject(actualFieldValue);
    WithIterable<Author> expected = new WithIterable<>(expectedFieldValue);
    // WHEN/THEN
    ComparisonDifference difference = desc == null ? diff(path, value1, value2) : diff(path, value1, value2, desc);
    compareRecursivelyFailsWithDifferences(actual, expected, difference);
  }

  static Stream<Arguments> should_fail_when_comparing_iterable_to_non_iterable() {
    Author pratchett = new Author("Terry Pratchett");
    Author georgeMartin = new Author("George Martin");
    // We need to use the actual iterable and the expected list otherwise
    // compareRecursivelyFailsWithDifferences fails as actualIterable and expectedList description includes
    // their instance reference (e.g. @123ff3f) to differentiate their otherwise similar description.
    Author[] array = array(pratchett, georgeMartin);
    List<Author> orderedCollection = list(pratchett, georgeMartin);
    Set<Author> nonOrderedCollection = newHashSet(orderedCollection);
    return Stream.of(Arguments.of(pratchett, list(pratchett), "group", pratchett, list(pratchett),
                                  "expected field is an ordered collection but actual field is not (org.assertj.tests.core.api.recursive.data.Author), ordered collections are: [java.util.List, java.util.SortedSet, java.util.LinkedHashSet]"),
                     Arguments.of(array, orderedCollection, "group", array, orderedCollection,
                                  "expected field is an ordered collection but actual field is not (org.assertj.tests.core.api.recursive.data.Author[]), ordered collections are: [java.util.List, java.util.SortedSet, java.util.LinkedHashSet]"),
                     Arguments.of(array, nonOrderedCollection, "group", array, nonOrderedCollection,
                                  "expected field is an iterable but actual field is not (org.assertj.tests.core.api.recursive.data.Author[])"),
                     Arguments.of(pratchett, nonOrderedCollection, "group", pratchett, nonOrderedCollection,
                                  "expected field is an iterable but actual field is not (org.assertj.tests.core.api.recursive.data.Author)"));
  }

  @Test
  void should_report_unmatched_duplicate_elements_even_if_the_first_was_matched() {
    // GIVEN
    List<String> actual = list("aaa", "aaa");
    List<String> expected = list("aaa", "bbb");
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(actual).usingRecursiveComparison(recursiveComparisonConfiguration)
                                                                                 // simulate unordered collection
                                                                                 .ignoringCollectionOrder()
                                                                                 .isEqualTo(expected));
    // THEN
    then(assertionError).hasMessageContaining("The following expected elements were not matched in the actual ArrayList:%n  [\"bbb\"]".formatted());
  }

  // https://github.com/assertj/assertj/issues/2279
  @Test
  void should_not_throw_UnknownFormatConversionException_when_unmatched_elements_have_percent_in_their_description() {
    // GIVEN
    List<Tuple> first = list(tuple("VtQh0ZAo%s2FKCnQcirWL", "foo % %d"), tuple("%F", "VtQh0ZAo%s2FKCnQcirWL"));
    List<Tuple> second = list(tuple("%F", "VtQh0ZAo%s2FKCnQcirWL"), tuple("VtQh0ZAo%s2FKCnQcirWL", "bar % %d"));
    // WHEN
    Throwable thrown = catchThrowable(() -> assertThat(first).usingRecursiveComparison(recursiveComparisonConfiguration)
                                                             .ignoringCollectionOrder()
                                                             .isEqualTo(second));
    // THEN
    then(thrown).isNotInstanceOf(UnknownFormatConversionException.class);
  }

  public static class WithIterable<E> {
    public Iterable<E> group;

    public WithIterable(Iterable<E> iterable) {
      this.group = iterable;
    }

    @Override
    public String toString() {
      return "WithIterable(%s)".formatted(group);
    }

  }

  record Item(String name, int quantity) {
  }

  @Test
  void should_honor_representation_in_unmatched_elements_when_comparing_unordered_set() {
    // GIVEN
    Set<Item> expectedItems = newHashSet(new Item("Shoes", 2), new Item("Pants", 3));
    Set<Item> actualItems = newHashSet(new Item("Pants", 3), new Item("Loafers", 1));
    registerFormatterForType(Item.class, item -> "Item(%s, %d)".formatted(item.name(), item.quantity()));
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(actualItems).usingRecursiveComparison(recursiveComparisonConfiguration)
                                                                                      .isEqualTo(expectedItems));
    // THEN
    then(assertionError).hasMessageContaining(format("The following expected elements were not matched in the actual HashSet:%n" +
                                                     "  [Item(Shoes, 2)]"));
  }

  @ParameterizedTest
  @MethodSource
  void should_treat_null_and_empty_iterables_as_equal(Iterable<?> iterable1, Iterable<?> iterable2) {
    // GIVEN
    WithIterable<?> actual = new WithIterable<>(iterable1);
    WithIterable<?> expected = new WithIterable<>(iterable2);
    // THEN
    then(actual).usingRecursiveComparison(recursiveComparisonConfiguration)
                .treatingNullAndEmptyIterablesAsEqual()
                .isEqualTo(expected);
  }

  static Stream<Arguments> should_treat_null_and_empty_iterables_as_equal() {
    List<Author> emptyList = emptyList();
    Set<Author> emptySet = emptySet();
    Set<String> emptyTreeSet = new TreeSet<>();
    Set<String> emptyHashSet = new HashSet<>();
    return Stream.of(Arguments.of(null, emptyList),
                     Arguments.of(emptyList, null),
                     Arguments.of(null, emptySet),
                     Arguments.of(emptySet, null),
                     Arguments.of(null, emptyHashSet),
                     Arguments.of(emptyHashSet, null),
                     Arguments.of(null, emptyTreeSet),
                     Arguments.of(emptyTreeSet, null));
  }
}
