/*******************************************************************************
 * Copyright 2019 Viridian Software Limited
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
package org.mini2Dx.core.util;

import org.mini2Dx.gdx.utils.OrderedSet;

/**
 * Tracks {@link Interpolatable} objects and auto-interpolates them each frame
 */
public class InterpolationTracker {
	private static final OrderedSet<Interpolatable> INTERPOLATABLES = new OrderedSet<Interpolatable>(512);

	public static void preUpdate() {
		for(Interpolatable interpolatable : INTERPOLATABLES.orderedItems()) {
			interpolatable.preUpdate();
		}
	}

	public static void interpolate(float alpha) {
		for(Interpolatable interpolatable : INTERPOLATABLES.orderedItems()) {
			interpolatable.interpolate(alpha);
		}
	}

	public static synchronized boolean isRegistered(Interpolatable interpolatable) {
		return INTERPOLATABLES.contains(interpolatable);
	}

	public static synchronized void register(Interpolatable interpolatable) {
		INTERPOLATABLES.add(interpolatable);
	}

	public static synchronized void deregister(Interpolatable interpolatable) {
		INTERPOLATABLES.remove(interpolatable);
	}

	public static synchronized void deregisterAll() {
		INTERPOLATABLES.clear();
	}
}
