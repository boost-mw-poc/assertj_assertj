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
package org.assertj.tests.core.testkit;

/**
 * @author Alex Ruiz
 */
public final class ShortArrays {
  private static final short[] EMPTY = {};

  public static short[] arrayOf(int... values) {
    int size = values.length;
    short[] array = new short[size];
    for (int i = 0; i < size; i++) {
      array[i] = (short) values[i];
    }
    return array;
  }

  public static short[] emptyArray() {
    return EMPTY;
  }

  private ShortArrays() {}
}
