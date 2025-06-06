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
package org.assertj.core.api;

import static org.assertj.core.testkit.CharArrays.emptyArray;
import static org.mockito.Mockito.mock;

import org.assertj.core.internal.CharArrays;

/**
 * Base class for {@link CharArrayAssert} tests.
 * 
 * @author Olivier Michallat
 */
public abstract class CharArrayAssertBaseTest extends BaseTestTemplate<CharArrayAssert, char[]> {
  protected CharArrays arrays;

  @Override
  protected CharArrayAssert create_assertions() {
    return new CharArrayAssert(emptyArray());
  }

  @Override
  protected void inject_internal_objects() {
    super.inject_internal_objects();
    arrays = mock(CharArrays.class);
    assertions.arrays = arrays;
  }

  protected CharArrays getArrays(CharArrayAssert someAssertions) {
    return someAssertions.arrays;
  }
}
