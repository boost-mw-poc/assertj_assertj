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
package org.assertj.core.internal.dates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldHaveDateField.shouldHaveDateField;
import static org.assertj.core.testkit.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Dates;
import org.assertj.core.internal.DatesBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Dates#assertHasMonth(AssertionInfo, Date, int)}</code>.
 * 
 * @author Joel Costigliola
 */
class Dates_assertHasMonth_Test extends DatesBaseTest {

  @Test
  void should_fail_if_actual_has_not_given_month() {
    AssertionInfo info = someInfo();
    int month = 5;

    Throwable error = catchThrowable(() -> dates.assertHasMonth(info, actual, month));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldHaveDateField(actual, "month", month));
  }

  @Test
  void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> dates.assertHasMonth(someInfo(), null, 1))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_actual_has_given_month() {
    dates.assertHasMonth(someInfo(), actual, 1);
  }

  @Test
  void should_fail_if_actual_has_not_given_month_whatever_custom_comparison_strategy_is() {
    AssertionInfo info = someInfo();
    int month = 5;

    Throwable error = catchThrowable(() -> datesWithCustomComparisonStrategy.assertHasMonth(info, actual, month));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldHaveDateField(actual, "month", month));
  }

  @Test
  void should_fail_if_actual_is_null_whatever_custom_comparison_strategy_is() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> datesWithCustomComparisonStrategy.assertHasMonth(someInfo(),
                                                                                                                      null, 1))
                                                   .withMessage(actualIsNull());
  }

  @Test
  void should_pass_if_actual_has_given_month_whatever_custom_comparison_strategy_is() {
    datesWithCustomComparisonStrategy.assertHasMonth(someInfo(), actual, 1);
  }

}
