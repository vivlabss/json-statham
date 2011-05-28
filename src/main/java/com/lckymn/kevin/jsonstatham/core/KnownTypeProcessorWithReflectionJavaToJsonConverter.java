/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core;

import com.lckymn.kevin.jsonstatham.core.reflect.java2json.ReflectionJavaToJsonConverter;
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
 * @version 0.0.1 (2010-06-10)
 */
public interface KnownTypeProcessorWithReflectionJavaToJsonConverter extends
		KnownTypeProcessor<ReflectionJavaToJsonConverter>
{
	@Override
	<T> Object process(ReflectionJavaToJsonConverter reflectionJavaToJsonConverter, Class<T> valueType, Object value)
			throws IllegalArgumentException, IllegalAccessException, JsonStathamException;
}