/**
 * 
 */
package org.elixirian.jsonstatham.core.reflect.java2json;

import static org.elixirian.kommonlee.util.MessageFormatter.*;
import static org.elixirian.kommonlee.util.Strings.*;
import static org.elixirian.kommonlee.validation.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.ValueAccessor;
import org.elixirian.jsonstatham.core.JavaToJsonConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorDeciderForJavaToJson;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonArrayConvertibleCreator;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.JsonObjectConvertibleCreator;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.reflect.Classes;

/**
 * <pre>
 *     ___  _____  __________  ___________ _____  ____
 *    /   \/    / /      \   \/   /_    _//     \/   /
 *   /        /  /    ___/\      / /   / /          /
 *  /        \  /    ___/  \    /_/   /_/          /
 * /____/\____\/_______/    \__//______/___/\_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-09-8)
 */
public class ReflectionJavaToJsonConverter implements JavaToJsonConverter
{
  private static final Class<?>[] EMPTY_CLASS_ARRAY = Classes.classArrayOf();
  private static final Object[] EMPTY_OBJECT_ARRAY = Classes.objectArrayOf();

  private final JsonObjectConvertibleCreator jsonObjectConvertibleCreator;

  private final JsonArrayConvertibleCreator jsonArrayConvertibleCreator;

  private final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider;
  private final KnownTypeProcessorDeciderForJavaToJson[] knownTypeProcessorDeciderForJavaToJsons;

  public ReflectionJavaToJsonConverter(final JsonObjectConvertibleCreator jsonObjectConvertibleCreator,
      final JsonArrayConvertibleCreator jsonArrayConvertibleCreator,
      final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider,
      final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider,
      final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider)
  {
    this.jsonObjectConvertibleCreator = jsonObjectConvertibleCreator;
    this.jsonArrayConvertibleCreator = jsonArrayConvertibleCreator;
    this.knownDataStructureTypeProcessorDecider = knownDataStructureTypeProcessorDecider;
    this.knownTypeProcessorDeciderForJavaToJsons =
      new KnownTypeProcessorDeciderForJavaToJson[] { knownDataStructureTypeProcessorDecider,
          knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider };
  }

  public JsonObjectConvertibleCreator getJsonObjectConvertibleCreator()
  {
    return jsonObjectConvertibleCreator;
  }

  public JsonArrayConvertibleCreator getJsonArrayConvertibleCreator()
  {
    return jsonArrayConvertibleCreator;
  }

  public KnownDataStructureTypeProcessorDecider getKnownDataStructureTypeProcessorDecider()
  {
    return knownDataStructureTypeProcessorDecider;
  }

  public KnownTypeProcessorDeciderForJavaToJson[] getKnownTypeProcessorDeciders()
  {
    return knownTypeProcessorDeciderForJavaToJsons;
  }

  /**/

  public JsonObject newJsonObjectConvertible()
  {
    return jsonObjectConvertibleCreator.newJsonObjectConvertible();
  }

  public JsonObject nullJsonObjectConvertible()
  {
    return jsonObjectConvertibleCreator.nullJsonObjectConvertible();
  }

  public JsonArray newJsonArrayConvertible()
  {
    return jsonArrayConvertibleCreator.newJsonArrayConvertible();
  }

  /**
   * Creates a JSON object which is {@link JsonObject} containing all the fields annotated with the
   * {@link JsonField} annotation. The value of the given sourceObject must not be a null reference.
   * 
   * @param sourceObject
   *          the given source object to be converted into {@link JsonObject}.
   * @return The {@link JsonObject} object created based on the given sourceObject.
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws JsonStathamException
   */
  private Object createJsonObject(final Object sourceObject) throws IllegalArgumentException, IllegalAccessException,
      JsonStathamException
  {
    Class<?> sourceClass = sourceObject.getClass();

    final Deque<Class<?>> classStack = new ArrayDeque<Class<?>>();
    while (!Object.class.equals(sourceClass))
    {
      if (sourceClass.isAnnotationPresent(Json.class))
      {
        /* add if the class is annotated with @Json. Otherwise ignore it as it is not a JSON Object */
        classStack.push(sourceClass);
      }
      sourceClass = sourceClass.getSuperclass();
    }

    assertFalse(classStack.isEmpty(), "The target object is not a JSON object. " + "It must be annotated with %s.\n"
        + "[class: %s]\n[object: %s]", Json.class.getName(), sourceClass, sourceObject);

    final Set<String> fieldNameSet = new HashSet<String>();
    final JsonObject jsonObject = newJsonObjectConvertible();
    for (final Class<?> eachClass : classStack)
    {
      extractJsonFields(sourceObject, eachClass, fieldNameSet, jsonObject);
    }
    return jsonObject;
  }

  private void extractJsonFields(final Object source, final Class<?> sourceClass, final Set<String> fieldNameSet,
      final JsonObject jsonObject) throws IllegalAccessException, JsonStathamException
  {
    for (final Field field : sourceClass.getDeclaredFields())
    {
      if (!field.isAnnotationPresent(JsonField.class))
      {
        /* not JsonField so check next one. */
        continue;
      }
      field.setAccessible(true);

      /* get field name from the @JsonField annotation */
      String jsonFieldName = field.getAnnotation(JsonField.class)
          .name();

      if (isEmpty(jsonFieldName))
      {
        /*
         * no field name is set in the @JsonField annotation so use the actual field name for the Json field.
         */
        jsonFieldName = field.getName();
      }

      if (fieldNameSet.contains(jsonFieldName))
      {
        /* [ERROR] duplicate field names found */
        /* @formatter:off */
        throw new JsonStathamException(
            format(
                "Json filed name must be unique. [JsonField name: %s] in [field: %s] is already used in another field.\n"
                    + "[input] Object source: %s, " +
                  		"Class<?> sourceClass: %s, " +
                  		"Set<String> fieldNameSet: %s, " +
                  		"JsonObject jsonObjectConvertible: %s",
                jsonFieldName, field, 
                      source,
                      sourceClass,
                      fieldNameSet,
                      jsonObject));
        /* @formatter:on */
      }
      fieldNameSet.add(jsonFieldName);

      Object fieldValue = null;

      if (field.isAnnotationPresent(ValueAccessor.class))
      {
        String valueAccessorName = field.getAnnotation(ValueAccessor.class)
            .name();

        final boolean hasNoAccessorName = isEmpty(valueAccessorName);
        boolean isFieldBoolean = false;
        if (hasNoAccessorName)
        {
          /*
           * no explicit ValueAccessor name is set so use the getter name that is get + the field name (e.g. field name:
           * name => getName / field name: id => getId). If the field type is boolean or Boolean, it is is + the field
           * name (e.g. field: boolean assigned => isAssigned).
           */
          final Class<?> fieldType = field.getType();
          final String fieldName = field.getName();
          isFieldBoolean = boolean.class.equals(fieldType) || Boolean.class.equals(fieldType);
          valueAccessorName =
            (isFieldBoolean ? "is" : "get") + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        }

        try
        {
          final Method method = sourceClass.getDeclaredMethod(valueAccessorName, EMPTY_CLASS_ARRAY);
          method.setAccessible(true);
          fieldValue = method.invoke(source, EMPTY_OBJECT_ARRAY);
        }
        catch (final SecurityException e)
        {
          throw new JsonStathamException(e);
        }
        catch (final NoSuchMethodException e)
        {
          if (hasNoAccessorName && isFieldBoolean)
          {
            final String anotherValueAccessorName = "get" + valueAccessorName.substring(2);
            try
            {
              final Method method = sourceClass.getDeclaredMethod(anotherValueAccessorName, EMPTY_CLASS_ARRAY);
              method.setAccessible(true);
              fieldValue = method.invoke(source, EMPTY_OBJECT_ARRAY);
            }
            catch (final Exception e1)
            {
              /* throw the original exception. */
              throw new JsonStathamException(format(
                  "The given ValueAccessor method [%s] for the JsonField [jsonFieldName: %s][fieldName: %s] is not found in [class: %s]."
                      + " An additional attempt with the method name, [%s], failed as well with the exception [%s].",
                  valueAccessorName, jsonFieldName, field.getName(), sourceClass.getName(), anotherValueAccessorName,
                  e1), e);
            }
          }
          else
          {
            throw new JsonStathamException(
                format(
                    "The given ValueAccessor method [%s] for the JsonField [jsonFieldName: %s][fieldName: %s] is not found in [class: %s].",
                    valueAccessorName, jsonFieldName, field.getName(), sourceClass.getName()), e);
          }
        }
        catch (final InvocationTargetException e)
        {
          throw new JsonStathamException(
              format(
                  "Value accessor invocation failed.\n"
                      + "It might be caused by any error happened in the given value accessor method or "
                      + "The given ValueAccessor method [%s] is not a proper value accessor for the JsonField [jsonFieldName: %s][fieldName: %s] in [class: %s].",
                  valueAccessorName, jsonFieldName, field.getName(), sourceClass.getName()), e);
        }
      }
      else
      {
        fieldValue = field.get(source);
      }
      jsonObject.put(jsonFieldName, createJsonValue(fieldValue));
    }
  }

  /**
   * Converts the given value into {@link JsonConvertible} object. It can be either {@link JsonObject} or
   * {@link JsonArray}.
   * 
   * @param value
   *          the given target object to be converted to {@link JsonConvertible} which is either
   *          {@link JsonObject} or {@link JsonArray}.
   * @return {@link JsonConvertible} converted from the given value object. It can be either
   *         {@link JsonObject} or {@link JsonArray}. If the given value is the null reference, it
   *         returns the object created by {@link #nullJsonObjectConvertible()}.
   * @throws JsonStathamException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  public Object createJsonValue(final Object value) throws IllegalArgumentException, IllegalAccessException,
      JsonStathamException
  {
    if (null == value)
    {
      return nullJsonObjectConvertible();
    }

    final Class<?> type = value.getClass();

    for (final KnownTypeProcessorDeciderForJavaToJson knowTypeProcessorDecider : knownTypeProcessorDeciderForJavaToJsons)
    {
      final KnownTypeProcessorWithReflectionJavaToJsonConverter knownTypeProcessorWithReflectionJavaToJsonConverter =
        knowTypeProcessorDecider.decide(type);
      if (null != knownTypeProcessorWithReflectionJavaToJsonConverter)
      {
        return knownTypeProcessorWithReflectionJavaToJsonConverter.process(this, type, value);
      }
    }
    return createJsonObject(value);
  }

  @Override
  public String convertIntoJson(final Object source) throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    if (null == source)
    {
      return nullJsonObjectConvertible().toString();
    }

    final Class<?> sourceType = source.getClass();
    final KnownTypeProcessorWithReflectionJavaToJsonConverter knownTypeProcessorWithReflectionJavaToJsonConverter =
      knownDataStructureTypeProcessorDecider.decide(sourceType);
    if (null != knownTypeProcessorWithReflectionJavaToJsonConverter)
    {
      return knownTypeProcessorWithReflectionJavaToJsonConverter.process(this, sourceType, source)
          .toString();
    }

    return createJsonObject(source).toString();
  }

}
