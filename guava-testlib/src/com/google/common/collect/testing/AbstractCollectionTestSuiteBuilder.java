/*
 * Copyright (C) 2008 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect.testing;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.testing.testers.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Abstract superclass of all test-suite builders for collection interfaces.
 *
 * @author George van den Driessche
 */
@GwtIncompatible
public abstract class AbstractCollectionTestSuiteBuilder<
        B extends AbstractCollectionTestSuiteBuilder<B, E>, E>
    extends PerCollectionSizeTestSuiteBuilder<B, TestCollectionGenerator<E>, Collection<E>, E> {
  // Class parameters must be raw.
  @SuppressWarnings("unchecked")
  @Override
  protected List<Class<? extends AbstractTester>> getTesters() {
    return Arrays.<Class<? extends AbstractTester>>asList(
        CollectionAddAllTester.class,
        CollectionAddTester.class,
        CollectionClearTester.class,
        CollectionContainsAllTester.class,
        CollectionContainsTester.class,
        CollectionCreationTester.class,
        CollectionEqualsTester.class,
        CollectionForEachTester.class,
        CollectionIsEmptyTester.class,
        CollectionIteratorTester.class,
        CollectionRemoveAllTester.class,
        CollectionRemoveIfTester.class,
        CollectionRemoveTester.class,
        CollectionRetainAllTester.class,
        CollectionSerializationTester.class,
        CollectionSizeTester.class,
        CollectionSpliteratorTester.class,
        CollectionStreamTester.class,
        CollectionToArrayTester.class,
        CollectionToStringTester.class);
  }
}
