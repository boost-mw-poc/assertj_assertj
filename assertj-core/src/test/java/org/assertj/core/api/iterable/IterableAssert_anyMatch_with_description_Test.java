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
package org.assertj.core.api.iterable;

import static org.mockito.Mockito.verify;

import java.util.Objects;
import java.util.function.Predicate;

import org.assertj.core.api.ConcreteIterableAssert;
import org.assertj.core.api.IterableAssertBaseTest;
import org.assertj.core.presentation.PredicateDescription;
import org.junit.jupiter.api.BeforeEach;

class IterableAssert_anyMatch_with_description_Test extends IterableAssertBaseTest {

  private Predicate<Object> predicate;

  @BeforeEach
  void beforeOnce() {
    predicate = Objects::nonNull;
  }

  @Override
  protected ConcreteIterableAssert<Object> invoke_api_method() {
    return assertions.anyMatch(predicate, "custom");
  }

  @Override
  protected void verify_internal_effects() {
    verify(iterables).assertAnyMatch(getInfo(assertions), getActual(assertions), predicate, new PredicateDescription("custom"));
  }
}
