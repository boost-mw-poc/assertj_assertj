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
package org.assertj.core.api.optionallong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.assertj.core.error.OptionalShouldContain.shouldContain;
import static org.assertj.core.util.AssertionsUtil.assertThatAssertionErrorIsThrownBy;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import java.util.OptionalLong;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

class OptionalLongAssert_hasValue_Test {

  @Test
  void should_fail_when_OptionalLong_is_null() {
    // GIVEN
    OptionalLong nullActual = null;
    // THEN
    assertThatAssertionErrorIsThrownBy(() -> assertThat(nullActual).hasValue(10L)).withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_OptionalLong_has_expected_value() {
    assertThat(OptionalLong.of(10L)).hasValue(10L);
  }

  @Test
  void should_fail_if_OptionalLong_does_not_have_expected_value() {
    // GIVEN
    OptionalLong actual = OptionalLong.of(5L);
    long expectedValue = 10L;
    // WHEN
    AssertionFailedError error = catchThrowableOfType(AssertionFailedError.class,
                                                      () -> assertThat(actual).hasValue(expectedValue));
    // THEN
    assertThat(error).hasMessage(shouldContain(actual, expectedValue).create());
    assertThat(error.getActual().getStringRepresentation()).isEqualTo(String.valueOf(actual.getAsLong()));
    assertThat(error.getExpected().getStringRepresentation()).isEqualTo(String.valueOf(expectedValue));
  }

  @Test
  void should_fail_if_OptionalLong_is_empty() {
    // GIVEN
    long expectedValue = 10L;
    // WHEN
    Throwable error = catchThrowable(() -> assertThat(OptionalLong.empty()).hasValue(expectedValue));
    // THEN
    assertThat(error).hasMessage(shouldContain(expectedValue).create());
  }
}
