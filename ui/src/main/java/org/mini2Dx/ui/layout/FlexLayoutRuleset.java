/**
 * Copyright (c) 2018 See AUTHORS file
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the mini2Dx nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mini2Dx.ui.layout;

import org.mini2Dx.core.exception.MdxException;
import org.mini2Dx.core.input.GamePadType;
import org.mini2Dx.gdx.math.MathUtils;
import org.mini2Dx.gdx.utils.Array;
import org.mini2Dx.ui.InputSource;
import org.mini2Dx.ui.render.ParentRenderNode;
import org.mini2Dx.ui.render.RenderNode;

import java.util.*;

public class FlexLayoutRuleset extends LayoutRuleset {
	public static final String DEFAULT_RULESET = "flex-column:xs-12c,xs-auto";

	protected static final String AUTO = "auto";
	protected static final String PIXEL_SUFFIX = "px";
	protected static final String COLUMN_SUFFIX = "c";
	protected static final String EMPTY_STRING = "";

	protected final String rules;
	protected final Map<ScreenSize, SizeRule> widthRules = new HashMap<ScreenSize, SizeRule>();
	protected final Map<ScreenSize, SizeRule> heightRules = new HashMap<ScreenSize, SizeRule>();
	protected final Set<InputSource> hiddenByInput = new HashSet<InputSource>();
	protected final Set<GamePadType> hiddenByGamePadType = new HashSet<GamePadType>();
	protected final Map<ScreenSize, OffsetRule> offsetXRules = new HashMap<ScreenSize, OffsetRule>();
	protected final Map<ScreenSize, OffsetRule> offsetYRules = new HashMap<ScreenSize, OffsetRule>();

	private final FlexDirection flexDirection;
	private boolean hiddenByInputSource = false;

	private SizeRule currentWidthRule = null;
	private SizeRule currentHeightRule = null;
	private OffsetRule currentOffsetXRule = null;
	private OffsetRule currentOffsetYRule = null;

	/**
	 * Constructor
	 * @param flexDirection The {@link FlexDirection}
	 * @param rules The ruleset, e.g. flex-column:xs-12c xs-offset-4c sm-500px sm-offset-20px,auto
	 * @param ruleValue The part of the ruleset after :
	 */
	public FlexLayoutRuleset(FlexDirection flexDirection, String rules, String ruleValue) {
		super();
		this.rules = rules;
		this.flexDirection = flexDirection;

		String [] components = ruleValue.split(",");
		switch(components.length) {
		case 1: {
			//Horizontal only
			String[] rule = components[0].trim().split(" ");
			for (int i = 0; i < rule.length; i++) {
				String[] ruleDetails = rule[i].split("-");
				switch (ruleDetails.length) {
				case 1:
					throw new MdxException("Invalid flex value '" + rule[i] + "'. Perhaps you forgot a size prefix, e.g. xs-");
				case 2:
					// e.g. xs-12, hidden-gamepad, visible-touchscreen,
					// hidden-keyboardmouse
					storeSizeRule(true, widthRules, ruleDetails);
					break;
				case 3:
					// e.g. xs-offset-12, hidden-gamepad-ps4
					storeOffsetRule(true, offsetXRules, ruleDetails);
					break;
				}
			}
			storeSizeRule(false, heightRules, "xs-auto".split("-"));
			break;
		}
		case 2: {
			{
				//Horizontal
				String[] rule = components[0].trim().split(" ");
				for (int i = 0; i < rule.length; i++) {
					String[] ruleDetails = rule[i].split("-");
					switch (ruleDetails.length) {
					case 1:
						throw new MdxException("Invalid flex value '" + rule[i] + "'. Perhaps you forgot a size prefix, e.g. xs-");
					case 2:
						// e.g. xs-12, hidden-gamepad, visible-touchscreen,
						// hidden-keyboardmouse
						storeSizeRule(true, widthRules, ruleDetails);
						break;
					case 3:
						// e.g. xs-offset-12, hidden-gamepad-ps4
						storeOffsetRule(true, offsetXRules, ruleDetails);
						break;
					}
				}
			}
			{
				//Vertical
				String[] rule = components[1].trim().split(" ");
				for (int i = 0; i < rule.length; i++) {
					String[] ruleDetails = rule[i].split("-");
					switch (ruleDetails.length) {
					case 1:
						throw new MdxException("Invalid flex value '" + rule[i] + "'. Perhaps you forgot a size prefix, e.g. xs-");
					case 2:
						// e.g. xs-12, hidden-gamepad, visible-touchscreen,
						// hidden-keyboardmouse
						storeSizeRule(false, heightRules, ruleDetails);
						break;
					case 3:
						// e.g. xs-offset-12, hidden-gamepad-ps4
						storeOffsetRule(false, offsetYRules, ruleDetails);
						break;
					}
				}
			}
		}
		default:
			break;
		}

		finaliseRuleset(widthRules, offsetXRules);
		finaliseRuleset(heightRules, offsetYRules);
	}

	private void storeSizeRule(boolean horizontalRuleset, Map<ScreenSize, SizeRule> sizeRules, String[] ruleDetails) {
		switch (ruleDetails[0].toLowerCase()) {
		case "hidden": {
			if(!horizontalRuleset) {
				throw new MdxException("hidden-* rules can only be applied to horizontal rulesets");
			}
			switch (InputSource.fromFriendlyString(ruleDetails[1])) {
			case CONTROLLER:
				hiddenByInput.add(InputSource.CONTROLLER);
				break;
			case KEYBOARD_MOUSE:
				hiddenByInput.add(InputSource.KEYBOARD_MOUSE);
				break;
			case TOUCHSCREEN:
				hiddenByInput.add(InputSource.TOUCHSCREEN);
				break;
			}
			break;
		}
		default:
			ScreenSize screenSize = ScreenSize.fromString(ruleDetails[0].trim());
			if (ruleDetails[1].equalsIgnoreCase(AUTO)) {
				if(horizontalRuleset) {
					throw new MdxException("Invalid size - cannot use auto size for horizontal size rules. Must end be columns (c) or pixels (px)");
				}
				sizeRules.put(screenSize, new AutoSizeRule());
			} else if (ruleDetails[1].endsWith(PIXEL_SUFFIX)) {
				sizeRules.put(screenSize,
						new AbsoluteSizeRule(Float.parseFloat(ruleDetails[1].replace(PIXEL_SUFFIX, EMPTY_STRING).trim())));
			} else if (ruleDetails[1].endsWith(COLUMN_SUFFIX)) {
				if(!horizontalRuleset) {
					throw new MdxException("Invalid size - cannot use column size for vertical size rules. Must be pixel (px) or auto");
				}
				sizeRules.put(screenSize,
						new ResponsiveSizeRule(Integer.parseInt(ruleDetails[1].replace(COLUMN_SUFFIX, EMPTY_STRING).trim())));
			} else {
				throw new MdxException("Invalid size - must end with c (columns) or px (pixels");
			}
			break;
		}
	}

	private void storeOffsetRule(boolean horizontalRuleset, Map<ScreenSize, OffsetRule> offsetRules, String[] ruleDetails) {
		switch (ruleDetails[0].toLowerCase()) {
		case "hidden": {
			if(!horizontalRuleset) {
				throw new MdxException("hidden-* rules can only be applied to horizontal rulesets");
			}
			switch (InputSource.fromFriendlyString(ruleDetails[1])) {
			case CONTROLLER:
				GamePadType controllerType = GamePadType.fromFriendlyString(ruleDetails[2]);
				switch(controllerType) {
				case UNKNOWN:
					break;
				default:
					hiddenByGamePadType.add(controllerType);
					break;
				}
				break;
			default:
				throw new MdxException("Invalid rule " + ruleDetails[0] + "-" + ruleDetails[1] + "-" + ruleDetails[2]);
			}
		}
		default: {
			ScreenSize screenSize = ScreenSize.fromString(ruleDetails[0]);
			if (ruleDetails[2].endsWith(PIXEL_SUFFIX)) {
				offsetRules.put(screenSize,
						new AbsoluteOffsetRule(Float.parseFloat(ruleDetails[2].replace(PIXEL_SUFFIX, EMPTY_STRING))));
			} else if (ruleDetails[2].endsWith(COLUMN_SUFFIX)) {
				if(!horizontalRuleset) {
					throw new MdxException("Invalid offset - cannot use column offset for vertical size rules. Must be pixel (px)");
				}
				offsetRules.put(screenSize,
						new ResponsiveOffsetRule(Integer.parseInt(ruleDetails[2].replace(COLUMN_SUFFIX, EMPTY_STRING))));
			} else {
				throw new MdxException("Invalid offset - must end with c (columns) or px (pixels");
			}
		}
		}
	}

	private void finaliseRuleset(Map<ScreenSize, SizeRule> sizeRules, Map<ScreenSize, OffsetRule> offsetRules) {
		Iterator<ScreenSize> screenSizes = ScreenSize.smallestToLargest();
		SizeRule lastSizeRule = new ResponsiveSizeRule(12);
		OffsetRule lastOffsetRule = new AbsoluteOffsetRule(0);

		while (screenSizes.hasNext()) {
			ScreenSize nextSize = screenSizes.next();

			if (!sizeRules.containsKey(nextSize)) {
				sizeRules.put(nextSize, lastSizeRule);
			} else {
				lastSizeRule = sizeRules.get(nextSize);
			}

			if (!offsetRules.containsKey(nextSize)) {
				offsetRules.put(nextSize, lastOffsetRule);
			} else {
				lastOffsetRule = offsetRules.get(nextSize);
			}
		}
	}

	@Override
	public void layout(LayoutState layoutState, ParentRenderNode<?, ?> parentNode, Array<RenderNode<?, ?>> children) {
		flexDirection.layout(layoutState, parentNode, children);
	}

	@Override
	public float getPreferredElementRelativeX(LayoutState layoutState) {
		currentOffsetXRule = offsetXRules.get(layoutState.getScreenSize());
		return currentOffsetXRule.getOffset(layoutState);
	}

	@Override
	public float getPreferredElementRelativeY(LayoutState layoutState) {
		currentOffsetYRule = offsetYRules.get(layoutState.getScreenSize());
		return currentOffsetYRule.getOffset(layoutState);
	}

	@Override
	public float getPreferredElementWidth(LayoutState layoutState) {
		currentWidthRule = widthRules.get(layoutState.getScreenSize());
		return currentWidthRule.getSize(layoutState);
	}

	@Override
	public float getPreferredElementHeight(LayoutState layoutState) {
		currentHeightRule = heightRules.get(layoutState.getScreenSize());
		return currentHeightRule.getSize(layoutState);
	}

	public boolean isHiddenByInputSource(LayoutState layoutState) {
		switch(layoutState.getLastInputSource()) {
		case CONTROLLER:
			if(hiddenByGamePadType.isEmpty()) {
				hiddenByInputSource = hiddenByInput.contains(layoutState.getLastInputSource());
			} else {
				hiddenByInputSource = hiddenByGamePadType.contains(layoutState.getLastGamePadType());
			}
			break;
		case KEYBOARD_MOUSE:
			hiddenByInputSource = hiddenByInput.contains(layoutState.getLastInputSource());
			break;
		case TOUCHSCREEN:
			hiddenByInputSource = hiddenByInput.contains(layoutState.getLastInputSource());
			break;
		default:
			break;
		}
		return hiddenByInputSource;
	}

	public boolean isHiddenByInputSource() {
		return hiddenByInputSource;
	}

	public SizeRule getCurrentWidthRule() {
		return currentWidthRule;
	}

	public SizeRule getCurrentHeightRule() {
		return currentHeightRule;
	}

	public OffsetRule getCurrentOffsetXRule() {
		return currentOffsetXRule;
	}

	public OffsetRule getCurrentOffsetYRule() {
		return currentOffsetYRule;
	}

	@Override
	public boolean isFlexLayout() {
		return true;
	}

	public boolean equals(String rules) {
		if(rules == null) {
			return false;
		}
		if(rules.isEmpty()) {
			return false;
		}
		return this.rules.equals(rules);
	}

	public static FlexLayoutRuleset parse(String layout) {
		final String [] typeAndValue = layout.toLowerCase().split(":");
		switch(typeAndValue[0]) {
		case "flex-col":
		case "flex-column":
			return new FlexLayoutRuleset(FlexDirection.COLUMN, layout, typeAndValue[1]);
		case "flex-r":
		case "flex-row":
			return new FlexLayoutRuleset(FlexDirection.ROW, layout, typeAndValue[1]);
		case "flex-col-r":
		case "flex-column-r":
		case "flex-column-reverse":
			return new FlexLayoutRuleset(FlexDirection.COLUMN_REVERSE, layout, typeAndValue[1]);
		case "flex-r-r":
		case "flex-row-r":
		case "flex-row-reverse":
			return new FlexLayoutRuleset(FlexDirection.ROW_REVERSE, layout, typeAndValue[1]);
		case "flex-cen":
		case "flex-centre":
		case "flex-center":
			return new FlexLayoutRuleset(FlexDirection.CENTER, layout, typeAndValue[1]);
		}
		throw new MdxException("Invalid layout type '" + typeAndValue[0] + "'");
	}

	public static String set(String flexLayout, float x, float y, float width, float height) {
		flexLayout = setXY(flexLayout, x, y);
		flexLayout = setWidth(flexLayout, width);
		flexLayout = setHeight(flexLayout, height);
		return flexLayout;
	}

	public static String setXY(String flexLayout, float x, float y) {
		flexLayout = setX(flexLayout, x);
		flexLayout = setY(flexLayout, y);
		return flexLayout;
	}

	public static String setX(String flexLayout, float x) {
		final int valueIndex = flexLayout.indexOf(':') + 1;
		final String flexComponent = flexLayout.substring(0, valueIndex);

		final String [] xyComponents = flexLayout.substring(valueIndex).split(",");
		final String [] xComponents = xyComponents[0].split(" ");
		final String [] yComponents = xyComponents.length > 1 ? xyComponents[1].split(" ") : new String[0];

		final StringBuilder xResult = new StringBuilder();
		final StringBuilder yResult = new StringBuilder();

		for(int i = 0; i < xComponents.length; i++) {
			if(xComponents[i].contains("offset")) {
				continue;
			}
			xResult.append(xComponents[i]);
			xResult.append(' ');
		}
		xResult.append("xs-offset-");
		xResult.append(MathUtils.round(x));
		xResult.append("px");

		if(yComponents.length == 0) {
			yResult.append("xs-auto");
		} else {
			yResult.append(flexLayout.substring(flexLayout.lastIndexOf(',') + 1));
		}
		return flexComponent + xResult.toString().trim() + ',' + yResult.toString().trim();
	}

	public static String setY(String flexLayout, float y) {
		final int valueIndex = flexLayout.indexOf(':') + 1;
		final String flexComponent = flexLayout.substring(0, valueIndex);

		final String [] xyComponents = flexLayout.substring(valueIndex).split(",");
		final String [] xComponents = xyComponents[0].split(" ");
		final String [] yComponents = xyComponents.length > 1 ? xyComponents[1].split(" ") : new String[0];

		final StringBuilder xResult = new StringBuilder();
		final StringBuilder yResult = new StringBuilder();

		for(int i = 0; i < xComponents.length; i++) {
			xResult.append(xComponents[i]);
			xResult.append(' ');
		}

		if(yComponents.length > 0) {
			for(int i = 0; i < yComponents.length; i++) {
				if(yComponents[i].contains("offset")) {
					continue;
				}

				yResult.append(yComponents[i]);
				yResult.append(' ');
			}
		} else {
			yResult.append("xs-auto");
			yResult.append(' ');
		}
		yResult.append("xs-offset-");
		yResult.append(MathUtils.round(y));
		yResult.append("px");

		return flexComponent + xResult.toString().trim() + ',' + yResult.toString().trim();
	}

	public static String setWidth(String flexLayout, float width) {
		final int valueIndex = flexLayout.indexOf(':') + 1;
		final String flexComponent = flexLayout.substring(0, valueIndex);

		final String [] xyComponents = flexLayout.substring(valueIndex).split(",");
		final String [] xComponents = xyComponents[0].split(" ");
		final String [] yComponents = xyComponents.length > 1 ? xyComponents[1].split(" ") : new String[0];

		final StringBuilder xResult = new StringBuilder();
		final StringBuilder yResult = new StringBuilder();

		for(int i = 0; i < xComponents.length; i++) {
			if(!xComponents[i].contains("offset")) {
				continue;
			}
			xResult.append(xComponents[i]);
			xResult.append(' ');
		}
		xResult.append("xs-");
		xResult.append(MathUtils.round(width));
		xResult.append("px");

		if(yComponents.length == 0) {
			yResult.append("xs-auto");
		} else {
			yResult.append(flexLayout.substring(flexLayout.lastIndexOf(',') + 1));
		}
		return flexComponent + xResult.toString().trim() + ',' + yResult.toString().trim();
	}

	public static String setHeight(String flexLayout, float height) {
		final int valueIndex = flexLayout.indexOf(':') + 1;
		final String flexComponent = flexLayout.substring(0, valueIndex);

		final String [] xyComponents = flexLayout.substring(valueIndex).split(",");
		final String [] xComponents = xyComponents[0].split(" ");
		final String [] yComponents = xyComponents.length > 1 ? xyComponents[1].split(" ") : new String[0];

		final StringBuilder xResult = new StringBuilder();
		final StringBuilder yResult = new StringBuilder();

		for(int i = 0; i < xComponents.length; i++) {
			xResult.append(xComponents[i]);
			xResult.append(' ');
		}

		if(yComponents.length > 0) {
			for(int i = 0; i < yComponents.length; i++) {
				if(!yComponents[i].contains("offset")) {
					continue;
				}
				yResult.append(yComponents[i]);
				yResult.append(' ');
			}
		}
		yResult.append("xs-");
		yResult.append(MathUtils.round(height));
		yResult.append("px");

		return flexComponent + xResult.toString().trim() + ',' + yResult.toString().trim();
	}
}
