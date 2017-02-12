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

import org.mini2Dx.core.geom.Rectangle;

public interface Sprite extends TextureRegion {

	public void drawTo(SpriteBatch<?, ?> spriteBatch);

	public void drawTo(SpriteBatch<?, ?> spriteBatch, float alphaModulation);

	public float getX();
	
	public void setX(float x);

	public float getY();
	
	public void setY(float y);
	
	public void setPosition(float x, float y);

	public float getWidth();

	public float getHeight();
	
	public void setSize(float width, float height);

	public Rectangle getBounds();
	
	public void setBounds(float x, float y, float width, float height);
	
	public float[] getVertices();
	
	public float getAlpha();
	
	public void setAlpha(float a);

	public float getOriginX();

	public float getOriginY();
	
	public void setOrigin(float originX, float originY);

	public void setOriginCenter();

	public float getScaleX();

	public float getScaleY();
	
	public void setScale(float scaleXY);

	public void setScale(float scaleX, float scaleY);

	public void scale(float amount);
	
	public float getRotation();
	
	public void setRotation(float degrees);

	public void rotate(float degrees);

	public void rotate90(boolean clockwise);

	public Color getTint();
	
	public void setTint(Color tint);

	public void setTint(float r, float g, float b, float a);

	public void setTint(float color);

	public void scroll(float xAmount, float yAmount);
	
	public void centerXOn(float x);

	public void centerYOn(float y);

	public void centerOn(float x, float y);

	public void translateX(float xAmount);

	public void translateY(float yAmount);

	public void translate(float xAmount, float yAmount);
	
	public void set(Sprite sprite);
}
