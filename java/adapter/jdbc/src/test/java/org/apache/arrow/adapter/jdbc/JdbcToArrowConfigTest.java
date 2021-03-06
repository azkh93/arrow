/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.arrow.adapter.jdbc;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.arrow.memory.BaseAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.Test;

public class JdbcToArrowConfigTest {

  private static final RootAllocator allocator = new RootAllocator(Integer.MAX_VALUE);
  private static final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ROOT);

  @Test(expected = NullPointerException.class)
  public void testConfigNullArguments() {
    new JdbcToArrowConfig(null, null);
  }

  @Test(expected = NullPointerException.class)
  public void testBuilderNullArguments() {
    new JdbcToArrowConfigBuilder(null, null);
  }

  @Test(expected = NullPointerException.class)
  public void testConfigNullCalendar() {
    new JdbcToArrowConfig(allocator, null);
  }

  @Test(expected = NullPointerException.class)
  public void testBuilderNullCalendar() {
    new JdbcToArrowConfigBuilder(allocator, null);
  }

  @Test(expected = NullPointerException.class)
  public void testConfigNullAllocator() {
    new JdbcToArrowConfig(null, calendar);
  }

  @Test(expected = NullPointerException.class)
  public void testBuilderNullAllocator() {
    new JdbcToArrowConfigBuilder(null, calendar);
  }

  @Test(expected = NullPointerException.class)
  public void testSetNullAllocator() {
    JdbcToArrowConfigBuilder builder = new JdbcToArrowConfigBuilder(allocator, calendar);
    builder.setAllocator(null);
  }

  @Test(expected = NullPointerException.class)
  public void testSetNullCalendar() {
    JdbcToArrowConfigBuilder builder = new JdbcToArrowConfigBuilder(allocator, calendar);
    builder.setCalendar(null);
  }

  @Test
  public void testConfig() {
    JdbcToArrowConfigBuilder builder = new JdbcToArrowConfigBuilder(allocator, calendar);
    JdbcToArrowConfig config = builder.build();

    assertTrue(allocator == config.getAllocator());
    assertTrue(calendar == config.getCalendar());

    Calendar newCalendar = Calendar.getInstance();
    BaseAllocator newAllocator = new RootAllocator(Integer.SIZE);

    builder.setAllocator(newAllocator).setCalendar(newCalendar);
    config = builder.build();

    assertTrue(newAllocator == config.getAllocator());
    assertTrue(newCalendar == config.getCalendar());
  }
}
