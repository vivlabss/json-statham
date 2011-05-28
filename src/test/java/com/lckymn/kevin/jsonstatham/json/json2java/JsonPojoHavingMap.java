package com.lckymn.kevin.jsonstatham.json.json2java;

import static org.elixirian.common.util.Conditional.*;
import static org.elixirian.common.util.Objects.*;

import java.util.Map;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

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
 * @version 0.0.1 (2010-10-07)
 */
@JsonObject
public class JsonPojoHavingMap
{
  @JsonField
  private final String name;

  @JsonField
  private final Map<String, Long> stringToLongMap;

  public JsonPojoHavingMap(String name, Map<String, Long> stringToLongMap)
  {
    this.name = name;
    this.stringToLongMap = stringToLongMap;
  }

  public String getName()
  {
    return name;
  }

  public Map<String, Long> getStringToLongMap()
  {
    return stringToLongMap;
  }

  @Override
  public int hashCode()
  {
    return hash(name, stringToLongMap);
  }

  @Override
  public boolean equals(Object jsonPojoHavingMap)
  {
    if (identical(this, jsonPojoHavingMap))
    {
      return true;
    }
    final JsonPojoHavingMap that = castIfInstanceOf(JsonPojoHavingMap.class, jsonPojoHavingMap);
    return isNotNull(that) && and(equal(this.name, that.name), equal(this.stringToLongMap, that.stringToLongMap));
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("name", name)
        .add("stringToLongMap", stringToLongMap)
        .toString();
  }
}
