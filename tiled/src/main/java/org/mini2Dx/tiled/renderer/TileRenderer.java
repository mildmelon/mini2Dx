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
package org.mini2Dx.tiled.renderer;

import org.mini2Dx.core.Graphics;
import org.mini2Dx.core.graphics.Sprite;
import org.mini2Dx.gdx.utils.Disposable;
import org.mini2Dx.tiled.Tile;

/**
 * Common interface for tile renderers
 */
public interface TileRenderer extends Disposable {

	/**
	 * Updates the {@link Tile} frame
	 * 
	 * @param delta
	 *            The time since the last update in seconds
	 */
	public void update(float delta);

	/**
	 * Renders the {@link Tile} at the given coordinates
	 * 
	 * @param g
	 *            The {@link Graphics} context to use
	 * @param renderX
	 *            The x coordinate to render at
	 * @param renderY
	 *            The y coordinate to render at
	 */
	public void draw(Graphics g, int renderX, int renderY);

	/**
	 * Renders the {@link Tile} at the given coordinates
	 * 
	 * @param g
	 *            The {@link Graphics} context to use
	 * @param renderX
	 *            The x coordinate to render at
	 * @param renderY
	 *            The y coordinate to render at
	 * @param flipH
	 *            True if the tile is flipped horizontally
	 * @param flipV
	 *            True if the tile is flipped vertically
	 * @param flipD
	 *            True if the tile is flipped (anti) diagonally - rotation
	 */
	public void draw(Graphics g, int renderX, int renderY, boolean flipH, boolean flipV, boolean flipD);

	/**
	 * Returns the current {@link Tile} image
	 * 
	 * @return The current image
	 */
	public Sprite getCurrentTileImage();
}
