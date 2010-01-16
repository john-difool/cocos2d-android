package org.cocos2d.nodes;

import org.cocos2d.opengl.Texture2D;
import org.cocos2d.types.CCSize;

public class Label extends TextureNode {

    public enum TextAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    CCSize _dimensions;
    TextAlignment _alignment;
    String _fontName;
    float _fontSize;

    public Label() {
        throw new LabelInitException("Use ctor with dimensions, aligment, fontName and font instead");
    }

    public Label(String string, String fontname, float fontsize) {
        this(string, 0, 0, TextAlignment.CENTER, fontname, fontsize);
    }

    public Label(String string, float w, float h, TextAlignment alignment, String name, float size) {
        _dimensions = CCSize.make(w, h);
        _alignment = alignment;
        _fontName = name;
        _fontSize = size;

        setString(string);
    }

    public void setString(String string) {
        if (CCSize.equalToSize(_dimensions, CCSize.zero())) {
            setTexture(new Texture2D(string, _fontName, _fontSize));
        } else
            setTexture(new Texture2D(string, _dimensions, _alignment, _fontName, _fontSize));

        setTransformAnchor(getTexture().getWidth() / 2, getTexture().getHeight() / 2);
    }

}

class LabelInitException extends RuntimeException {

    public LabelInitException(String s) {
        super(s);
    }

}
