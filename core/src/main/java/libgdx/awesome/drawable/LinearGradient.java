package libgdx.awesome.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class LinearGradient extends Gradient{
    public LinearGradient(Color start, Color end) {
        super(start, end,20);
    }

    @Override
    protected void buildVertices() {
        vert(0,x,y+height,0,0,end.toFloatBits());
        vert(5,x+width,y+height,1,0,end.toFloatBits());
        vert(10,x+width,y,1,1,start.toFloatBits());
        vert(15,x,y,0,1,start.toFloatBits());
    }
}
