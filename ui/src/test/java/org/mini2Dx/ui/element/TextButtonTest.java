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
import org.mini2Dx.ui.layout.HorizontalAlignment;

/**
 * Unit and integration tests for {@link TextButton}
 */
public class TextButtonTest {
	@Before
	public void setUp() {
		Mdx.graphics = new LibgdxGraphicsUtils();
		Mdx.reflect = new JvmReflection();
	}

	@Test
	public void testSerialization() {
		TextButton textButton = new TextButton("textButton-54");
		textButton.setText("Button text");
		textButton.setTextAlignment(HorizontalAlignment.RIGHT);
		
		try {
			String xml = Mdx.xml.toXml(textButton);
			System.out.println(xml);
			TextButton result = Mdx.xml.fromXml(xml, TextButton.class);
			Assert.assertEquals(textButton.getId(), result.getId());
			Assert.assertEquals(textButton.getText(), result.getText());
			Assert.assertEquals(textButton.getTextAlignment(), result.getTextAlignment());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
