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
package org.assertj.core.api.short2darray;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.description.EmptyTextDescription.emptyDescription;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.error.ShouldNotBeNull.shouldNotBeNull;
import static org.assertj.core.error.SubarraysShouldHaveSameSize.subarraysShouldHaveSameSize;
import static org.assertj.core.error.array2d.Array2dElementShouldBeDeepEqual.elementShouldBeEqual;
import static org.assertj.core.presentation.StandardRepresentation.STANDARD_REPRESENTATION;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;

import org.assertj.core.api.Short2DArrayAssert;
import org.assertj.core.error.ErrorMessageFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Short2DArrayAssert#isDeepEqualTo(short[][])}</code>.
 *
 * @author Maciej Wajcht
 */
@DisplayName("Short2DArrayAssert isDeepEqualTo")
class Short2DArrayAssert_isDeepEqualTo_Test {

  @Test
  void should_pass_if_both_actual_and_expected_are_null() {
    // GIVEN
    short[][] actual = null;
    short[][] expected = null;
    // WHEN/THEN
    then(actual).isDeepEqualTo(expected);
  }

  @Test
  void should_pass_if_both_actual_and_expected_are_same_arrays() {
    // GIVEN
    short[][] actual = new short[][] { { 1, 2 }, { 3 }, { 4, 5, 6 } };
    short[][] expected = actual;
    // WHEN/THEN
    then(actual).isDeepEqualTo(expected);
  }

  @Test
  void should_pass_if_both_actual_and_expected_are_equal() {
    // GIVEN
    short[][] actual = new short[][] { { 1, 2 }, { 3 }, { 4, 5, 6 } };
    short[][] expected = new short[][] { { 1, 2 }, { 3 }, { 4, 5, 6 } };
    // WHEN/THEN
    then(actual).isDeepEqualTo(expected);
  }

  @Test
  void should_fail_if_actual_is_null() {
    // GIVEN
    short[][] actual = null;
    short[][] expected = new short[][] { { 1, 2 }, { 3 }, { 4, 5, 6 } };
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> then(actual).isDeepEqualTo(expected));
    // THEN
    then(assertionError).hasMessage(shouldNotBeNull().create());
  }

  @Test
  void should_fail_if_actual_in_second_dimension_is_null() {
    // GIVEN
    short[][] actual = new short[][] { { 1, 2 }, null, { 4, 5, 6 } };
    short[][] expected = new short[][] { { 1, 2 }, { 3 }, { 4, 5, 6 } };
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> then(actual).isDeepEqualTo(expected));
    // THEN
    then(assertionError).hasMessage(shouldNotBeNull("actual[1]").create());
  }

  @Test
  void should_fail_if_first_dimension_size_is_different() {
    // GIVEN
    short[][] actual = new short[][] { { 1, 2 }, { 3 } };
    short[][] expected = new short[][] { { 1, 2 }, { 3 }, { 4, 5, 6 } };
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> then(actual).isDeepEqualTo(expected));
    // THEN
    then(assertionError).hasMessage(shouldHaveSameSizeAs(actual, expected, actual.length, expected.length).create());
  }

  @Test
  void should_fail_if_second_dimension_size_is_different() {
    // GIVEN
    short[][] actual = new short[][] { { 1, 2 }, { 3, 999 }, { 4, 5, 6 } };
    short[][] expected = new short[][] { { 1, 2 }, { 3 }, { 4, 5, 6 } };
    short[] actualSubArrayWithDifference = new short[] { 3, 999 };
    short[] expectedSubArrayWithDifference = new short[] { 3 };
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> then(actual).isDeepEqualTo(expected));
    // THEN
    ErrorMessageFactory subarraysShouldHaveSameSize = subarraysShouldHaveSameSize(actual, expected,
                                                                                  actualSubArrayWithDifference,
                                                                                  actualSubArrayWithDifference.length,
                                                                                  expectedSubArrayWithDifference,
                                                                                  expectedSubArrayWithDifference.length,
                                                                                  1);
    then(assertionError).hasMessage(subarraysShouldHaveSameSize.create());
  }

  @Test
  void should_fail_if_one_value_in_second_dimension_is_different() {
    // GIVEN
    short actualValue = 999;
    short expectedValue = 3;
    short[][] actual = new short[][] { { 1, 2 }, { actualValue }, { 4, 5, 6 } };
    short[][] expected = new short[][] { { 1, 2 }, { expectedValue }, { 4, 5, 6 } };
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> then(actual).isDeepEqualTo(expected));
    // THEN
    ErrorMessageFactory elementShouldBeEqual = elementShouldBeEqual(actualValue, expectedValue, 1, 0);
    then(assertionError).hasMessage(elementShouldBeEqual.create(emptyDescription(), STANDARD_REPRESENTATION));
  }
}
