package org.cocos2d.menus;

import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Label;
import org.cocos2d.types.CCRect;

public class MenuItemFont extends MenuItemLabel {
    private Label label;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label l) {
        label = l;
    }

    static int _fontSize = kItemSize;
    static String _fontName = "DroidSans";

    public static void setFontSize(int s) {
        _fontSize = s;
    }

    public static int fontSize() {
        return _fontSize;
    }

    public static void setFontName(String n) {
        _fontName = n;
    }

    public static String fontName() {
        return _fontName;
    }

    public static MenuItemFont item(String value, CocosNode rec, String cb) {
        return new MenuItemFont(new Label(value, _fontName, _fontSize), rec, cb);
    }

    public MenuItemFont(Label label, CocosNode rec, String cb) {
        super(label, rec, cb);

        setTransformAnchor(label.getWidth() / 2, label.getHeight() / 2);
    }

    public void setString(String string) {
        label.setString(string);
        setTransformAnchor(label.getWidth() / 2, label.getHeight() / 2);
    }

    public CCRect rect() {
        float width = label.getWidth();
        float height = label.getHeight();

        return CCRect.make(getPositionX() - width / 2, getPositionY() - height, width, height);
    }

}

