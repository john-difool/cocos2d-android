package org.cocos2d.nodes;

import org.cocos2d.types.CCQuad2;
import org.cocos2d.types.CCQuad3;

import javax.microedition.khronos.opengles.GL10;

public class LabelAtlas extends AtlasNode implements CocosNode.CocosNodeLabel, CocosNode.CocosNodeSize {
    /// string to render
    String string;

    /// the first char in the charmap
    char mapStartChar;

    public static LabelAtlas label(String theString, String charmapfile, int w, int h, char c) {
        return new LabelAtlas(theString, charmapfile, w, h, c);
    }

    protected LabelAtlas(String theString, String charmapfile, int w, int h, char c) {
        super(charmapfile, w, h, theString.length());

        string = theString;
        mapStartChar = c;

        updateAtlasValues();
    }

    @Override
    public void updateAtlasValues() {
        int n = string.length();

        CCQuad2 texCoord = new CCQuad2();
        CCQuad3 vertex = new CCQuad3();

        String s = string;

		int xpos = 0;
		int ypos = 0;
		int numLines = 1;
		int maxLineWidth = 0;

        for (int i = 0; i < n; i++)
		{
			// If we see a newline, reset the x-position back to zero
			// and update our y-position. Since OpenGL is y=0 bottom,
			// we negate numLines
		    if (s.charAt(i) == '\n')
			{
				// Keep track of the longest line
				if (xpos > maxLineWidth)
				{
					maxLineWidth = xpos;
				}
				xpos = 0;
				ypos = -numLines * itemHeight;
				numLines++;
			}
            else
			{
				int a = s.charAt(i) - mapStartChar;
				float row = (a % itemsPerRow) * texStepX;
				float col = (a / itemsPerRow) * texStepY;

				texCoord.bl_x = row;                        // A - x
				texCoord.bl_y = col;                        // A - y
				texCoord.br_x = row + texStepX;             // B - x
				texCoord.br_y = col;                        // B - y
				texCoord.tl_x = row;                        // C - x
				texCoord.tl_y = col + texStepY;             // C - y
				texCoord.tr_x = row + texStepX;             // D - x
				texCoord.tr_y = col + texStepY;             // D - y

				vertex.bl_x = xpos * itemWidth;             // A - x
				vertex.bl_y = ypos;                         // A - y
				vertex.bl_z = 0;                            // A - z
				vertex.br_x = xpos * itemWidth + itemWidth; // B - x
				vertex.br_y = ypos;                         // B - y
				vertex.br_z = 0;                            // B - z
				vertex.tl_x = xpos * itemWidth;             // C - x
				vertex.tl_y = ypos + itemHeight;            // C - y
				vertex.tl_z = 0;                            // C - z
				vertex.tr_x = xpos * itemWidth + itemWidth; // D - x
				vertex.tr_y = ypos + itemHeight;            // D - y
				vertex.tr_z = 0;                            // D - z

				textureAtlas_.updateQuad(texCoord, vertex, i);
				xpos++;
			}
        }

		int numCharsWidth = n;
		// If this is multi-line, use the max line width instead of the total string length
		if (numLines > 1)
		{
			numCharsWidth = maxLineWidth;
		}

		setContentSize(itemWidth * numCharsWidth, itemHeight * numLines);
    }

    public void setString(String newString) {
        if (newString.length() > textureAtlas_.getTotalQuads())
            textureAtlas_.resizeCapacity(newString.length());

        string = newString;
        updateAtlasValues();
    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glEnable(GL10.GL_TEXTURE_2D);

        //fixed bug that can't show text on G1 by zt
        gl.glColor4f(color_.r / 255f, color_.g / 255f, color_.b / 255f, opacity_ / 255f);

        textureAtlas_.draw(gl, string.length());

        // is this chepear than saving/restoring color state ?
        //gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glDisable(GL10.GL_TEXTURE_2D);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    @Override
    public float getWidth() {
        return string.length() * itemWidth;
    }

    @Override
    public float getHeight() {
        return itemHeight;
    }
}
