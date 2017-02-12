/**
 * Copyright 2017 Thomas Cashman
 */
package org.mini2Dx.core.graphics;

/**
 *
 */
public interface SpriteBatch<T extends Texture, TR extends TextureRegion> {

	public void begin();

	public void end();

	public Color getTint();

	public void setTint();

	public void draw(T texture, float x, float y);

	public void draw(T texture, float x, float y, float width, float height);

	public void draw(T texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight);

	public void draw(T texture, float x, float y, float width, float height, float u, float v, float u2, float v2);

	public void draw(T texture, float x, float y, float originX, float originY, float width, float height, float scaleX,
			float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX,
			boolean flipY);

	public void draw(T texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth,
			int srcHeight, boolean flipX, boolean flipY);

	public void draw(Texture texture, float[] spriteVertices, int offset, int count);
}
