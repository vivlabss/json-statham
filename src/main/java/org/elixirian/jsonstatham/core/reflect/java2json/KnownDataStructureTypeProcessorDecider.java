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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.exception.JsonStathamException;

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
 * @version 0.0.1 (2010-06-10)
 */
public class KnownDataStructureTypeProcessorDecider implements KnownTypeProcessorDeciderForJavaToJson
{
  public static final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter> DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;

  static
  {
    final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter> tempMap =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter>();
    tempMap.put(JsonArray.class, new KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter() {
      @Override
      public <T> JsonConvertible process(
          @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        return (JsonArray) value;
      }
    });
    tempMap.put(Array.class, new KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter() {
      @Override
      public <T> JsonConvertible process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (int i = 0, size = Array.getLength(value); i < size; i++)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(Array.get(value, i)));
        }
        return jsonArray;
      }
    });

    tempMap.put(Collection.class, new KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> JsonConvertible process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (final Object eachElement : (Collection<Object>) value)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
        }
        return jsonArray;
      }
    });
    tempMap.put(Iterable.class, new KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> JsonConvertible process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (final Object eachElement : (Iterable<Object>) value)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(eachElement));
        }
        return jsonArray;
      }
    });
    tempMap.put(Iterator.class, new KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> JsonConvertible process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (final Iterator<Object> iterator = (Iterator<Object>) value; iterator.hasNext();)
        {
          jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(iterator.next()));
        }
        return jsonArray;
      }
    });
    tempMap.put(Map.class, new KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> JsonConvertible process(final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          @SuppressWarnings("unused") final Class<T> valueType, final Object value) throws IllegalArgumentException,
          IllegalAccessException, JsonStathamException
      {
        final JsonObject jsonObject = reflectionJavaToJsonConverter.newJsonObjectConvertible();
        for (final Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet())
        {
          jsonObject.put(String.valueOf(entry.getKey()),
              reflectionJavaToJsonConverter.createJsonValue(entry.getValue()));
        }
        return jsonObject;
      }
    });
    DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP = Collections.unmodifiableMap(tempMap);
  }

  private final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter> knownDataStructuresProcessorMap;

  public KnownDataStructureTypeProcessorDecider()
  {
    knownDataStructuresProcessorMap = DEFAULT_KNOWN_DATA_STRUCTURES_PROCESSOR_MAP;
  }

  public KnownDataStructureTypeProcessorDecider(
      final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter> knownDataStructuresProcessorMap)
  {
    this.knownDataStructuresProcessorMap = Collections.unmodifiableMap(knownDataStructuresProcessorMap);
  }

  @Override
  public <T> KnownTypeProcessorWithReflectionJavaToJsonConverter decide(final Class<T> type)
  {
    if (type.isArray())
    {
      return knownDataStructuresProcessorMap.get(Array.class);
    }

    for (final Entry<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter> entry : knownDataStructuresProcessorMap.entrySet())
    {
      if (entry.getKey()
          .isAssignableFrom(type))
      {
        return entry.getValue();
      }
    }
    return null;
  }

}
