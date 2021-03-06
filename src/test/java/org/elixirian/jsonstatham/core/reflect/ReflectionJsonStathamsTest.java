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
package org.elixirian.jsonstatham.core.reflect;

import static org.assertj.core.api.Assertions.*;
import static org.elixirian.kommonlee.test.CommonTestHelper.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.elixirian.jsonstatham.core.JsonStathamInAction;
import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.convertible.JsonArrayCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithUnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonScannerCreator;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.UnorderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider;
import org.elixirian.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.reflect.json2java.DefaultJsonToJavaConfig;
import org.elixirian.jsonstatham.core.reflect.json2java.ReflectionJsonToJavaConverter;
import org.elixirian.kommonlee.test.CommonTestHelper.Accessibility;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
 * @version 0.0.1 (2010-06-14)
 */
@RunWith(MockitoJUnitRunner.class)
public class ReflectionJsonStathamsTest
{
  @Mock
  private JsonScannerCreator jsonScannerCreator;

  @Mock
  private JsonObjectCreator jsonObjectCreator;

  @Mock
  private JsonArrayCreator jsonArrayCreator;

  @Mock
  private KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider;

  @Mock
  private KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider;

  @Mock
  private OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  @Test(expected = IllegalAccessException.class)
  public void testReflectionJsonStathams() throws Exception
  {
    testNotAccessibleConstructor(ReflectionJsonStathams.class, this, Accessibility.PRIVATE, classArrayOf(),
        objectArrayOf());
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams#newJsonStathamInAction(org.elixirian.jsonstatham.core.convertible.JsonObjectCreator, org.elixirian.jsonstatham.core.convertible.JsonArrayCreator, org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider, org.elixirian.jsonstatham.core.reflect.java2json.KnownObjectReferenceTypeProcessorDecider, org.elixirian.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider)}
   * .
   */
  @Test
  public final void testNewJsonStathamInActionJsonObjectConvertibleCreatorJsonArrayConvertibleCreatorKnownDataStructureTypeProcessorDeciderKnownObjectReferenceTypeProcessorDeciderOneProcessorForKnownTypeDecider()
  {
    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(jsonObjectCreator, jsonArrayCreator, knownDataStructureTypeProcessorDecider,
          knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider);
    final ReflectionJsonToJavaConverter reflectionJsonToJavaConverter =
      new ReflectionJsonToJavaConverter(DefaultJsonToJavaConfig.builder(jsonScannerCreator, jsonObjectCreator,
          jsonArrayCreator)
          .build());

    final JsonStathamInAction jsonStathamInAction =
      ReflectionJsonStathams.newJsonStathamInAction(reflectionJavaToJsonConverter, reflectionJsonToJavaConverter);

    assertThat(jsonStathamInAction.getJavaToJsonConverter()).isExactlyInstanceOf(ReflectionJavaToJsonConverter.class);
    assertThat(jsonStathamInAction.getJsonToJavaConverter()).isExactlyInstanceOf(ReflectionJsonToJavaConverter.class);

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverterFromJsonStathamInAction =
      (ReflectionJavaToJsonConverter) jsonStathamInAction.getJavaToJsonConverter();

    // TODO uncomment when ReflectionJsonToJavaConverter is done.
    // final ReflectionJsonToJavaConverter reflectionJsonToJavaConverterFromJsonStathamInAction =
    // (ReflectionJsonToJavaConverter) jsonStathamInAction.getJsonToJavaConverter();

    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator()).isEqualTo(
        jsonObjectCreator);
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator()).isEqualTo(
        jsonArrayCreator);
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownDataStructureTypeProcessorDecider()).isEqualTo(
        knownDataStructureTypeProcessorDecider);
    assertTrue(Arrays.deepEquals(new KnownTypeProcessorDeciderForJavaToJson[] { knownDataStructureTypeProcessorDecider,
        knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider },
        reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownTypeProcessorDeciders()));
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams#newReflectionJsonStathamInAction()}.
   */
  @Test
  public final void testNewJsonStathamInAction()
  {
    final JsonStathamInAction jsonStathamInAction = ReflectionJsonStathams.newReflectionJsonStathamInAction();

    assertThat(jsonStathamInAction.getJavaToJsonConverter()).isExactlyInstanceOf(ReflectionJavaToJsonConverter.class);
    assertThat(jsonStathamInAction.getJsonToJavaConverter()).isExactlyInstanceOf(ReflectionJsonToJavaConverter.class);

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverterFromJsonStathamInAction =
      (ReflectionJavaToJsonConverter) jsonStathamInAction.getJavaToJsonConverter();

    // TODO uncomment when ReflectionJsonToJavaConverter is done.
    // final ReflectionJsonToJavaConverter reflectionJsonToJavaConverterFromJsonStathamInAction =
    // (ReflectionJsonToJavaConverter) jsonStathamInAction.getJsonToJavaConverter();

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator(),
    // is((instanceOf(OrgJsonOrderedJsonObjectCreator.class));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator()).isExactlyInstanceOf(
        OrderedJsonObjectCreator.class);

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator(),
    // is(instanceOf(OrgJsonJsonArrayCreator.class);
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator()).isExactlyInstanceOf(
        JsonArrayWithOrderedJsonObjectCreator.class);

    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownDataStructureTypeProcessorDecider()).isExactlyInstanceOf(
        KnownDataStructureTypeProcessorDecider.class);

    final KnownTypeProcessorDeciderForJavaToJson[] knownTypeProcessorDeciders =
      reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownTypeProcessorDeciders();
    assertEquals(3, knownTypeProcessorDeciders.length);
    assertThat(knownTypeProcessorDeciders[0]).isExactlyInstanceOf(KnownDataStructureTypeProcessorDecider.class);
    assertThat(knownTypeProcessorDeciders[1]).isExactlyInstanceOf(KnownObjectReferenceTypeProcessorDecider.class);
    assertThat(knownTypeProcessorDeciders[2]).isExactlyInstanceOf(OneProcessorForKnownTypeDecider.class);

  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.ReflectionJsonStathams#newUnorderedReflectionJsonStathamInAction()} .
   */
  @Test
  public final void testNewUnorderedReflectionJsonStathamInAction()
  {
    final JsonStathamInAction jsonStathamInAction = ReflectionJsonStathams.newUnorderedReflectionJsonStathamInAction();

    assertThat(jsonStathamInAction.getJavaToJsonConverter()).isExactlyInstanceOf(ReflectionJavaToJsonConverter.class);
    assertThat(jsonStathamInAction.getJsonToJavaConverter()).isExactlyInstanceOf(ReflectionJsonToJavaConverter.class);

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverterFromJsonStathamInAction =
      (ReflectionJavaToJsonConverter) jsonStathamInAction.getJavaToJsonConverter();

    // TODO uncomment when ReflectionJsonToJavaConverter is done.
    // final ReflectionJsonToJavaConverter reflectionJsonToJavaConverterFromJsonStathamInAction =
    // (ReflectionJsonToJavaConverter) jsonStathamInAction.getJsonToJavaConverter();

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator(),
    // is((instanceOf(OrgJsonUnorderedJsonObjectCreator.class));
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonObjectConvertibleCreator()).isExactlyInstanceOf(
        UnorderedJsonObjectCreator.class);

    // TODO remove it after testing.
    // assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator(),
    // is(instanceOf(OrgJsonJsonArrayCreator.class);
    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getJsonArrayConvertibleCreator()).isExactlyInstanceOf(
        JsonArrayWithUnorderedJsonObjectCreator.class);

    assertThat(reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownDataStructureTypeProcessorDecider()).isExactlyInstanceOf(
        KnownDataStructureTypeProcessorDecider.class);

    final KnownTypeProcessorDeciderForJavaToJson[] knownTypeProcessorDeciders =
      reflectionJavaToJsonConverterFromJsonStathamInAction.getKnownTypeProcessorDeciders();
    assertEquals(3, knownTypeProcessorDeciders.length);
    assertThat(knownTypeProcessorDeciders[0]).isExactlyInstanceOf(KnownDataStructureTypeProcessorDecider.class);
    assertThat(knownTypeProcessorDeciders[1]).isExactlyInstanceOf(KnownObjectReferenceTypeProcessorDecider.class);
    assertThat(knownTypeProcessorDeciders[2]).isExactlyInstanceOf(OneProcessorForKnownTypeDecider.class);

  }
}
