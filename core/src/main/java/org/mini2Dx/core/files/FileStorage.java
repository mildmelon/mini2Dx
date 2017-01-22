/**
 * Copyright (c) 2017 See AUTHORS file
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the mini2Dx nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mini2Dx.core.files;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.mini2Dx.core.serialization.annotation.Field;

/**
 * Common interface for reading/writing data from a storage location
 * 
 * Note: Objects must use {@link Field} annotations to be
 * serialized/deserialized properly
 */
public interface FileStorage {
	/**
	 * Reads the contents of a file in the storage into a {@link String}
	 * 
	 * @param filepath
	 *            The path to the file. This will be resolved as a path within
	 *            the location.
	 * @return A string containing the contents of the file
	 * @throws FileStorageException
	 *             Thrown if the file does not exist
	 */
	public String readString(String... filepath) throws FileStorageException;

	/**
	 * Writes a {@link String} to a file in the storage
	 * 
	 * @param content
	 *            The {@link String} to be written to the file
	 * @param filepath
	 *            The path to the file. This will be resolved as a path within
	 *            the storage.
	 * @throws FileStorageException
	 *             Thrown if the location cannot be accessed or the data cannot
	 *             be written to the file.
	 */
	public void writeString(String content, String... filepath) throws FileStorageException;

	/**
	 * Converts XML from a file into an object. Note the object must use the
	 * mini2Dx data annotations.
	 * 
	 * @param clazz
	 *            The object type to convert the XML into
	 * @param filepath
	 *            The path to the XML file. This will be resolved as a path
	 *            within the storage.
	 * @return The resulting object
	 * @throws FileStorageException
	 *             Thrown if the XML is invalid, the file does not exist or the
	 *             storage cannot be accessed.
	 */
	public <T> T readXml(Class<T> clazz, String... filepath) throws FileStorageException;

	/**
	 * Writes an object as XML to a file. Note the object must use the mini2Dx
	 * data annotations.
	 * 
	 * @param object
	 *            The object to be written to the file
	 * @param filepath
	 *            The path to the XML file. This will be resolved as a path
	 *            within the storage.
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed or the data cannot
	 *             be written to the file.
	 */
	public <T> void writeXml(T object, String... filepath) throws FileStorageException;

	/**
	 * Converts JSON from a file into an object. Note the object must use the
	 * mini2Dx data annotations.
	 * 
	 * @param clazz
	 *            The object type to convert the JSON into
	 * @param filepath
	 *            The path to the JSON file. This will be resolved as a path
	 *            within the storage.
	 * @return The resulting object
	 * @throws FileStorageException
	 *             Thrown if the XML is invalid, the file does not exist or the
	 *             storage cannot be accessed.
	 */
	public <T> T readJson(Class<T> clazz, String... filepath) throws FileStorageException;

	/**
	 * Writes an object as JSON to a file. Note the object must use the mini2Dx
	 * data annotations.
	 * 
	 * @param object
	 *            The object to be written to the file
	 * @param filepath
	 *            The path to the JSON file. This will be resolved as a path
	 *            within the storage
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed or the data cannot
	 *             be written to the file.
	 */
	public <T> void writeJson(T object, String... filepath) throws FileStorageException;

	/**
	 * Open an {@link InputStream} to a file in the storage
	 * 
	 * @param filepath
	 *            The path to the file. This will be resolved as a path within
	 *            the storage.
	 * @return An {@link InputStream} instance
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed or the file cannot
	 *             be read
	 */
	public InputStream read(String... filepath) throws FileStorageException;

	/**
	 * Opens an {@link OutputStream} to a file in the storage
	 * 
	 * @param filepath
	 *            The path to the file. This will be resolved as a path within
	 *            the storage.
	 * @return An {@link OutputStream} instance
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed or the file cannot
	 *             be written to
	 */
	public OutputStream write(String... filepath) throws FileStorageException;

	/**
	 * Checks if the file exists in the storage
	 * 
	 * @param filepath
	 *            The path to the file within the storage
	 * @return True if the file exists
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed
	 */
	public boolean hasFile(String... filepath) throws FileStorageException;

	/**
	 * Checks if the directory exists in the storage
	 * 
	 * @param path
	 *            The path to the directory within the storage
	 * @return True if the file exists
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed
	 */
	public boolean hasDirectory(String... path) throws FileStorageException;

	/**
	 * Creates a directory within in the storage
	 * 
	 * @param path
	 *            The path to the directory within the storage
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed or the directory
	 *             could not be created
	 */
	public void createDirectory(String... path) throws FileStorageException;

	/**
	 * Deletes a file or directory within in the storage
	 * 
	 * @param path
	 *            The path to the file or directory within the storage
	 * @throws FileStorageException
	 *             Thrown if the storage cannot be accessed or the
	 *             file/directory could not be deleted
	 * @return True if the file or directory was deleted successfully
	 */
	public boolean delete(String... path) throws FileStorageException;
}
