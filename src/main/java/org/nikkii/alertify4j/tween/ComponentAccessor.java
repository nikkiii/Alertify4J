package org.nikkii.alertify4j.tween;

import aurelienribon.tweenengine.TweenAccessor;

import java.awt.Component;

/**
 * A {@link TweenAccessor} for Components, with added X and Y types.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com
 * @author Nikki
 */
public class ComponentAccessor implements TweenAccessor<Component> {

	/**
	 * Sets the Component's X and Y positions.
	 */
	public static final int POSITION_XY = 0;

	/**
	 * Sets the Component's X position.
	 */
	public static final int POSITION_X = 1;

	/**
	 * Sets the Component's Y position.
	 */
	public static final int POSITION_Y = 2;

	@Override
	public int getValues(Component target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case POSITION_XY:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case POSITION_X:
			returnValues[0] = target.getX();
			return 1;
		case POSITION_Y:
			returnValues[0] = target.getY();
			return 1;
		}
		return 0;
	}

	@Override
	public void setValues(Component target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case POSITION_XY:
			target.setLocation((int) newValues[0], (int) newValues[1]);
			break;
		case POSITION_X:
			target.setLocation((int) newValues[0], target.getY());
			break;
		case POSITION_Y:
			target.setLocation(target.getX(), (int) newValues[0]);
			break;
		}
	}
}