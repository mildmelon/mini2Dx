/**
 * Copyright (c) 2016 See AUTHORS file
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the mini2Dx nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mini2Dx.ui.element;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.reflect.jvm.JvmReflection;
import org.mini2Dx.libgdx.LibgdxGraphicsUtils;

/**
 * Unit and integration tests for {@link Image}
 */
public class ImageTest {
	@Before
	public void setUp() {
		Mdx.graphics = new LibgdxGraphicsUtils();
		Mdx.reflect = new JvmReflection();
	}

	@Test
	public void testSerialization() {
		Image image = new Image("image-34");
		image.setResponsive(true);
		image.setTexturePath("/path/texture.png");
		
		try {
			String xml = Mdx.xml.toXml(image);
			System.out.println(xml);
			Image result = Mdx.xml.fromXml(xml, Image.class);
			Assert.assertEquals(image.getId(), result.getId());
			Assert.assertEquals(image.isResponsive(), result.isResponsive());
			Assert.assertEquals(image.getTexturePath(), result.getTexturePath());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
