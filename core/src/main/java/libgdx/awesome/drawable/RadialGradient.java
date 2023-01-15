package libgdx.awesome.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class RadialGradient extends Gradient{
    protected float radius;
    protected Vector2 center=new Vector2();
    public RadialGradient(Color start, Color end) {
        super(start, end,25);
    }

    @Override
    protected void buildVertices() {
        radius=Math.min(width,height)/2f;
        center.set(x+width/2f,y+height/2f);
        vert(0,center.x,center.y,0.5f,0.5f,getColor(center.x,center.y).toFloatBits());
        vert(5,x,y+height,0,0,getColor(x,y+height).toFloatBits());
        vert(10,x+width,y+height,1,0,getColor(x+width,y+height).toFloatBits());
        vert(15,x+width,y,1,1,getColor(x+width,y).toFloatBits());
        vert(20,x,y,0,1,getColor(x,y).toFloatBits());

    }

    protected Color getColor(float x,float y){
        float distance=center.dst(x,y);
        if (distance>=radius){
            return end;
        }else{
            return start;
        }
    }
}
