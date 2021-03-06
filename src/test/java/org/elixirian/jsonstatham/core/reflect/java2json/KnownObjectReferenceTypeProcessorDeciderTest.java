/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elixirian.jsonstatham.core.reflect.java2json;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-06-11)
 */
public class KnownObjectReferenceTypeProcessorDeciderTest
{
  private static final Date DATE = new Date();
  private static final Calendar CALENDAR = Calendar.getInstance();
  private static final Map<String, String> MAP = new HashMap<String, String>();

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider#KnownObjectReferenceTypeProcessorDecider()}
   * .
   */
  @Test
  public final void testKnownObjectReferenceTypeProcessorDecider()
  {
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new KnownObjectReferenceTypeProcessorDecider();
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(DATE.getClass())).isNotNull();
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(CALENDAR.getClass())).isNotNull();

    for (final Entry<String, String> entry : MAP.entrySet())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(entry.getClass())).isNotNull();
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(new ArrayList<String>().getClass())).isNull();
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider#KnownObjectReferenceTypeProcessorDecider(java.util.Map)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testKnownObjectReferenceTypeProcessorDeciderMapOfClassOfQKnownTypeProcessor()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    class TestClass
    {
      private final Long id;
      private final String name;

      public TestClass(final Long id, final String name)
      {
        this.id = id;
        this.name = name;
      }
    }

    final TestClass testClass = new TestClass(Long.valueOf(999L), "Kevin");

    final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter> map =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConverter>();
    map.put(TestClass.class, new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
      @Override
      public <T> Object process(
          @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final TestClass testClassObject = (TestClass) value;
        return "id: " + testClassObject.id + " | name: " + testClassObject.name;
      }
    });
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new KnownObjectReferenceTypeProcessorDecider(map);

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(testClass.getClass())).isNotNull();
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(testClass.getClass())
        .process(null, testClass.getClass(), testClass)).isEqualTo("id: " + testClass.id + " | name: " + testClass.name);

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(DATE.getClass())).isNull();
    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(CALENDAR.getClass())).isNull();

    for (final Entry<String, String> entry : MAP.entrySet())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(entry.getClass())).isNull();
    }
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider#decide(java.lang.Class)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @SuppressWarnings("unchecked")
  @Test
  public final void testDecide() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      mock(KnownDataStructureTypeProcessorDecider.class);
    when(knownDataStructureTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);
    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(String.class)).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              @SuppressWarnings("unused") final Class<T> valueType, final Object value)
              throws IllegalArgumentException, IllegalAccessException, JsonStathamException
          {
            return value;
          }
        });

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(new OrderedJsonObjectCreator(), new JsonArrayWithOrderedJsonObjectCreator(),
          knownDataStructureTypeProcessorDecider, new KnownObjectReferenceTypeProcessorDecider(),
          oneProcessorForKnownTypeDecider);
    // final JsonStathamInAction jsonStathamInAction =
    // new JsonStathamInAction(reflectionJavaToJsonConverter, new ReflectionJsonToJavaConverter());
    final KnownTypeProcessorDeciderForJavaToJson knownTypeProcessorDeciderForJavaToJson =
      new KnownObjectReferenceTypeProcessorDecider();

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(DATE.getClass())
        .process(reflectionJavaToJsonConverter, DATE.getClass(), DATE)).isEqualTo(
        reflectionJavaToJsonConverter.createJsonValue(DATE.toString()));

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(CALENDAR.getClass())
        .process(reflectionJavaToJsonConverter, CALENDAR.getClass(), CALENDAR)).isEqualTo(
        reflectionJavaToJsonConverter.createJsonValue(CALENDAR.getTime()
            .toString()));

    for (final Entry<String, String> entry : MAP.entrySet())
    {
      assertThat(knownTypeProcessorDeciderForJavaToJson.decide(entry.getClass())
          .process(reflectionJavaToJsonConverter, entry.getClass(), entry)
          .toString()).isEqualTo(reflectionJavaToJsonConverter.newJsonObjectConvertible()
          .put(entry.getKey(), reflectionJavaToJsonConverter.createJsonValue(entry.getValue()))
          .toString());
    }

    assertThat(knownTypeProcessorDeciderForJavaToJson.decide(new ArrayList<String>().getClass())).isNull();
  }
}
