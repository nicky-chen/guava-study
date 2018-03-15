/*
 * Copyright (C) 2009 The Guava Authors
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

package com.google.common.util.concurrent;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.*;

/**
 * Used to test listenable future implementations.
 *
 * @author Sven Mawson
 */
public class ListenableFutureTester {

  private final ExecutorService exec;
  private final ListenableFuture<?> future;
  private final CountDownLatch latch;

  public ListenableFutureTester(ListenableFuture<?> future) {
    this.exec = Executors.newCachedThreadPool();
    this.future = checkNotNull(future);
    this.latch = new CountDownLatch(1);
  }

  public void setUp() {
    future.addListener(
        new Runnable() {
          @Override
          public void run() {
            latch.countDown();
          }
        },
        exec);

    assertEquals(1, latch.getCount());
    assertFalse(future.isDone());
    assertFalse(future.isCancelled());
  }

  public void tearDown() {
    exec.shutdown();
  }

  public void testCompletedFuture(@NullableDecl Object expectedValue)
      throws InterruptedException, ExecutionException {
    assertTrue(future.isDone());
    assertFalse(future.isCancelled());

    assertTrue(latch.await(5, TimeUnit.SECONDS));
    assertTrue(future.isDone());
    assertFalse(future.isCancelled());

    assertEquals(expectedValue, future.get());
  }

  public void testCancelledFuture() throws InterruptedException, ExecutionException {
    assertTrue(future.isDone());
    assertTrue(future.isCancelled());

    assertTrue(latch.await(5, TimeUnit.SECONDS));
    assertTrue(future.isDone());
    assertTrue(future.isCancelled());

    try {
      future.get();
      fail("Future should throw CancellationException on cancel.");
    } catch (CancellationException expected) {
    }
  }

  public void testFailedFuture(@NullableDecl String message) throws InterruptedException {
    assertTrue(future.isDone());
    assertFalse(future.isCancelled());

    assertTrue(latch.await(5, TimeUnit.SECONDS));
    assertTrue(future.isDone());
    assertFalse(future.isCancelled());

    try {
      future.get();
      fail("Future should rethrow the exception.");
    } catch (ExecutionException e) {
      assertThat(e.getCause()).hasMessage(message);
    }
  }
}
