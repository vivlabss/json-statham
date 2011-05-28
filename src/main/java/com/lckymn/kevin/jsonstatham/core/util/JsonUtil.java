/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.util;

import static org.elixirian.common.util.MessageFormatter.*;
import static org.elixirian.common.util.Objects.*;

import java.util.Collection;
import java.util.Map;

import com.lckymn.kevin.jsonstatham.core.convertible.JsonArray;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonConvertible;
import com.lckymn.kevin.jsonstatham.core.convertible.JsonObjectImpl;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

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
 * @version 0.0.1 (2010-12-25)
 */
public final class JsonUtil
{
  private JsonUtil()
  {
  }

  public static String doubleQuote(final String value)
  {
    final int length = value.length();
    char c = 0;
    final StringBuilder quotedStringBuilder = new StringBuilder("\"");
    for (int i = 0; i < length; i++)
    {
      final char previous = c;
      c = value.charAt(i);
      switch (c)
      {
        case '"':
        case '\\':
          quotedStringBuilder.append('\\')
              .append(c);
          break;
        case '/':
          if ('<' == previous)
            quotedStringBuilder.append('\\');
          quotedStringBuilder.append(c);
          break;
        case '\b':
          quotedStringBuilder.append("\\b");
          break;
        case '\f':
          quotedStringBuilder.append("\\f");
          break;
        case '\n':
          quotedStringBuilder.append("\\n");
          break;
        case '\r':
          quotedStringBuilder.append("\\r");
          break;
        case '\t':
          quotedStringBuilder.append("\\t");
          break;
        default:
          if (' ' > c || (('\u0080' <= c & '\u00a0' > c) | ('\u2000' <= c & '\u2100' > c)))
          {
            final String hex = Integer.toHexString(c);
            quotedStringBuilder.append("\\u")
                .append(hex.substring(hex.length() - 4));
          }
          else
          {
            quotedStringBuilder.append(c);
          }
          break;
      }
    }
    return quotedStringBuilder.append('"')
        .toString();
  }

  public static void validate(final Object value)
  {
    final Double doubleValue = castIfInstanceOf(Double.class, value);
    if (null != doubleValue)
    {
      if (doubleValue.isInfinite() | doubleValue.isNaN())
      {
        throw new JsonStathamException(format("JSON allow neither infinite numbers nor NaN. [value: %s]", doubleValue));
      }
    }
    final Float floatValue = castIfInstanceOf(Float.class, value);
    if (null != floatValue)
    {
      if (floatValue.isInfinite() | floatValue.isNaN())
      {
        throw new JsonStathamException(format("JSON allow neither infinite numbers nor NaN. [value: %s]", floatValue));
      }
    }
  }

  public static String toStringValue(final Number value)
  {
    validate(value);
    String numberString = value.toString();

    if (0 < numberString.indexOf('.') && (0 > numberString.indexOf('e') & 0 > numberString.indexOf('E')))
    {
      /* remove 0s from the end. */
      int theEnd = numberString.length() - 1;
      while ('0' == numberString.charAt(theEnd))
        numberString = numberString.substring(0, theEnd--);

      if ('.' == numberString.charAt(theEnd))
        numberString = numberString.substring(0, theEnd);
    }
    return numberString;
  }

  public static String toStringValue(final Object value)
  {
    if (null == value)
      return JsonObjectImpl.NULL_JSON_OBJECT.toString();

    if (value instanceof JsonConvertible | value instanceof Boolean)
      return value.toString();

    if (value instanceof Number)
      return toStringValue((Number) value);

    if (value instanceof Map)
    {
      @SuppressWarnings("unchecked")
      final Map<String, Object> map = (Map<String, Object>) value;
      return JsonObjectImpl.newJsonObject(map)
          .toString();
    }

    if (value instanceof Collection)
      return JsonArray.newJsonArray((Collection<?>) value)
          .toString();

    return doubleQuote(value.toString());
  }
}
