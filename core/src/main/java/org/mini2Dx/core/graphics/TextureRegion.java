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
package org.mini2Dx.core.graphics;

/**
 *
 */
public interface TextureRegion {

	public float getU();

	public void setU(float u);

	public float getV();

	public void setV(float v);

	public float getU2();

	public void setU2(float u2);

	public float getV2();

	public void setV2(float v2);

	public int getRegionX();

	public void setRegionX(int x);

	public int getRegionY();

	public void setRegionY(int y);

	public int getRegionWidth();

	public void setRegionWidth(int width);

	public int getRegionHeight();

	public void setRegionHeight(int height);
	
	public void setRegion(int x, int y, int width, int height);

	public boolean isFlipX();

	public boolean isFlipY();
	
	public void flip(boolean x, boolean y);
	
	public void setFlip(boolean flipX, boolean flipY);

	public Texture getBackingTexture();

	/**
	 * Creates a {@link TextureRegion} from this {@link TextureRegion}
	 * 
	 * @return
	 */
	public TextureRegion createTextureRegion();

	/**
	 * Creates a {@link TextureRegion} from this {@link TextureRegion}
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public TextureRegion createTextureRegion(int width, int height);

	/**
	 * Creates a {@link TextureRegion} from this {@link TextureRegion}
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public TextureRegion createTextureRegion(int x, int y, int width, int height);

	/**
	 * Creates a {@link TextureRegion} from this {@link TextureRegion}
	 * 
	 * @param u
	 * @param v
	 * @param u2
	 * @param v2
	 * @return
	 */
	public TextureRegion createTextureRegion(float u, float v, float u2, float v2);
}
