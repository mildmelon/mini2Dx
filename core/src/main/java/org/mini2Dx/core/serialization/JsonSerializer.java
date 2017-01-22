/**
 * Copyright (c) 2015 See AUTHORS file
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the mini2Dx nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mini2Dx.core.serialization;

import java.io.Reader;
import java.io.Writer;

/**
 * Serializes objects to/from JSON based on
 * {@link org.mini2Dx.core.serialization.annotation.Field} annotations
 */
@SuppressWarnings("unchecked")
public interface JsonSerializer {
	/**
	 * Reads a JSON document and converts it into an object of the specified
	 * type
	 * 
	 * @param reader
	 *            The {@link Reader} reading the JSON document
	 * @param clazz
	 *            The {@link Class} to convert the document to
	 * @return The object deserialized from JSON
	 * @throws SerializationException
	 *             Thrown when the data is invalid
	 */
	public <T> T fromJson(Reader reader, Class<T> clazz) throws SerializationException;
	
	/**
	 * Reads a JSON document and converts it into an object of the specified
	 * type
	 * 
	 * @param json
	 *            The JSON document
	 * @param clazz
	 *            The {@link Class} to convert the document to
	 * @return The object deserialized from JSON
	 * @throws SerializationException
	 *             Thrown when the data is invalid
	 */
	public <T> T fromJson(String json, Class<T> clazz) throws SerializationException;

	/**
	 * Writes a JSON document by searching the object for
	 * {@link org.mini2Dx.core.serialization.annotation.Field} annotations
	 * 
	 * @param writer
	 *            The {@link Writer} to write to
	 * @param object
	 *            The object to convert to JSON
	 * @throws SerializationException
	 *             Thrown when the object is invalid
	 */
	public <T> void toJson(Writer writer, T object) throws SerializationException;

	/**
	 * Writes a JSON document by searching the object for
	 * {@link org.mini2Dx.core.serialization.annotation.Field} annotations
	 * 
	 * @param writer
	 *            The {@link Writer} to write to
	 * @param object
	 *            The object to convert to JSON
	 * @param prettyPrint
	 *            Set to true if the JSON should be prettified
	 * @throws SerializationException
	 *             Thrown when the object is invalid
	 */
	public <T> void toJson(Writer writer, T object, boolean prettyPrint) throws SerializationException;

	/**
	 * Writes a JSON document by searching the object for
	 * {@link org.mini2Dx.core.serialization.annotation.Field} annotations
	 * 
	 * @param object
	 *            The object to convert to JSON
	 * @return The object serialized as JSON
	 * @throws SerializationException
	 *             Thrown when the object is invalid
	 */
	public <T> String toJson(T object) throws SerializationException;

	/**
	 * Writes a JSON document by searching the object for
	 * {@link org.mini2Dx.core.serialization.annotation.Field} annotations
	 * 
	 * @param object
	 *            The object to convert to JSON
	 * @param prettyPrint
	 *            Set to true if the JSON should be prettified
	 * @return The object serialized as JSON
	 * @throws SerializationException
	 *             Thrown when the object is invalid
	 */
	public <T> String toJson(T object, boolean prettyPrint) throws SerializationException;
}
