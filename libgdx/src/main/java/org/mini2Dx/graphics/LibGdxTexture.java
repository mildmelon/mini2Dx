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
package org.mini2Dx.graphics;

import org.mini2Dx.core.graphics.Texture;
import org.mini2Dx.core.graphics.TextureRegion;

/**
 *
 */
public class LibGdxTexture implements Texture {
	private com.badlogic.gdx.graphics.Texture texture;

	@Override
	public int getWidth() {
		return texture.getWidth();
	}

	@Override
	public int getHeight() {
		return texture.getHeight();
	}

	@Override
	public int getDepth() {
		return texture.getDepth();
	}
	
	@Override
	public TextureRegion createTextureRegion() {
		return new LibGdxTextureRegion(this);
	}

	@Override
	public TextureRegion createTextureRegion(int width, int height) {
		return new LibGdxTextureRegion(this, width, height);
	}

	@Override
	public TextureRegion createTextureRegion(int x, int y, int width, int height) {
		return new LibGdxTextureRegion(this, x, y, width, height);
	}

	@Override
	public TextureRegion createTextureRegion(float u, float v, float u2, float v2) {
		return new LibGdxTextureRegion(this, u, v, u2, v2);
	}
	
	public com.badlogic.gdx.graphics.Texture getBackingTexture() {
		return texture;
	}
}
