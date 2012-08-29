/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original KommonLee project is owned by Lee, Seong Hyun (Kevin).
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
package org.elixirian.jsonstatham.core.reflect.json2java;

import static org.elixirian.kommonlee.util.MessageFormatter.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObject;
import org.elixirian.jsonstatham.core.convertible.OrgJsonJsonObject;
import org.elixirian.jsonstatham.core.convertible.UnorderedJsonObject;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.json.JSONObject;

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
 * @version 0.0.1 (2010-10-04)
 */
public final class JsonToJavaKnownObjectTypeProcessorDecider implements
		KnownTypeProcessorWithReflectionJsonToJavaConverterDeciderForJsonToJava<Class<?>>
{
	public static final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;

	static
	{
		final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> map =
			new LinkedHashMap<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>>();
		map.put(Date.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(
					@SuppressWarnings("unused") final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				if (long.class.equals(value.getClass()) || Long.class.equals(value.getClass()))
				{
					return new Date(((Long) value).longValue());
				}
				throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
			}
		});

		map.put(Calendar.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(
					@SuppressWarnings("unused") final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				if (long.class.equals(value.getClass()) || Long.class.equals(value.getClass()))
				{
					final Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(((Long) value).longValue());
					return calendar;
				}
				throw new JsonStathamException(format("Unknown type [class: %s][object: %s]", valueType, value));
			}

		});

		map.put(JSONObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, new OrgJsonJsonObject((JSONObject) value));
			}
		});

		map.put(OrderedJsonObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, (OrderedJsonObject) value);
			}

		});

		map.put(UnorderedJsonObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, (UnorderedJsonObject) value);
			}

		});

		map.put(JsonObject.class, new KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>() {
			@Override
			public <T> Object process(final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter,
					final Class<?> valueType, final Object value) throws IllegalArgumentException, IllegalAccessException,
					JsonStathamException
			{
				final JsonObject castedValue = (JsonObject) value;
				if (castedValue.isNull())
				{
					return null;
				}
				return reflectionJsonToJavaConverter.createFromJsonObject(valueType, castedValue);
			}

		});

		DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP = Collections.unmodifiableMap(map);
	}

	public final Map<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> KnownObjectTypeProcessorMap;

	public JsonToJavaKnownObjectTypeProcessorDecider()
	{
		this.KnownObjectTypeProcessorMap = DEFAULT_KNOWN_OBJECT_TYPE_PROCESSOR_MAP;
	}

	@Override
	public KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>> decide(final Class<?> type)
	{
		/* @formatter:off */
    for (final Entry<Class<?>, KnownTypeProcessorWithReflectionJsonToJavaConverter<Class<?>>> entry :
            KnownObjectTypeProcessorMap.entrySet())
    {
      /* @formatter:on */
			if (entry.getKey()
					.isAssignableFrom(type))
			{
				return entry.getValue();
			}
		}
		return null;
	}

}
