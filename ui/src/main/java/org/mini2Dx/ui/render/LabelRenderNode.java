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
package org.mini2Dx.ui.render;

import org.mini2Dx.core.Graphics;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.exception.MdxException;
import org.mini2Dx.core.font.GameFont;
import org.mini2Dx.core.font.GameFontCache;
import org.mini2Dx.core.graphics.Color;
import org.mini2Dx.ui.animation.NullTextAnimation;
import org.mini2Dx.ui.element.Label;
import org.mini2Dx.ui.layout.LayoutState;
import org.mini2Dx.ui.style.LabelStyleRule;

/**
 * {@link RenderNode} implementation for {@link Label}
 */
public class LabelRenderNode extends RenderNode<Label, LabelStyleRule> {
	protected final NullTextAnimation nullAnimation = new NullTextAnimation();

	protected GameFont font = Mdx.fonts.defaultFont();
	protected GameFontCache fontCache = Mdx.fonts.defaultFont().newCache();
	protected final Color white = Mdx.graphics.newColor(1f, 1f, 1f, 1f);

	protected boolean bitmapCacheReset = false;

	public LabelRenderNode(ParentRenderNode<?, ?> parent, Label element) {
		super(parent, element);
	}

	@Override
	public void update(UiContainerRenderTree uiContainer, float delta) {
		super.update(uiContainer, delta);

		if(bitmapCacheReset) {
			nullAnimation.onResize(fontCache, element.getText(), preferredContentWidth,
					element.getHorizontalAlignment().getAlignValue());
			if (element.getTextAnimation() != null) {
				element.getTextAnimation().onResize(fontCache, element.getText(), preferredContentWidth,
						element.getHorizontalAlignment().getAlignValue());
			}
			bitmapCacheReset = false;
		}

		if (element.getTextAnimation() == null) {
			nullAnimation.update(fontCache, element.getText(), preferredContentWidth,
					element.getHorizontalAlignment().getAlignValue(), delta);
		} else {
			element.getTextAnimation().update(fontCache, element.getText(), preferredContentWidth,
					element.getHorizontalAlignment().getAlignValue(), delta);
		}
	}

	protected void renderBackground(Graphics g) {
		switch(getState()) {
		case NORMAL:
			if (style.getNormalBackgroundRenderer() != null) {
				style.getNormalBackgroundRenderer().render(g, getInnerRenderX(), getInnerRenderY(), getInnerRenderWidth(),
						getInnerRenderHeight());
			}
			break;
		case HOVER:
			if(style.getHoverBackgroundRenderer() != null) {
				style.getHoverBackgroundRenderer().render(g, getInnerRenderX(), getInnerRenderY(), getInnerRenderWidth(),
						getInnerRenderHeight());
			}
			break;
		case ACTION:
			if(style.getHoverBackgroundRenderer() != null) {
				style.getHoverBackgroundRenderer().render(g, getInnerRenderX(), getInnerRenderY(), getInnerRenderWidth(),
						getInnerRenderHeight());
			}
			break;
		}
	}

	@Override
	protected void renderElement(Graphics g) {
		renderBackground(g);

		if (element.getTextAnimation() == null) {
			nullAnimation.render(fontCache, g, getContentRenderX(), getContentRenderY());
		} else {
			element.getTextAnimation().render(fontCache, g, getContentRenderX(), getContentRenderY());
		}
	}

	@Override
	protected float determinePreferredContentWidth(LayoutState layoutState) {
		float availableWidth = layoutState.getParentWidth() - style.getPaddingLeft() - style.getPaddingRight()
				- style.getMarginLeft() - style.getMarginRight();
		if (element.isResponsive()) {
			return style.getRounding().calculateRounding(availableWidth);
		} else if(parent.getElement().isFlexLayout()) {
			font.getSharedGlyphLayout().setText(element.getText());

			if (font.getSharedGlyphLayout().getWidth() > availableWidth) {
				return style.getRounding().calculateRounding(availableWidth);
			}
			return style.getRounding().calculateRounding(font.getSharedGlyphLayout().getWidth());
		} else {
			return style.getRounding().calculateRounding(element.getWidth());
		}
	}

	@Override
	protected float determinePreferredContentHeight(LayoutState layoutState) {
		font.getSharedGlyphLayout().setText(element.getText(), white, preferredContentWidth,
				element.getHorizontalAlignment().getAlignValue(), true);
		if (style.getMinHeight() > 0 && font.getSharedGlyphLayout().getHeight() + style.getPaddingTop() + style.getPaddingBottom()
				+ style.getMarginTop() + style.getMarginBottom() < style.getMinHeight()) {
			return style.getMinHeight() - style.getPaddingTop() - style.getPaddingBottom() - style.getMarginTop()
					- style.getMarginBottom();
		}
		if(parent.getElement().isFlexLayout()) {
			return font.getSharedGlyphLayout().getHeight();
		} else {
			return element.getHeight();
		}
	}

	@Override
	protected float determineXOffset(LayoutState layoutState) {
		if(parent.getLayoutRuleset().isFlexLayout()) {
			return 0f;
		} else {
			return element.getX();
		}
	}

	@Override
	protected float determineYOffset(LayoutState layoutState) {
		if(parent.getLayoutRuleset().isFlexLayout()) {
			return 0f;
		} else {
			return element.getY();
		}
	}

	@Override
	protected LabelStyleRule determineStyleRule(LayoutState layoutState) {
		if (fontCache != null) {
			fontCache.clear();
			fontCache = null;
		}
		bitmapCacheReset = true;

		LabelStyleRule result = layoutState.getTheme().getStyleRule(element, layoutState.getScreenSize());
		if (result.getGameFont() == null) {
			font = Mdx.fonts.defaultFont();
			fontCache = Mdx.fonts.defaultFont().newCache();
		} else {
			font = result.getGameFont();
			fontCache = result.getGameFont().newCache();
		}

		if (element.getColor() != null) {
			fontCache.setColor(element.getColor());
		} else if (result.getColor() != null) {
			fontCache.setColor(result.getColor());
		} else {
			throw new MdxException("Could not determine color for Label " + element.getId()
					+ ". Please use Label#setColor or set a Color on the label style rule");
		}
		return result;
	}

	public void updateBitmapFontCache() {
		if (style == null) {
			return;
		}
		bitmapCacheReset = true;

		font.getSharedGlyphLayout().setText(element.getText(), white, preferredContentWidth,
				element.getHorizontalAlignment().getAlignValue(), true);
		if (font.getSharedGlyphLayout().getHeight() == getPreferredContentHeight()) {
			return;
		}
		setDirty();
	}
}
