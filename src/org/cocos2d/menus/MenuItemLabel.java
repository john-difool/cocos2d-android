package org.cocos2d.menus;

import org.cocos2d.actions.base.Action;
import org.cocos2d.actions.interval.ScaleTo;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.types.CCColor3B;

import javax.microedition.khronos.opengles.GL10;

public class MenuItemLabel extends MenuItem implements CocosNode.CocosNodeRGBA {
    private CocosNode label_;
    private CCColor3B colorBackup;
    private CCColor3B disabledColor_;

    public MenuItemLabel(CocosNode label, CocosNode target, String selector) {
        super(target, selector);
        label_ = label;
        colorBackup = new CCColor3B(255, 255, 255);
        disabledColor_ = new CCColor3B(126, 126, 126);
    }

    public void setOpacity(int opacity) {
        ((CocosNodeRGBA) label_).setOpacity(opacity);
    }

    public int getOpacity() {
        return ((CocosNodeRGBA) label_).getOpacity();
    }

    public void setColor(CCColor3B color) {
        ((CocosNodeRGBA) label_).setColor(color);
    }

    public CCColor3B getColor() {
        return ((CocosNodeRGBA) label_).getColor();
    }

    public CCColor3B getDisabledColor() {
        return new CCColor3B(disabledColor_.r, disabledColor_.g, disabledColor_.b);
    }

    public void setDisabledColor(CCColor3B color) {
        disabledColor_.r = color.r;
        disabledColor_.g = color.g;
        disabledColor_.b = color.b;
    }

    public CocosNode getLabel() {
        return label_;
    }

    public void setLabel(CocosNode label) {
        label_ = label;
        setContentSize(((CocosNodeSize) label_).getWidth(), ((CocosNodeSize) label_).getHeight());
    }

    public void setString(String string) {
        ((CocosNodeLabel) label_).setString(string);
        setContentSize(((CocosNodeSize) label_).getWidth(), ((CocosNodeSize) label_).getHeight());
    }

    public void activate() {
        if (isEnabled_) {
            stopAllActions();

            setScale(1.0f);

            super.activate();
        }
    }

    public void selected() {
        // subclass to change the default action
        if (isEnabled_) {
            selected();

            stopAction(kZoomActionTag);
            Action zoomAction = ScaleTo.action(0.1f, 1.2f);
            zoomAction.setTag(kZoomActionTag);
            runAction(zoomAction);
        }
    }

    public void unselected() {
        // subclass to change the default action
        if (isEnabled_) {
            super.unselected();

            stopAction(kZoomActionTag);
            Action zoomAction = ScaleTo.action(0.1f, 1.0f);
            zoomAction.setTag(kZoomActionTag);
            runAction(zoomAction);
        }
    }

    public void setIsEnabled(boolean enabled) {
        if (isEnabled_ != enabled) {
            if (!enabled) {
                colorBackup = ((CocosNodeRGBA) label_).getColor();
                ((CocosNodeRGBA) label_).setColor(disabledColor_);
            } else
                ((CocosNodeRGBA) label_).setColor(colorBackup);
        }

        super.setIsEnabled(enabled);
    }

    public void draw(GL10 gl) {
        label_.draw(gl);
    }


}
