/*******************************************************************************
 * Copyright 2019 See AUTHORS file
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.mini2Dx.core.geom;

import org.mini2Dx.core.Geometry;

/**
 * A <a href="https://en.wikipedia.org/wiki/Hexagon">hexagon</a> where all
 * interior angles are 120 degrees.
 */
public class RegularHexagon extends RegularPolygon {
    public static final float ROTATION_SYMMETRY = 60f;
    private static final int TOTAL_SIDES = 6;

    /**
     * Constructor
     * @param centerX The center X coordinate
     * @param centerY The center Y coordinate
     * @param radius The distance from the center to the corner points
     */
    public RegularHexagon(float centerX, float centerY, float radius) {
        super(centerX, centerY, radius, TOTAL_SIDES, ROTATION_SYMMETRY);
    }

    /**
     * Constructs a {@link RegularHexagon} belonging to the {@link Geometry} pool
     * @param geometry the {@link Geometry} pool
     */
    public RegularHexagon(Geometry geometry) {
        super(geometry, TOTAL_SIDES, ROTATION_SYMMETRY);
    }

    @Override
    public void dispose() {
        clearPositionChangeListeners();
        clearSizeChangeListeners();

        if(geometry == null) {
            return;
        }
        geometry.release(this);
    }
}
