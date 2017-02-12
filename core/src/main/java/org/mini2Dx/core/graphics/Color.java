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
package org.mini2Dx.core.graphics;

import org.mini2Dx.core.exception.MdxException;
import org.mini2Dx.gdx.utils.NumberUtils;

/**
 *
 */
public class Color {
	public float r, g, b, a;

	public Color() {
	}

	public Color(float r, float g, float b) {
		this(r, g, b, 1f);
	}

	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		clamp();
	}

	public Color(int rgba8888) {
		rgba8888(rgba8888);
	}

	public Color(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}

	private Color clamp() {
		if (r < 0f) {
			r = 0f;
		} else if (r > 1f) {
			r = 1f;
		}

		if (g < 0f) {
			g = 0f;
		} else if (g > 1f) {
			g = 1f;
		}

		if (b < 0f) {
			b = 0f;
		} else if (b > 1f) {
			b = 1f;
		}

		if (a < 0f) {
			a = 0f;
		} else if (a > 1f) {
			a = 1f;
		}
		return this;
	}

	public Color premultiplyAlpha() {
		r *= a;
		g *= a;
		b *= a;
		return this;
	}

	public Color add(Color color) {
		return add(color.r, color.g, color.b, color.a);
	}

	public Color add(float r, float g, float b, float a) {
		this.r += r;
		this.g += g;
		this.b += b;
		this.a += a;
		return clamp();
	}

	public Color subtract(Color color) {
		return subtract(color.r, color.g, color.b, color.a);
	}
	
	public Color subtract (float r, float g, float b, float a) {
		this.r -= r;
		this.g -= g;
		this.b -= b;
		this.a -= a;
		return clamp();
	}

	public Color multiply(Color color) {
		return multiply(color.r, color.g, color.b, color.a);
	}
	
	public Color multiply(float value) {
		return multiply(value, value, value, value);
	}
	
	public Color multiply(float r, float g, float b, float a) {
		this.r *= r;
		this.g *= g;
		this.b *= b;
		this.a *= a;
		return clamp();
	}

	public Color set(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
		return this;
	}

	public Color set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		return clamp();
	}

	public Color set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return clamp();
	}

	public Color rgb(float r, float g, float b) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		return clamp();
	}

	public Color rgba(float r, float g, float b, float a) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a;
		return clamp();
	}

	public Color rgb565(int value) {
		r = ((value & 0x0000F800) >>> 11) / 31f;
		g = ((value & 0x000007E0) >>> 5) / 63f;
		b = ((value & 0x0000001F) >>> 0) / 31f;
		return this;
	}

	public Color rgba4444(int value) {
		r = ((value & 0x0000f000) >>> 12) / 15f;
		g = ((value & 0x00000f00) >>> 8) / 15f;
		b = ((value & 0x000000f0) >>> 4) / 15f;
		a = ((value & 0x0000000f)) / 15f;
		return this;
	}

	public Color rgb888(int value) {
		r = ((value & 0x00ff0000) >>> 16) / 255f;
		g = ((value & 0x0000ff00) >>> 8) / 255f;
		b = ((value & 0x000000ff)) / 255f;
		return this;
	}

	public Color rgba8888(int value) {
		r = ((value & 0xff000000) >>> 24) / 255f;
		g = ((value & 0x00ff0000) >>> 16) / 255f;
		b = ((value & 0x0000ff00) >>> 8) / 255f;
		a = ((value & 0x000000ff)) / 255f;
		return this;
	}

	public Color argb8888(int value) {
		a = ((value & 0xff000000) >>> 24) / 255f;
		r = ((value & 0x00ff0000) >>> 16) / 255f;
		g = ((value & 0x0000ff00) >>> 8) / 255f;
		b = ((value & 0x000000ff)) / 255f;
		return this;
	}

	public Color abgr8888(float value) {
		int c = NumberUtils.floatToIntColor(value);

		a = ((c & 0xff000000) >>> 24) / 255f;
		b = ((c & 0x00ff0000) >>> 16) / 255f;
		g = ((c & 0x0000ff00) >>> 8) / 255f;
		r = ((c & 0x000000ff)) / 255f;
		return this;
	}

	public Color copy() {
		return new Color(this);
	}

	public float toFloatBits() {
		int color = ((int) (255 * a) << 24) | ((int) (255 * b) << 16) | ((int) (255 * g) << 8) | ((int) (255 * r));
		return NumberUtils.intToFloatColor(color);
	}

	public int toIntBits() {
		int color = ((int) (255 * a) << 24) | ((int) (255 * b) << 16) | ((int) (255 * g) << 8) | ((int) (255 * r));
		return color;
	}

	public static int rgb565(float r, float g, float b) {
		return ((int) (r * 31) << 11) | ((int) (g * 63) << 5) | (int) (b * 31);
	}

	public static int rgba4444(float r, float g, float b, float a) {
		return ((int) (r * 15) << 12) | ((int) (g * 15) << 8) | ((int) (b * 15) << 4) | (int) (a * 15);
	}

	public static int rgb888(float r, float g, float b) {
		return ((int) (r * 255) << 16) | ((int) (g * 255) << 8) | (int) (b * 255);
	}

	public static int rgba8888(float r, float g, float b, float a) {
		return ((int) (r * 255) << 24) | ((int) (g * 255) << 16) | ((int) (b * 255) << 8) | (int) (a * 255);
	}

	public static int argb8888(float a, float r, float g, float b) {
		return ((int) (a * 255) << 24) | ((int) (r * 255) << 16) | ((int) (g * 255) << 8) | (int) (b * 255);
	}

	public static Color rgbToColor(float r, float g, float b) {
		return new Color().rgba(r, g, b, 1f);
	}

	public static Color rgbaToColor(float r, float g, float b, float a) {
		return new Color().rgba(r, g, b, a);
	}

	public static Color rgb565ToColor(int value) {
		return new Color().rgb565(value);
	}

	public static Color rgba4444ToColor(int value) {
		return new Color().rgba4444(value);
	}

	public static Color rgb888ToColor(int value) {
		return new Color().rgb888(value);
	}

	public static Color rgba8888ToColor(int value) {
		return new Color().rgba8888(value);
	}

	public static Color argb8888ToColor(int value) {
		return new Color().argb8888(value);
	}

	public static Color abgr8888ToColor(float value) {
		return new Color().abgr8888(value);
	}

	/**
	 * Converts a RGB value to an instance of {@link Color}
	 * 
	 * @param value
	 *            A comma-separated RGB value - e.g. 255,255,255
	 * @return The {@link Color} instance corresponding to the value
	 */
	public static Color rgbToColor(String value) {
		String[] values = value.split(",");
		if (values.length != 3) {
			throw new MdxException("Invalid RGB value: " + value);
		}
		return Color.rgbaToColor(Float.parseFloat(values[0]), Float.parseFloat(values[1]),
				Float.parseFloat(values[2]) / 255f, 1f);
	}

	/**
	 * Converts a RGBA value to an instance of {@link Color}
	 * 
	 * @param value
	 *            A comma-separated RGBA value - e.g. 255,255,255,0.3
	 * @return The {@link Color} instance corresponding to the value
	 */
	public static Color rgbaToColor(String value) {
		String[] values = value.split(",");
		if (values.length != 4) {
			throw new MdxException("Invalid RGBA value: " + value);
		}
		return Color.rgbaToColor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]),
				Float.parseFloat(values[3]));
	}
}
