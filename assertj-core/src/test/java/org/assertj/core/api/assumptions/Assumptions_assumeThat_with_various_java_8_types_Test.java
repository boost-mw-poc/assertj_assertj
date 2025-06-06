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
package org.assertj.core.api.assumptions;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.assertj.core.api.Assumptions.assumeThatCode;
import static org.assertj.core.api.Assumptions.assumeThatTemporal;
import static org.assertj.core.util.AssertionsUtil.expectAssumptionNotMetException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class Assumptions_assumeThat_with_various_java_8_types_Test {

  public static Stream<AssumptionRunner<?>> provideAssumptionsRunners() {
    return Stream.of(
                     new AssumptionRunner<ThrowingCallable>(() -> {}) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThatCode(actual).isInstanceOf(NullPointerException.class);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThatCode(actual).doesNotThrowAnyException();
                       }
                     },
                     new AssumptionRunner<Instant>(Instant.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isAfter(Instant.now());
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isBefore(Instant.now().plusSeconds(100));
                       }
                     },
                     new AssumptionRunner<LocalDate>(LocalDate.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isAfter(LocalDate.now());
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isBefore(LocalDate.now().plusDays(1));
                       }
                     },
                     new AssumptionRunner<LocalDateTime>(LocalDateTime.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isAfter(LocalDateTime.now());
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isBefore(LocalDateTime.now().plusDays(1));
                       }
                     },
                     new AssumptionRunner<LocalTime>(LocalTime.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isAfter(LocalTime.MAX);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isBefore(LocalTime.MAX);
                       }
                     },
                     new AssumptionRunner<OffsetDateTime>(OffsetDateTime.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isAfter(OffsetDateTime.now());
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isBefore(OffsetDateTime.now().plusSeconds(100));
                       }
                     },
                     new AssumptionRunner<OffsetTime>(OffsetTime.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isAfter(OffsetTime.now());
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isBefore(OffsetTime.now().plusSeconds(100));
                       }
                     },
                     new AssumptionRunner<ZonedDateTime>(ZonedDateTime.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isAfter(ZonedDateTime.now());
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isBefore(ZonedDateTime.now().plusSeconds(100));
                       }
                     },
                     new AssumptionRunner<Optional<String>>(Optional.of("test")) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().contains("other");
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().contains("test");
                       }
                     },
                     new AssumptionRunner<OptionalDouble>(OptionalDouble.of(2.0)) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().hasValue(1.0);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().hasValue(2.0);
                       }
                     },
                     new AssumptionRunner<OptionalInt>(OptionalInt.of(2)) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().hasValue(1);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().hasValue(2);
                       }
                     },
                     new AssumptionRunner<OptionalLong>(OptionalLong.of(2L)) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().hasValue(1L);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().hasValue(2L);
                       }
                     },
                     new AssumptionRunner<CompletableFuture<String>>(completedFuture("test")) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isCancelled();
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isCompleted();
                       }
                     },
                     new AssumptionRunner<Predicate<String>>(Predicate.isEqual("test")) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().accepts("other");
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().accepts("test");
                       }
                     },
                     new AssumptionRunner<DoublePredicate>(number -> number == 0) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().accepts(1.0);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().accepts(0.0);
                       }
                     },
                     new AssumptionRunner<IntPredicate>(number -> number == 0) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().accepts(1);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().accepts(0);
                       }
                     },
                     new AssumptionRunner<LongPredicate>(number -> number == 0) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().accepts(1L);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().accepts(0L);
                       }
                     },
                     new AssumptionRunner<Stream<String>>(Stream.of("test")) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().contains("other");
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().contains("test");
                       }
                     },
                     new AssumptionRunner<DoubleStream>(DoubleStream.of(0.0)) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().contains(1.0);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().contains(0.0);
                       }
                     },
                     new AssumptionRunner<IntStream>(IntStream.of(0)) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().contains(1);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().contains(0);
                       }
                     },
                     new AssumptionRunner<LongStream>(LongStream.of(0)) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().contains(1L);
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().contains(0L);
                       }
                     },
                     new AssumptionRunner<Duration>(Duration.ofHours(1)) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThat(actual).isNotNull().isNegative();
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThat(actual).isNotNull().isPositive();
                       }
                     },
                     new AssumptionRunner<Temporal>(ZonedDateTime.now()) {
                       @Override
                       public void runFailingAssumption() {
                         assumeThatTemporal(actual).isNotNull().isCloseTo(ZonedDateTime.now().plusMinutes(5),
                                                                          within(1, ChronoUnit.MINUTES));
                       }

                       @Override
                       public void runPassingAssumption() {
                         assumeThatTemporal(actual).isNotNull().isCloseTo(ZonedDateTime.now(), within(10, ChronoUnit.MINUTES));
                       }
                     });
  }

  @ParameterizedTest
  @MethodSource("provideAssumptionsRunners")
  void should_ignore_test_when_assumption_fails(AssumptionRunner<?> assumptionRunner) {
    expectAssumptionNotMetException(assumptionRunner::runFailingAssumption);
  }

  @ParameterizedTest
  @MethodSource("provideAssumptionsRunners")
  void should_run_test_when_assumption_passes(AssumptionRunner<?> assumptionRunner) {
    assertThatCode(assumptionRunner::runPassingAssumption).doesNotThrowAnyException();
  }

}
