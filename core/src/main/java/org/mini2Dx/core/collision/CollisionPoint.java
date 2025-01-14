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
package org.mini2Dx.core.collision;

import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.geom.Point;
import org.mini2Dx.core.geom.PositionChangeListener;
import org.mini2Dx.core.geom.Positionable;
import org.mini2Dx.core.geom.Shape;
import org.mini2Dx.core.util.InterpolationTracker;
import org.mini2Dx.gdx.math.MathUtils;
import org.mini2Dx.gdx.utils.Array;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * An implementation of {@link Point} that allows for interpolation. Game
 * objects can use this class to move around the game world and retrieve the
 * appropriate rendering coordinates during render phase.
 */
public class CollisionPoint extends Point implements CollisionObject, PositionChangeListener<CollisionPoint> {
	private final int id;
	private final ReentrantReadWriteLock positionChangeListenerLock;

	private final Point previousPosition;
	private final Point renderPosition;

	private int renderX, renderY;
	private boolean interpolateRequired = false;

	public CollisionPoint() {
		this(0f, 0f);
	}

	public CollisionPoint(int id) {
		this(id, 0f, 0f);
	}

	public CollisionPoint(float x, float y) {
		this(CollisionIdSequence.nextId(), x, y);
	}

	public CollisionPoint(int id, float x, float y) {
		super(x, y);
		this.id = id;

		positionChangeListenerLock = new ReentrantReadWriteLock();
		addPostionChangeListener(this);

		InterpolationTracker.register(this);

		previousPosition = Mdx.geom.point();
		previousPosition.set(x, y);

		renderPosition = Mdx.geom.point();
		renderPosition.set(x, y);

		storeRenderCoordinates();
	}

	private void storeRenderCoordinates() {
		renderX = MathUtils.round(renderPosition.getX());
		renderY = MathUtils.round(renderPosition.getY());
	}

	@Override
	public void dispose() {
		InterpolationTracker.deregister(this);
		super.dispose();
		previousPosition.dispose();
		renderPosition.dispose();
	}

	@Override
	public void preUpdate() {
		previousPosition.set(this);
	}

	@Override
	public void interpolate(float alpha) {
		if(!interpolateRequired) {
			return;
		}
		renderPosition.set(previousPosition.lerp(this, alpha));
		storeRenderCoordinates();
		if(renderX != MathUtils.round(x)) {
			return;
		}
		if(renderY != MathUtils.round(y)) {
			return;
		}
		interpolateRequired = false;
	}

	@Override
	public void forceTo(float x, float y) {
		super.set(x, y);
		previousPosition.set(x, y);
		renderPosition.set(x, y);
		storeRenderCoordinates();
		interpolateRequired = false;
	}

	@Override
	public int getRenderX() {
		return renderX;
	}

	@Override
	public int getRenderY() {
		return renderY;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void positionChanged(CollisionPoint moved) {
		if(moved.getId() != id) {
			return;
		}
		interpolateRequired = true;
	}

	@Override
	public <T extends Positionable> void addPostionChangeListener(
			PositionChangeListener<T> listener) {
		positionChangeListenerLock.writeLock().lock();
		if (positionChangeListeners == null) {
			positionChangeListeners = new Array<PositionChangeListener>(true,1);
		}
		positionChangeListeners.add(listener);
		positionChangeListenerLock.writeLock().unlock();
	}

	@Override
	public <T extends Positionable> void removePositionChangeListener(
			PositionChangeListener<T> listener) {
		Shape.removePositionListener(positionChangeListenerLock, positionChangeListeners, listener);
	}

	@Override
	protected void notifyPositionChangeListeners() {
		Shape.notifyPositionListeners(positionChangeListenerLock, positionChangeListeners, this);
	}

	@Override
	protected void clearPositionChangeListeners() {
		Shape.clearPositionListeners(positionChangeListenerLock, positionChangeListeners);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CollisionPoint)) return false;
		if (!super.equals(o)) return false;
		CollisionPoint that = (CollisionPoint) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "CollisionPoint{" +
				"id=" + id +
				", x=" + x +
				", y=" + y +
				'}';
	}
}
