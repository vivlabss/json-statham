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
package org.elixirian.jsonstatham.core;

import java.lang.reflect.InvocationTargetException;

import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.io.CharReadable;
import org.elixirian.kommonlee.reflect.TypeHolder;

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
 * @version 0.0.1 (2010-09-08)
 */
public interface JsonToJavaConverter
{
  <T> T convertFromJson(Class<T> targetClass, String jsonString) throws JsonStathamException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException;

  <T> T convertFromJson(TypeHolder<T> typeHolder, String jsonString) throws JsonStathamException,
      IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException;

  <T> T convertFromJson(Class<T> targetClass, CharReadable charReadable) throws JsonStathamException,
      IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException;

  <T> T convertFromJson(TypeHolder<T> typeHolder, CharReadable charReadable) throws JsonStathamException,
      IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException;

  <T> T convertFromJsonConvertible(Class<T> targetClass, JsonConvertible jsonConvertible) throws JsonStathamException,
      IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException;

  <T> T convertFromJsonConvertible(TypeHolder<T> typeHolder, JsonConvertible jsonConvertible)
      throws JsonStathamException, IllegalArgumentException, InstantiationException, IllegalAccessException,
      InvocationTargetException;

  JsonConvertible convertJsonStringIntoJsonConvertible(String json) throws JsonStathamException;
}
