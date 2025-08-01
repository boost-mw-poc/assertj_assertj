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
package org.assertj.tests.core.api.recursive.comparison.dualvalue;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.Lists.list;
import static org.assertj.tests.core.testkit.Maps.mapOf;
import static org.assertj.tests.core.testkit.Maps.treeMapOf;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.recursive.comparison.DualValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

class DualValue_mapValues_Test {

  private static final List<String> PATH = list("foo", "bar");

  @ParameterizedTest
  @MethodSource("maps")
  void isActualAMap_should_return_true_when_actual_is_a_map(Object actual) {
    // GIVEN
    DualValue dualValue = new DualValue(PATH, actual, "");
    // WHEN
    boolean haveMapValues = dualValue.isActualAMap();
    // THEN
    then(haveMapValues).isTrue();
  }

  @ParameterizedTest
  @MethodSource("maps")
  void isExpectedAMap_should_return_true_when_expected_is_a_map(Object expected) {
    // GIVEN
    DualValue dualValue = new DualValue(PATH, "", expected);
    // WHEN
    boolean haveMapValues = dualValue.isExpectedAMap();
    // THEN
    then(haveMapValues).isTrue();
  }

  static Stream<Object> maps() {
    return Stream.of(singletonMap("a", "b"),
                     mapOf(entry(1, 2), entry(3, 4)),
                     treeMapOf(entry("a", "b")),
                     ImmutableMap.of("a", "b"),
                     ImmutableSortedMap.of("a", "b"));
  }

  @ParameterizedTest
  @MethodSource("nonMaps")
  void isActualAMap_should_return_false_when_actual_is_not_a_map(Object actual) {
    // GIVEN
    DualValue dualValue = new DualValue(PATH, actual, singletonMap("a", "b"));
    // WHEN
    boolean haveMapValues = dualValue.isActualAMap();
    // THEN
    then(haveMapValues).isFalse();
  }

  @ParameterizedTest
  @MethodSource("nonMaps")
  void isExpectedAMap_should_return_false_when_expected_is_not_a_map(Object expected) {
    // GIVEN
    DualValue dualValue = new DualValue(PATH, singletonMap("a", "b"), expected);
    // WHEN
    boolean haveMapValues = dualValue.isExpectedAMap();
    // THEN
    then(haveMapValues).isFalse();
  }

  static Stream<Object> nonMaps() {
    return Stream.of(list("a", "b"), "abc", 123, null);
  }

}
