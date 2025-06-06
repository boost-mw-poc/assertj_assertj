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
package org.assertj.tests.core.api.recursive.comparison.comparator;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.presentation.StandardRepresentation.STANDARD_REPRESENTATION;

import org.assertj.core.api.recursive.comparison.RecursiveComparator;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;

class RecursiveComparator_getDescription_Test {

  @Test
  void should_include_recursive_configuration_in_description() {
    // GIVEN
    RecursiveComparisonConfiguration recursiveComparisonConfiguration = new RecursiveComparisonConfiguration();
    recursiveComparisonConfiguration.setIgnoreAllActualNullFields(true);
    RecursiveComparator recursiveComparator = new RecursiveComparator(recursiveComparisonConfiguration);
    // WHEN
    String recursiveComparatorDescription = recursiveComparator.getDescription();
    // THEN
    then(recursiveComparatorDescription).contains("RecursiveComparator",
                                                  recursiveComparisonConfiguration.multiLineDescription(STANDARD_REPRESENTATION));
  }

}
