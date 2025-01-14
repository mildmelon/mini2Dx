/*******************************************************************************
 * Copyright 2019 See AUTHORS file
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.mini2Dx.core.serialization.map.deserialize;

import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.exception.ReflectionException;
import org.mini2Dx.core.reflect.Field;
import org.mini2Dx.core.reflect.Method;

/**
 * Utility class used during JSON/XML deserialization
 */
public class GdxDeserializedMap<T> extends DeserializedMap<T> {
	private static final String LOGGING_TAG = GdxDeserializedMap.class.getSimpleName();

	private final Class<T> fallbackClass;
	private final Class<?> keyClass, valueClass;

	private Method putMethod;

	public GdxDeserializedMap(Field field, Class<T> fieldClass, Class<?> keyClass, Class<?> valueClass, Object object) throws ReflectionException {
		super(field, fieldClass, object);
		this.fallbackClass = fieldClass;
		this.keyClass = keyClass;
		this.valueClass = valueClass;

		for(Method method : Mdx.reflect.getMethods(fieldClass)) {
			if(method.getName().equals("put")) {
				putMethod = method;
				break;
			}
		}
	}

	@Override
	public Class<? extends T> getFallbackImplementation() {
		return fallbackClass;
	}

	@Override
	public Class<?> getKeyClass() {
		return keyClass;
	}

	@Override
	public Class<?> getValueClass() {
		return valueClass;
	}

	@Override
	public void put(Object key, Object value) {
		try {
			putMethod.invoke(map, key, value);
		} catch (ReflectionException e) {
			Mdx.log.error(LOGGING_TAG, e.getMessage(), e);
		}
	}
}
