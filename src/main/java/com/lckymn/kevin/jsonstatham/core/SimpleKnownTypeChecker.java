package com.lckymn.kevin.jsonstatham.core;

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
 * @version 0.0.1 (2010-06-10)
 * @version 0.0.2 (2010-09-16) moved from
 *          {@link com.lckymn.kevin.jsonstatham.core.reflect.java2json.OneProcessorForKnownTypeDecider}.
 */
public interface SimpleKnownTypeChecker
{
  boolean isKnown(Class<?> type);
}